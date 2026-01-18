package com.spokiy.echoesofthedeep.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.server.block.entity.CalibratedSculkShriekerShriekEntity;
import com.spokiy.echoesofthedeep.server.block.entity.SculkGuardianBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CalibratedSculkShriekerShriekEntityRenderer extends EntityRenderer<CalibratedSculkShriekerShriekEntity> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, "textures/entity/blockentity/sculk_guardian_eye.png");
    public CalibratedSculkShriekerShriekEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(
            @NotNull CalibratedSculkShriekerShriekEntity entity,
            float f,
            float partialTicks,
            PoseStack poseStack,
            @NotNull MultiBufferSource buffer,
            int light
    ) {
        poseStack.pushPose();

        poseStack.translate(0.0f, 0.5f, 0.0f);

        float scale = (float) entity.currentRadius * 1.5f;
        poseStack.scale(scale, scale, scale);


        VertexConsumer vc = buffer.getBuffer(RenderType.entityTranslucent(getTextureLocation(entity)));
        PoseStack.Pose pose = poseStack.last();

        int lightLevel = LightTexture.FULL_BRIGHT;

        vc.vertex(pose.pose(), -0.5F, 0, -0.5F)
                .color(255,255,255,255).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightLevel).normal(pose.normal(),0,1,0).endVertex();
        vc.vertex(pose.pose(), 0.5F, 0, -0.5F)
                .color(255,255,255,255).uv(1,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightLevel).normal(pose.normal(),0,1,0).endVertex();
        vc.vertex(pose.pose(), 0.5F, 0, 0.5F)
                .color(255,255,255,255).uv(1,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightLevel).normal(pose.normal(),0,1,0).endVertex();
        vc.vertex(pose.pose(), -0.5F, 0, 0.5F)
                .color(255,255,255,255).uv(0,1).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(lightLevel).normal(pose.normal(),0,1,0).endVertex();


        poseStack.popPose();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CalibratedSculkShriekerShriekEntity entity) {
        return ResourceLocation.parse("textures/particle/shriek.png");
    }
}
