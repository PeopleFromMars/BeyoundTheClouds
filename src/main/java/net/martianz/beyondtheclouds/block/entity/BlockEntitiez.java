package net.martianz.beyondtheclouds.block.entity;

import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.block.Blockz;
import net.martianz.beyondtheclouds.block.entity.custom.AeroforgeBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntitiez {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPEZ = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, BeyondTheClouds.MODID);

    public static final Supplier<BlockEntityType<AeroforgeBlockEntity>> AEROFORGE_BE = BLOCK_ENTITY_TYPEZ.register("aeroforge_block_entity", () -> new BlockEntityType<>(AeroforgeBlockEntity::new, Blockz.AEROFORGE.get()));

    public static void register(IEventBus eventBus){
        BLOCK_ENTITY_TYPEZ.register(eventBus);
    }
}
