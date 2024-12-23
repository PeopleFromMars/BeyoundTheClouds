package net.martianz.beyondtheclouds.particle;

import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.particle.custom.FadingItemParticleType;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class Particlez {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPEZ = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BeyondTheClouds.MODID);

    public static final DeferredHolder<ParticleType<?>, FadingItemParticleType> METAL_SPARK = PARTICLE_TYPEZ.register("metal_spark", () -> new FadingItemParticleType(false));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPEZ.register(eventBus);
    }

}
