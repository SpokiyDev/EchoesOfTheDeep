package com.spokiy.echoesofthedeep.client;

import com.spokiy.echoesofthedeep.client.event.EDClientEvents;
import com.spokiy.echoesofthedeep.client.render.blockentity.SculkGuardianBlockEntityRenderer;
import com.spokiy.echoesofthedeep.server.CommonProxy;
import com.spokiy.echoesofthedeep.server.registry.EDBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
    @Override
    public void clientInit() {
        MinecraftForge.EVENT_BUS.register(new EDClientEvents());
//        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        BlockEntityRenderers.register(EDBlockEntities.SCULK_GUARDIAN.get(), SculkGuardianBlockEntityRenderer::new);

    }
}
