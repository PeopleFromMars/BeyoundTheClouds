package net.martianz.beyondtheclouds.block.entity.custom;

import net.martianz.beyondtheclouds.block.entity.BlockEntitiez;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AeroforgeBlockEntity extends BlockEntity {

    public AeroforgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntitiez.AEROFORGE_BE.get(), pos, blockState);
    }

}
