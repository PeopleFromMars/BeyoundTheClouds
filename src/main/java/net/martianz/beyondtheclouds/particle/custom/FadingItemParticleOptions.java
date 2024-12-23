package net.martianz.beyondtheclouds.particle.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.martianz.beyondtheclouds.particle.Particlez;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public class FadingItemParticleOptions implements ParticleOptions {
    public static final MapCodec<FadingItemParticleOptions> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            ItemStack.CODEC.fieldOf("stack1").forGetter(FadingItemParticleOptions::getStack),
            Codec.INT.fieldOf("lifetime").forGetter(FadingItemParticleOptions::getLifeTime)
            ).apply(inst, FadingItemParticleOptions::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FadingItemParticleOptions> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, FadingItemParticleOptions::getStack,
            ByteBufCodecs.VAR_INT, FadingItemParticleOptions::getLifeTime,
            FadingItemParticleOptions::new);

    public ItemStack stack;
    public int lifeTime;

    public FadingItemParticleOptions(ItemStack stack, int lifeTime){
        this.stack = stack;
        this.lifeTime = lifeTime;
    }

    @Override
    public ParticleType<?> getType() {
        return Particlez.METAL_SPARK.get();
    }

    public ItemStack getStack(){
        return this.stack;
    }

    public int getLifeTime(){
        return this.lifeTime;
    }
}
