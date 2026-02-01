package com.spokiy.echoesofthedeep.client;

import com.spokiy.echoesofthedeep.EchoesOfTheDeep;
import com.spokiy.echoesofthedeep.client.model.WardenHeadItemModel;
import com.spokiy.echoesofthedeep.client.model.WardenHeadModel;
import com.spokiy.echoesofthedeep.server.block.EDBlockEntities;
import com.spokiy.echoesofthedeep.server.block.EDWallScullBlock;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EchoesOfTheDeep.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EDModelLayers {
    public static final ModelLayerLocation WARDEN_HEAD = register("warden_head");
    public static final ModelLayerLocation WARDEN_HEAD_ITEM = register("warden_head_item");

    public static ModelLayerLocation register(String name) {
        return register(name, "main");
    }
    public static ModelLayerLocation register(String name, String layer) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(EchoesOfTheDeep.MOD_ID, name), layer);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WARDEN_HEAD, WardenHeadModel::createHeadLayer);
        event.registerLayerDefinition(WARDEN_HEAD_ITEM, WardenHeadItemModel::createHeadLayer);
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(EDBlockEntities.SKULL.get(), SkullBlockRenderer::new);
    }
    @SubscribeEvent
    public static void createSkullModels(EntityRenderersEvent.CreateSkullModels event) {
        event.registerSkullModel(EDWallScullBlock.EDTypes.WARDEN, new WardenHeadModel(event.getEntityModelSet().bakeLayer(WARDEN_HEAD)));
    }

}
