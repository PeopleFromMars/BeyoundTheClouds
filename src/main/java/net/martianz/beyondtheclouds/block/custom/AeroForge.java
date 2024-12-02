package net.martianz.beyondtheclouds.block.custom;

import net.martianz.beyondtheclouds.block.entity.custom.AeroforgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AeroForge extends Block implements EntityBlock {

    public AeroForge(BlockBehaviour.Properties properties){
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new AeroforgeBlockEntity(pos, state);
    }
}
