package net.martianz.beyondtheclouds.network;

import net.martianz.beyondtheclouds.block.entity.custom.AeroforgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {

    public static void handleAeroforgeData(final AeroforgeBlockEntity.AeroforgeData data, final IPayloadContext context){
        BlockEntity be = context.player().level().getBlockEntity(new BlockPos(data.x(), data.y(), data.z()));
        if(be instanceof AeroforgeBlockEntity forge){
            forge.standby = data.standby();
            forge.craftingTimer = data.craftingTimer();
            forge.itemSelector = data.itemSelector();
        }
    }

}
