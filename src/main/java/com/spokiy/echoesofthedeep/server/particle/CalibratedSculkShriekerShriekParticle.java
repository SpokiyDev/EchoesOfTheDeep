package com.spokiy.echoesofthedeep.server.particle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
        import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CalibratedSculkShriekerShriekParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    public CalibratedSculkShriekerShriekParticle(ClientLevel level, double x, double y, double z, Vec3 velocity, SpriteSet sprites) {
        super(level, x, y, z, velocity.x, velocity.y, velocity.z);
        this.sprites = sprites;
        this.lifetime = 4 + level.random.nextInt(3);
        this.quadSize = 0.5F + (float)level.random.nextDouble() * 0.5F;
        this.rCol = 1.0F;
        this.gCol = 1.0F;
        this.bCol = 1.0F;
        this.setSpriteFromAge(sprites);
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880;
    }

    @Override
    public void tick() {
        super.tick();
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level,
                                       double x, double y, double z,
                                       double xd, double yd, double zd) {
            return new CalibratedSculkShriekerShriekParticle(level, x, y, z, new Vec3(xd, yd, zd), sprites);
        }
    }
}
