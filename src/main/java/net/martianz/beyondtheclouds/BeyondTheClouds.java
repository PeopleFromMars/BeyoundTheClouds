package net.martianz.beyondtheclouds;

import net.martianz.beyondtheclouds.block.Blockz;
import net.martianz.beyondtheclouds.block.entity.BlockEntitiez;
import net.martianz.beyondtheclouds.component.DataComponentz;
import net.martianz.beyondtheclouds.item.Itemz;
import net.martianz.beyondtheclouds.util.ItemPropertiez;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(BeyondTheClouds.MODID)
public class BeyondTheClouds {

    public static final String MODID = "beyondtheclouds";
    private static final Logger LOGGER = LogUtils.getLogger();

    public BeyondTheClouds(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        Itemz.register(modEventBus);
        Blockz.register(modEventBus);
        BlockEntitiez.register(modEventBus);

        DataComponentz.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemPropertiez.addItemProperties();
        }

    }
}
