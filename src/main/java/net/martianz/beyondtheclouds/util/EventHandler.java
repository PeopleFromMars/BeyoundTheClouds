package net.martianz.beyondtheclouds.util;

import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.block.Blockz;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;

@EventBusSubscriber(modid = BeyondTheClouds.MODID)
public class EventHandler {

    @SubscribeEvent
    public static void onMaceFalls(AttackEntityEvent event){
        Level level = event.getEntity().level();
        if(!level.isClientSide){
            Entity target = event.getTarget();
            Entity attacker = event.getEntity();
            ItemStack handStack = attacker.getWeaponItem();

            if(target instanceof Sheep sheep && attacker instanceof Player player && handStack.is(Items.MACE)){
                if(player.fallDistance > 1.5F && !player.isFallFlying()){
                    BlockPos origin = sheep.blockPosition();
                    if(level.getBlockState(origin).getBlock() == Blocks.BLUE_CARPET){
                        //Initiate ritual I.
                        BlockPos anvilPos = origin.below();
                        BlockPos goldPos = anvilPos.below();
                        Direction anvilDirection = level.getBlockState(anvilPos).getValue(HorizontalDirectionalBlock.FACING);

                        boolean isAnvilPresent = level.getBlockState(anvilPos).getBlock() == Blocks.ANVIL;
                        boolean isGoldBelowAnvil = level.getBlockState(goldPos).getBlock() == Blocks.GOLD_BLOCK;
                        boolean areThereCrystals = level.getBlockState(goldPos.east()).getBlock() == Blocks.AMETHYST_CLUSTER &&
                                level.getBlockState(goldPos.west()).getBlock() == Blocks.AMETHYST_CLUSTER &&
                                level.getBlockState(goldPos.north()).getBlock() == Blocks.AMETHYST_CLUSTER &&
                                level.getBlockState(goldPos.south()).getBlock() == Blocks.AMETHYST_CLUSTER;
                        boolean areThereCarpets = level.getBlockState(goldPos.east().north()).getBlock() == Blocks.BLUE_CARPET &&
                                level.getBlockState(goldPos.east().south()).getBlock() == Blocks.BLUE_CARPET &&
                                level.getBlockState(goldPos.west().north()).getBlock() == Blocks.BLUE_CARPET &&
                                level.getBlockState(goldPos.west().south()).getBlock() == Blocks.BLUE_CARPET;

                        if(isAnvilPresent && isGoldBelowAnvil && areThereCarpets && areThereCrystals){

                            level.setBlockAndUpdate(origin, Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(anvilPos, Blockz.AEROFORGE.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, anvilDirection));
                            level.setBlockAndUpdate(goldPos.east(), Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(goldPos.west(), Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(goldPos.north(), Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(goldPos.south(), Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(goldPos, Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(goldPos.east().north(), Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(goldPos.east().south(), Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(goldPos.west().north(), Blocks.AIR.defaultBlockState());
                            level.setBlockAndUpdate(goldPos.west().south(), Blocks.AIR.defaultBlockState());

                            LightningBolt lightningbolt = EntityType.LIGHTNING_BOLT.create(level, EntitySpawnReason.TRIGGERED);
                            if (lightningbolt != null) {
                                lightningbolt.moveTo(anvilPos.getX(), anvilPos.getY(), anvilPos.getZ());
                                lightningbolt.setVisualOnly(true);
                                level.addFreshEntity(lightningbolt);
                            }

                            sheep.setPos(sheep.blockPosition().getX(), -1000, sheep.blockPosition().getZ());
                        }
                    }
                }
            }

        }
    }
}
