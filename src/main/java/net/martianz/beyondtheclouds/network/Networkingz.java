package net.martianz.beyondtheclouds.network;

import net.martianz.beyondtheclouds.block.entity.custom.AeroforgeBlockEntity;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class Networkingz {


    public static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                AeroforgeBlockEntity.AeroforgeData.TYPE,
                AeroforgeBlockEntity.AeroforgeData.STREAM_CODEC,
                ClientPayloadHandler::handleAeroforgeData);
    }
}
