package net.martianz.beyondtheclouds.item.custom;

import net.martianz.beyondtheclouds.component.DataComponentz;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;

public class GateCompass extends CompassItem {

    public GateCompass(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        if(!level.isClientSide){
            ItemStack thisStack = player.getItemInHand(interactionHand);
            if(thisStack.is(Itemz.GATE_COMPASS.get())){
                BlockPos origin = player.blockPosition();
                BlockPos highestWithin = origin;
                for (int i = 0; i < 30; i++) {
                    RandomSource rnd = level.getRandom();
                    BlockPos randomPos = new BlockPos(rnd.nextInt(origin.getX()-500, origin.getX()+500), 320,rnd.nextInt(origin.getZ()-500, origin.getZ()+500));
                    BlockPos validHighest = new BlockPos(randomPos.getX(), level.getHeight(Heightmap.Types.MOTION_BLOCKING, randomPos.getX(), randomPos.getY()), randomPos.getZ());
                    BlockState randomState = level.getBlockState(validHighest);
                    if(validHighest.getY() > highestWithin.getY()){
                        highestWithin = randomPos;;
                    }
                }
                if(highestWithin==origin){
                    System.out.println("roll again");
                }else{
                    thisStack.set(DataComponentz.COORDINATE_COMPONENT, highestWithin);
                }
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(DataComponentz.COORDINATE_COMPONENT) != null){
            BlockPos pos=stack.get(DataComponentz.COORDINATE_COMPONENT);
            tooltipComponents.add(Component.translatable("tooltip.gate_compass.target").append( ": x:" + pos.getX() +", y:" + pos.getY()+ ", z:" +pos.getZ()).withStyle(ChatFormatting.GRAY));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
