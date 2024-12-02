package net.martianz.beyondtheclouds.block;

import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.block.custom.AeroForge;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class Blockz {
    public static final DeferredRegister.Blocks BLOCKZ = DeferredRegister.createBlocks(BeyondTheClouds.MODID);

    public static final DeferredBlock<AeroForge> AEROFORGE = registerWithItem("aeroforge", registryName -> new AeroForge(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .destroyTime(2.0f)
            .explosionResistance(10.0f)
            .sound(SoundType.ANVIL)
            .lightLevel(state -> 7)
    ));

    public static <B extends Block> DeferredBlock<B> registerWithItem(String name, Function<ResourceLocation, ? extends B> func){
        DeferredBlock<B> block = BLOCKZ.register(name, func);
        Itemz.ITEMZ.registerSimpleBlockItem(block);
        return block;
    }

    public static void register(IEventBus eventBus){
        BLOCKZ.register(eventBus);
    }
}