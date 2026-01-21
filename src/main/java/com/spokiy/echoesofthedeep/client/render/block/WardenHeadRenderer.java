package com.spokiy.echoesofthedeep.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.spokiy.echoesofthedeep.server.block.WardenHeadBlock;
import com.spokiy.echoesofthedeep.server.block.entity.WardenHeadBlockEntity;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class WardenHeadRenderer implements BlockEntityRenderer<WardenHeadBlockEntity> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/entity/warden/warden.png");

    private final WardenModel<?> model;
    private final ModelPart head;

    public WardenHeadRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new WardenModel<>(ctx.bakeLayer(ModelLayers.WARDEN));
        this.head = this.model.root().getChild("bone").getChild("body").getChild("head");
    }

    @Override
    public void render(WardenHeadBlockEntity entity,
                       float partialTicks,
                       @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource buffer,
                       int light, int overlay
    ) {
        BlockState state = entity.getBlockState();
        Direction facing = state.getValue(WardenHeadBlock.FACING);

        float yRot = switch (facing) {
//            case NORTH -> 0.0F;
            case SOUTH -> 180.0F;
            case WEST  -> -90.0F;
            case EAST  -> 90.0F;
            default -> 0.0F;
        };

        poseStack.pushPose();

        poseStack.translate(0.5F - (float)facing.getStepX() * 0.25F, -0.8125F, 0.5F - (float)facing.getStepZ() * 0.25F);

        poseStack.scale(-1.0F, -1.0F, 1.0F);

        VertexConsumer vc = buffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        head.render(poseStack, vc, light, overlay);

        poseStack.popPose();
    }

}
