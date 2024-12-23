package net.martianz.beyondtheclouds.particle.custom;

import com.mojang.serialization.MapCodec;
import net.martianz.beyondtheclouds.particle.Particlez;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class FadingItemParticleType extends ParticleType<FadingItemParticleOptions> implements ParticleOptions {

    public FadingItemParticleType(boolean overrideLimitter) {
        super(overrideLimitter);
    }

    @Override
    public MapCodec<FadingItemParticleOptions> codec() {
        return FadingItemParticleOptions.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, FadingItemParticleOptions> streamCodec() {
        return FadingItemParticleOptions.STREAM_CODEC;
    }

    @Override
    public ParticleType<?> getType() {
        return Particlez.METAL_SPARK.get();
    }
}
