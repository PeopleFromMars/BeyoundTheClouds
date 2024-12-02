package net.martianz.beyondtheclouds.item;

import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.block.Blockz;
import net.martianz.beyondtheclouds.item.custom.GateCompass;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;;

public class Itemz {
    public static final DeferredRegister.Items ITEMZ = DeferredRegister.createItems(BeyondTheClouds.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BeyondTheClouds.MODID);

    public static final DeferredItem<Item> GATE_COMPASS = ITEMZ.registerItem("gate_compass", GateCompass::new, new Item.Properties());


    public static final Supplier<CreativeModeTab> CLOUDREALM_TAB = CREATIVE_TAB.register("cloud_realm_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(GATE_COMPASS.get()))
                    .title(Component.translatable("creativetab.beyondtheclouds.cloudrealm_tab"))
                    .displayItems((itemDisplayParamaters, output) -> {
                        output.accept(GATE_COMPASS.get());
                        output.accept(Blockz.AEROFORGE.get());
                    }).build());

    public static void register(IEventBus eventBus){
        ITEMZ.register(eventBus);
        CREATIVE_TAB.register(eventBus);
    }
}
