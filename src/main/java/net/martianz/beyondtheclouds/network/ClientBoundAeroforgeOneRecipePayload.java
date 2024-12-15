package net.martianz.beyondtheclouds.network;

import io.netty.buffer.ByteBuf;
import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Set;

public record ClientBoundAeroforgeOneRecipePayload (Set<Holder<Item>> inputItems) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ClientBoundAeroforgeOneRecipePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BeyondTheClouds.MODID, "aeroforge_i_recipe_payload"));

    //public static final StreamCodec<ByteBuf, ClientBoundAeroforgeOneRecipePayload> STREAM_CODEC = StreamCodec.composite();

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}