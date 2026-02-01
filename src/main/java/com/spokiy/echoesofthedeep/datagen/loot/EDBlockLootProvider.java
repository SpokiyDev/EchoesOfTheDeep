package com.spokiy.echoesofthedeep.datagen.loot;

import com.spokiy.echoesofthedeep.server.block.EDBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class EDBlockLootProvider extends BlockLootSubProvider
{
    private final Set<Block> generatedLootTables = new HashSet<>();

    public EDBlockLootProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(EDBlocks.SCULK_SPROUTS.get(), BlockLootSubProvider::createShearsOnlyDrop);
        this.dropWhenSilkTouch(EDBlocks.SCULK_GUARDIAN.get());

        this.dropSelf(EDBlocks.WARDEN_HEAD.get());

    }

    protected void dropNamedContainer(Block block) {
        add(block, this::createNameableBlockEntityTable);
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder builder) {
        this.generatedLootTables.add(block);
        this.map.put(block.getLootTable(), builder);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return generatedLootTables;
    }
}