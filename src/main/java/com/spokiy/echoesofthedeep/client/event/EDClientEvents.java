package com.spokiy.echoesofthedeep.client.event;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.client.render.blockentity.SculkGuardianBlockEntityRenderer;
import com.spokiy.echoesofthedeep.server.registry.EDBlockEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import net.minecraftforge.fml.common.Mod;

// EDClientEvents.java
@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EDClientEvents {
    private static final ResourceLocation ANCIENT_CITY_ICON =
            ResourceLocation.fromNamespaceAndPath("echoesofthedeep", "textures/map/ancient_city.png");

//    @SubscribeEvent
//    public static void onRenderLevelStage(RenderLevelStageEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//        if (mc.player == null || mc.level == null) return;
//
//        ItemStack stack = mc.player.getMainHandItem();
//        if (!(stack.getItem() instanceof MapItem)) return;
//
//        Integer integer = MapItem.getMapId(stack);
//        MapItemSavedData data = MapItem.getSavedData(integer, mc.level);
//        if (data != null) {
//            PoseStack pose = event.getPoseStack();
//            MultiBufferSource.BufferSource buffer = mc.renderBuffers().bufferSource();
//
//            for (MapDecoration deco : data.getDecorations()) {
//                if (deco.getType() == MapDecoration.Type.BANNER_CYAN)
//                {
//                    CompoundTag tag = stack.getTag();
//                    if (tag != null && tag.contains("display", 10)) {
//                        CompoundTag display = tag.getCompound("display");
//                        if (display.contains("Name", 8)) {
//                            String jsonName = display.getString("Name");
//                            String key = jsonName.replace("{\"translate\":\"", "").replace("\"}", ""); // filled_map.mansion
//
////                            if (key.equals("filled_map.ancient_city") && deco.renderOnFrame())
////                                renderIcon(pose, buffer, deco);
//                        }
//                    }
//
//                }
//            }
//
//            buffer.endBatch();
//        }
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    private static void renderIcon(PoseStack pose, MultiBufferSource buffer, MapDecoration deco) {
//        pose.pushPose();
//
//        pose.translate(
//                64 + deco.getX() / 2f,
//                64 + deco.getY() / 2f,
//                -0.02f
//        );
//
//        pose.scale(4f, 4f, 1f);
//
//        VertexConsumer vc = buffer.getBuffer(
//                RenderType.entityCutout(ANCIENT_CITY_ICON)
//        );
//
//        Matrix4f mat = pose.last().pose();
//        int light = 15728880;
//
//        vc.vertex(mat, -1,  1, 0)
//                .color(255,255,255,255)
//                .uv(0, 0)
//                .uv2(light)
//                .endVertex();
//
//        vc.vertex(mat,  1,  1, 0)
//                .color(255,255,255,255)
//                .uv(1, 0)
//                .uv2(light)
//                .endVertex();
//
//        vc.vertex(mat,  1, -1, 0)
//                .color(255,255,255,255)
//                .uv(1, 1)
//                .uv2(light)
//                .endVertex();
//
//        vc.vertex(mat, -1, -1, 0)
//                .color(255,255,255,255)
//                .uv(0, 1)
//                .uv2(light)
//                .endVertex();
//
//        pose.popPose();
//    }


    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(EDBlockEntities.SCULK_GUARDIAN.get(), SculkGuardianBlockEntityRenderer::new);
    }
}
