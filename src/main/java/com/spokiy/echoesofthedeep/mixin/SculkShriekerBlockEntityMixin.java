package com.spokiy.echoesofthedeep.mixin;

import com.spokiy.echoesofthedeep.config.EDConfigs;
import com.spokiy.echoesofthedeep.server.recipe.SculkShriekerRecipe;
import com.spokiy.echoesofthedeep.server.recipe.SculkShriekerRecipeContainer;
import com.spokiy.echoesofthedeep.server.util.SculkShriekerTickBridge;
import com.spokiy.echoesofthedeep.server.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(SculkShriekerBlockEntity.class)
public abstract class SculkShriekerBlockEntityMixin implements SculkShriekerTickBridge, WorldlyContainer {
    @Unique private int echoes$cooldown = -1;
    @Unique private int echoes$cooldownToDropItemsTime = -1;
    @Unique private ItemStack echoes$currentItemStack;

    @Unique private NonNullList<ItemStack> echoesOfTheDeep$items = NonNullList.withSize(5, ItemStack.EMPTY);


    @Inject(method = "shriek", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onShriek(ServerLevel level, Entity source, CallbackInfo ci, BlockPos blockPos, BlockState blockState) {
        Vec3 center = blockPos.getCenter();
        echoesOfTheDeep$dropAllItemsUp(level, center);

    }

    public void echoesOfTheDeep$tickServer(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) return;

        Vec3 center = pos.getCenter();

        // Periodic visuals during sucking
        if (echoes$cooldown > 0 && echoes$cooldown % 6 == 0 && echoes$currentItemStack != null) {
            Utils.spawnEatParticles(level, center, echoes$currentItemStack, 10);
            level.playSound(null,
                    BlockPos.containing(center),
                    SoundEvents.GENERIC_EAT, SoundSource.BLOCKS,
                    1.0F, EDConfigs.SCULK_SHRIEKER_EAT_SOUND_PITCH);
        }

        // Start item sucking
        if (echoes$cooldown <= 0) {
            if (!echoesOfTheDeep$doRecipe(level, center)) {     // Do not perform sucking if the crafting was successful

                AABB box = new AABB(
                        pos.getX() + 0.2, pos.getY(), pos.getZ() + 0.2,
                        pos.getX() + 0.8, pos.getY() + 1, pos.getZ() + 0.8
                );

                for (ItemEntity item : level.getEntitiesOfClass(ItemEntity.class, box)) {
                    echoes$currentItemStack = item.getItem().copy();

                    if (echoesOfTheDeep$addItem(item)) {
                        echoes$cooldown = EDConfigs.SCULK_SHRIEKER_SUCK_COOLDOWN;
                        echoes$cooldownToDropItemsTime = EDConfigs.SCULK_SHRIEKER_DROP_ITEMS_COOLDOWN;

                        if (!state.getValue(SculkShriekerBlock.SHRIEKING)) {
                            level.setBlock(pos, state.setValue(SculkShriekerBlock.SHRIEKING, true), 2);
                            level.scheduleTick(pos, state.getBlock(), 90);
                        }
                        break;
                    }
                }
            }
        }

        // Drop items after timeout
        if (!this.isEmpty() && echoes$cooldownToDropItemsTime <= 0) {
            echoesOfTheDeep$dropAllItemsUp(level, center);

            echoes$cooldownToDropItemsTime = EDConfigs.SCULK_SHRIEKER_DROP_ITEMS_COOLDOWN;
        }

        // Tick cooldowns
        if (echoes$cooldown > 0) echoes$cooldown--;
        if (echoes$cooldownToDropItemsTime > 0) echoes$cooldownToDropItemsTime--;
    }

    // Recipes
    @Unique
    public Optional<SculkShriekerRecipe> echoesOfTheDeep$getCurrentRecipe(Level level) {
        if (level == null) return Optional.empty();

        SculkShriekerRecipeContainer container = new SculkShriekerRecipeContainer(this.echoesOfTheDeep$items);
        return level.getRecipeManager().getRecipeFor(SculkShriekerRecipe.Type.INSTANCE, container, level);
    }
    @Unique
    public boolean echoesOfTheDeep$doRecipe(Level level, Vec3 pos) {
        var optional = echoesOfTheDeep$getCurrentRecipe(level);
        if (optional.isEmpty()) return false;

        SculkShriekerRecipe recipe = optional.get();
        ItemStack result = recipe.getResultItem(level.registryAccess());

        this.clearContent();
        echoesOfTheDeep$dropStack(level, pos, result);

        Utils.spawnEatParticles(level, pos, result, 16);
        level.playSound(null, BlockPos.containing(pos),
                SoundEvents.SCULK_SHRIEKER_BREAK, SoundSource.BLOCKS,
                1.0F, 1.1F);

        return true;
    }

