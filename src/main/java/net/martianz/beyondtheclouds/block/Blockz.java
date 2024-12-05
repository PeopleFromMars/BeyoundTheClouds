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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public class Blockz {
    public static final DeferredRegister.Blocks BLOCKZ = DeferredRegister.createBlocks(BeyondTheClouds.MODID);

    public static final DeferredBlock<AeroForge> AEROFORGE = registerWithItem("aeroforge", registryName -> new AeroForge(BlockBehaviour.Properties.of()
            .setId(ResourceKey.create(Registries.BLOCK, registryName))
            .mapColor(MapColor.METAL)
            .requiresCorrectToolForDrops()
            .strength(5.0F, 1200.0F)
            .sound(SoundType.ANVIL)
            .pushReaction(PushReaction.BLOCK)
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