package com.spokiy.echoesofthedeep.client.render;

import com.spokiy.echoesofthedeep.client.render.entity.CalibratedSculkShriekerShriekEntityRenderer;
import com.spokiy.echoesofthedeep.server.entity.EDEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class EDEntityRenders {
    public static void register() {
        EntityRenderers.register(EDEntities.CALIBRATED_SCULK_SHRIEKER_SHRIEK.get(), CalibratedSculkShriekerShriekEntityRenderer::new);
    }
}
