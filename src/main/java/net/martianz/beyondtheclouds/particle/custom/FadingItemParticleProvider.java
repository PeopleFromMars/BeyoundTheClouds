package net.martianz.beyondtheclouds.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import org.jetbrains.annotations.Nullable;

public class FadingItemParticleProvider implements ParticleProvider<FadingItemParticleOptions> {

    public FadingItemParticleProvider() {}

    @Override
    public @Nullable Particle createParticle(FadingItemParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new FadingItemParticle(level, x, y, z, options.getStack(), options.getLifeTime());
    }
}