    // Drop items
    @Unique
    private void echoesOfTheDeep$dropStack(Level level, Vec3 pos, ItemStack stack) {
        if (level.isClientSide) return;

        RandomSource random = level.getRandom();

        ItemEntity itemEntity = new ItemEntity(level, pos.x, pos.y + 0.6D, pos.z, stack.copy());

        int[] dx = {1, -1, 0, 0, 1, 1, -1, -1};
        int[] dz = {0, 0, 1, -1, 1, -1, 1, -1};
        int choice = random.nextInt(8);

        double speed = 0.075D;

        double motionX = dx[choice] * speed;
        double motionZ = dz[choice] * speed;
        motionX += (random.nextDouble() - 0.5D) * 0.05D;
        motionZ += (random.nextDouble() - 0.5D) * 0.05D;

        double motionY = 0.24D + random.nextDouble() * 0.15D;

        itemEntity.setDeltaMovement(motionX, motionY, motionZ);
        itemEntity.setDefaultPickUpDelay();

        level.addFreshEntity(itemEntity);

    }
    @Unique
    public void echoesOfTheDeep$dropAllItemsUp(Level level, Vec3 pos) {
        if (level.isClientSide) return;
        for (int i = 0; i < this.getContainerSize(); i++) {
            ItemStack stack = this.getItem(i);

            if (!stack.isEmpty()) {
                ItemStack stackToDrop = stack.copy();
                this.setItem(i, ItemStack.EMPTY);
                echoesOfTheDeep$dropStack(level, pos, stackToDrop);

            }
        }

        Utils.spawnEatParticles(level, pos, echoes$currentItemStack, 10);
        level.playSound(null, BlockPos.containing(pos),
                SoundEvents.SCULK_SHRIEKER_BREAK, SoundSource.BLOCKS,
                1.0F, 1.1F);

        this.setChanged();
    }

    // Item manipulations
    @Override
    public @NotNull ItemStack getItem(int slot) {
        return echoesOfTheDeep$items.get(slot);
    }
    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        echoesOfTheDeep$items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Unique
    private boolean echoesOfTheDeep$addItem(ItemEntity item) {
        ItemStack entityStack = item.getItem();
        if (entityStack.isEmpty()) return false;

        ItemStack oneItem = entityStack.split(1);

        for (int i = 0; i < this.getContainerSize(); i++) {
            ItemStack slot = this.getItem(i);

            if (slot.isEmpty()) {
                this.setItem(i, oneItem);
                echoesOfTheDeep_1_20_x$finalizeItemEntity(item, entityStack);
                return true;
            }

            if (ItemStack.isSameItemSameTags(slot, oneItem) && slot.getCount() < slot.getMaxStackSize()) {
                slot.grow(1);
                echoesOfTheDeep_1_20_x$finalizeItemEntity(item, entityStack);
                return true;
            }
        }

        entityStack.grow(1);
        return false;
    }
    @Unique
    private void echoesOfTheDeep_1_20_x$finalizeItemEntity(ItemEntity item, ItemStack remaining) {
        if (remaining.isEmpty()) item.discard();
        else item.setItem(remaining);

        this.setChanged();
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(echoesOfTheDeep$items, slot, amount);
    }
    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(echoesOfTheDeep$items, slot);
    }

    // Inventory
    @Override
    public int getContainerSize() {
        return echoesOfTheDeep$items.size();
    }
    @Override
    public boolean isEmpty() {
        for (ItemStack stack : echoesOfTheDeep$items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }
    @Override
    public void clearContent() {
        echoesOfTheDeep$items.clear();
    }

    // Block item inputs/outputs
    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return new int[0];
    }
    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack stack, @Nullable Direction dir) {
        return false;
    }
    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction dir) {
        return false;
    }
    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return false;
    }

    // Save/load
    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void echo$save(CompoundTag tag, CallbackInfo ci) {
        ContainerHelper.saveAllItems(tag, echoesOfTheDeep$items);

        tag.putInt("TransferCooldown", this.echoes$cooldown);
        tag.putInt("TransferCooldownToDropItems", this.echoes$cooldownToDropItemsTime);
    }
    @Inject(method = "load", at = @At("TAIL"))
    private void echo$load(CompoundTag tag, CallbackInfo ci) {
        echoesOfTheDeep$items = NonNullList.withSize(5, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, echoesOfTheDeep$items);

        this.echoes$cooldown = tag.getInt("TransferCooldown");
        this.echoes$cooldownToDropItemsTime = tag.getInt("TransferCooldownToDropItems");
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return false;
    }

}
