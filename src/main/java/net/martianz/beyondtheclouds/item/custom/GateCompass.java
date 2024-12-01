package net.martianz.beyondtheclouds.item.custom;

import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.component.DataComponentz;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
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
                BlockPos origin = new BlockPos(player.blockPosition().getX(), 0, player.blockPosition().getZ());
                BlockPos highestWithin = origin;

                for (BlockPos choice : BlockPos.betweenClosed(new BlockPos(origin.getX()-1000, 0, origin.getZ()-1000), new BlockPos(origin.getX()+1000, 0, origin.getZ()+1000))) {
                    int choiceY = level.getHeight(Heightmap.Types.WORLD_SURFACE, choice.getX(), choice.getZ());
                    if(choiceY >= highestWithin.getY()){
                        highestWithin = new BlockPos(choice.getX(), choiceY, choice.getZ());
                    }
                }

                if(highestWithin==origin){
                    player.displayClientMessage(Component.translatable("clientmsg."+BeyondTheClouds.MODID +".entry_point_locating.fail").withStyle(ChatFormatting.RED), true);
                }else{
                    thisStack.set(DataComponentz.COORDINATE_COMPONENT, highestWithin);
                    player.displayClientMessage(Component.translatable("clientmsg."+BeyondTheClouds.MODID +".entry_point_locating.success").withStyle(ChatFormatting.GREEN), true);
                }
            }
        }
        return super.use(level, player, interactionHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(DataComponentz.COORDINATE_COMPONENT) != null){
            BlockPos pos=stack.get(DataComponentz.COORDINATE_COMPONENT);
            tooltipComponents.add(Component.translatable("tooltip."+BeyondTheClouds.MODID+".gate_compass.target").append( ": x:" + pos.getX() +", y:" + pos.getY()+ ", z:" +pos.getZ()).withStyle(ChatFormatting.GREEN));
        }else{
            tooltipComponents.add(Component.translatable("tooltip."+BeyondTheClouds.MODID+".gate_compass.info").withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
