package net.martianz.beyondtheclouds.item.custom;

import net.martianz.beyondtheclouds.component.DataComponentz;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class GateCompass extends CompassItem {

    public GateCompass(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack thisStack = player.getItemInHand(interactionHand);
        if(thisStack.is(Itemz.GATE_COMPASS.get())){
            //TODO Figure out position tracking hier
            thisStack.set(DataComponentz.COORDINATE_COMPONENT, player.blockPosition());
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
