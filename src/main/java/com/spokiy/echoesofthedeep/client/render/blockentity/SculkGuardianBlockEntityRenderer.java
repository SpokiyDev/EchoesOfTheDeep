package com.spokiy.echoesofthedeep.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.SculkGuardian;
import com.spokiy.echoesofthedeep.server.block.entity.SculkGuardianBlockEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;

public class SculkGuardianBlockEntityRenderer implements BlockEntityRenderer<SculkGuardianBlockEntity> {
    BlockEntityRenderDispatcher entityRenderDispatcher;
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "textures/entity/blockentity/sculk_guardian_eye.png");

    public SculkGuardianBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.entityRenderDispatcher = context.getBlockEntityRenderDispatcher();
    }

    @Override
    public void render(
            @NotNull SculkGuardianBlockEntity entity,
            float partialTicks,
            @NotNull PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int light,
            int packedOverlay
    ) {
        Level level = entity.getLevel();
        if (level == null) return;
        poseStack.pushPose();

        poseStack.translate(0.5f, SculkGuardianBlockEntity.EYE_Y_OFFSET + Math.sin((level.getGameTime() + partialTicks) * 0.1) * 0.028, 0.5f);
        poseStack.scale(0.6f, 0.6f, 0.6f);

        poseStack.mulPose(this.entityRenderDispatcher.camera.rotation());

        VertexConsumer vc = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE));
        PoseStack.Pose pose = poseStack.last();

        //int lightLevel = getLightLevel(level, entity.getBlockPos());
        int lightLevel = LightTexture.FULL_BRIGHT;

//        long time = level.getGameTime();
//        int frameDuration = 100;
        boolean isRecharging = entity.getBlockState().getValue(SculkGuardian.SHRIEKING);
        int frameIndex = isRecharging ? 0 : 1; //(int)(time / frameDuration) % 2;

        float u0 = 1f;
        float u1 = 0f;
        float v0 = (frameIndex + 1) * 0.5f;
        float v1 = frameIndex * 0.5f;

        vc.vertex(pose.pose(), -0.5F, -0.5F, 0)
                .color(255,255,255,255)
                .uv(u0, v0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(lightLevel)
                .normal(pose.normal(), 0.0F, 1.0F, 0.0F)
                .endVertex();

        vc.vertex(pose.pose(), 0.5F, -0.5F, 0)
                .color(255,255,255,255)
                .uv(u1, v0)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(lightLevel)
                .normal(pose.normal(), 0.0F, 1.0F, 0.0F)
                .endVertex();

        vc.vertex(pose.pose(), 0.5F, 0.5F, 0)
                .color(255,255,255,255)
                .uv(u1, v1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(lightLevel)
                .normal(pose.normal(), 0.0F, 1.0F, 0.0F)
                .endVertex();

        vc.vertex(pose.pose(), -0.5F, 0.5F, 0)
                .color(255,255,255,255)
                .uv(u0, v1)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(lightLevel)
                .normal(pose.normal(), 0.0F, 1.0F, 0.0F)
                .endVertex();


        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

}
