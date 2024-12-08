package net.martianz.beyondtheclouds.block.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.joml.Vector3f;

public class AeroforgeBER implements BlockEntityRenderer<AeroforgeBlockEntity> {
    ItemRenderer itemRenderer;

    public AeroforgeBER(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    // - partialTick:   The amount of time, in fractions of a tick (0.0 to 1.0), that has passed since the last tick.
    // - poseStack:     The pose stack to render to.
    // - bufferSource:  The buffer source to get vertex buffers from.
    // - packedLight:   The light value of the block entity.
    // - packedOverlay: The current overlay value of the block entity, usually OverlayTexture.NO_OVERLAY.

    @Override
    public void render(AeroforgeBlockEntity aeroforge, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if(aeroforge.hasValidAltar){

            //Carpet under anvil
            poseStack.pushPose();
            ItemStack carpetStack = new ItemStack(Items.BLUE_CARPET.asItem());
            poseStack.translate(0.5f, -0.25f, 0.5f);
            poseStack.scale(4, 4, 4);
            this.itemRenderer.render(carpetStack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(carpetStack, aeroforge.getLevel(), null, 1));
            poseStack.popPose();

            //Base ring
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.1f, 0.5f);
            poseStack.scale(2, 2, 2);
            poseStack.mulPose(Axis.YP.rotation(aeroforge.rotator1));
            ItemStack effectBaseStack = new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_BASE.get());
            this.itemRenderer.render(effectBaseStack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(effectBaseStack, aeroforge.getLevel(), null, 1));
            poseStack.popPose();

            //level ring
            poseStack.pushPose();
            poseStack.translate(0.5f, 0.6f, 0.5f);
            poseStack.mulPose(Axis.YP.rotation(aeroforge.rotator2));
            ItemStack effect2Stack = new ItemStack(Blocks.AIR.asItem());
            switch (aeroforge.altarLevel){
                case 1:
                    effect2Stack = new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_I.get());
                    break;
                case 2:
                    effect2Stack = new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_II.get());
                    break;
            }
            this.itemRenderer.render(effect2Stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(effect2Stack, aeroforge.getLevel(), null, 1));
            poseStack.popPose();

            //contained items
            poseStack.pushPose();
            poseStack.translate(4.5f, 0.5f, 0.5f);
            renderItem(aeroforge.getItem(0).getItem(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());

            poseStack.translate(-8.0f, 0.0f, 0.0f);
            renderItem(aeroforge.getItem(1).getItem(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());

            poseStack.translate(4.0f, 0.0f, 4.0f);
            renderItem(aeroforge.getItem(2).getItem(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());

            poseStack.translate(0.0f, 0.0f, -8.0f);
            renderItem(aeroforge.getItem(3).getItem(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());

            poseStack.translate(3.0f, 1.0f, 1.0f);
            renderItem(aeroforge.getItem(4).getItem(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());

            poseStack.translate(-6.0f, 0.0f, 0.0f);
            renderItem(aeroforge.getItem(5).getItem(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());

            poseStack.translate(0.0f, 0.0f, 6.0f);
            renderItem(aeroforge.getItem(6).getItem(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());

            poseStack.translate(6.0f, 0.0f, 0.0f);
            renderItem(aeroforge.getItem(7).getItem(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());
            poseStack.popPose();


            //selector ring
            Vector3f selectorPos = new Vector3f(0.5f, 0.0f, 0.5f);
            switch (aeroforge.itemSelector){
                case 0: selectorPos = new Vector3f(4.5f, 1.0f, 0.5f); break;
                case 1: selectorPos = new Vector3f(-3.5f, 1.0f, 0.5f); break;
                case 2: selectorPos = new Vector3f(0.5f, 1.0f, 4.5f); break;
                case 3: selectorPos = new Vector3f(0.5f, 1.0f, -3.5f); break;
                case 4: selectorPos = new Vector3f(3.5f, 2.0f, -2.5f); break;
                case 5: selectorPos = new Vector3f(-2.5f, 2.0f, -2.5f); break;
                case 6: selectorPos = new Vector3f(-2.5f, 2.0f, 3.5f); break;
                case 7: selectorPos = new Vector3f(3.5f, 2.0f, 3.5f); break;
            }
            poseStack.pushPose();
            poseStack.translate(selectorPos.x, selectorPos.y, selectorPos.z);
            poseStack.mulPose(Axis.YP.rotation(aeroforge.rotator1));
            renderItem(Itemz.AEROFORGE_EFFECT_LAYER_SELECTOR.get(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());
            poseStack.popPose();
        }

    }

    public void renderItem(Item item, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Level level){
        ItemStack stack = new ItemStack(item);
        this.itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, this.itemRenderer.getModel(stack, level, null, 1));

    }
}
