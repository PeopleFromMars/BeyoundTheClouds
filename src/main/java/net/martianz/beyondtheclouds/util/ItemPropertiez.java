package net.martianz.beyondtheclouds.util;

import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.component.DataComponentz;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;

public class ItemPropertiez {

    public static void addItemProperties(){
        ItemProperties.register(Itemz.GATE_COMPASS.get(), ResourceLocation.fromNamespaceAndPath(BeyondTheClouds.MODID, "angle"),
                new CompassItemPropertyFunction((clientLevel, stack, entity) -> {
                    BlockPos pos = new BlockPos(1, 1, 1);
                    if(stack.get(DataComponentz.COORDINATE_COMPONENT) != null){
                        pos = stack.get(DataComponentz.COORDINATE_COMPONENT);
                    }
                    return GlobalPos.of(clientLevel.dimension(), pos);
                })
        );
    }

}
