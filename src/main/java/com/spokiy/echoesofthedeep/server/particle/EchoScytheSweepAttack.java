package com.spokiy.echoesofthedeep.server.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class EchoScytheSweepAttack extends TextureSheetParticle {

    private final float red;
    private final float green;
    private final float blue;

    public EchoScytheSweepAttack(ClientLevel world, double x, double y, double z,
                                 double xd, double yd, double zd,
                                 float red, float green, float blue) {
        super(world, x, y, z);
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.quadSize = 0.2f + world.random.nextFloat() * 0.1f;
        this.lifetime = 20 + world.random.nextInt(10); // тривалість частинки
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void render(VertexConsumer buffer, Camera camera, float partialTicks) {
        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
        super.render(buffer, camera, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        // трохи прискорення вниз, як у феєрверка
        this.yd -= 0.01;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world,
                                       double x, double y, double z,
                                       double xd, double yd, double zd) {
            float r = world.random.nextFloat();
            float g = world.random.nextFloat();
            float b = world.random.nextFloat();

            EchoScytheSweepAttack particle = new EchoScytheSweepAttack(world, x, y, z, xd, yd, zd, r, g, b);
            particle.pickSprite(sprites);
            return particle;
        }
    }
}
