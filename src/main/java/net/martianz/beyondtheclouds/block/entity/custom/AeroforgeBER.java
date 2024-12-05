package net.martianz.beyondtheclouds.block.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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
    float rotator1 = 0.0f;
    float rotator2 = ((float)Math.PI*2);
    @Override
    public void render(AeroforgeBlockEntity aeroforge, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if(aeroforge.hasValidAltar){
            //render for all lvl altars
            this.rotator1+=0.01f;
            if(this.rotator1 > (2 * Math.PI)) this.rotator1 = 0;

            this.rotator2-=0.01f;
            if(this.rotator2 < 0.0f) this.rotator2 = ((float)Math.PI*2);
            //Carpet under anvil
            poseStack.pushPose();
            ItemStack carpetStack = new ItemStack(Items.BLUE_CARPET.asItem());
            poseStack.translate(0.5f, -0.25f, 0.5f);
            poseStack.scale(4, 4, 4);
            this.itemRenderer.render(carpetStack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(carpetStack, aeroforge.getLevel(), null, 1));
            poseStack.popPose();

            poseStack.pushPose();
            poseStack.translate(0.5f, 0.1f, 0.5f);
            poseStack.scale(2, 2, 2);
            poseStack.mulPose(Axis.YP.rotation(this.rotator1));
            ItemStack effectBaseStack = new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_BASE.get());
            this.itemRenderer.render(effectBaseStack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(effectBaseStack, aeroforge.getLevel(), null, 1));
            poseStack.popPose();


            switch (aeroforge.altarLevel){
                case 1:
                    //render for level 1 altar
                    poseStack.pushPose();
                    poseStack.translate(0.5f, 0.6f, 0.5f);
                    poseStack.mulPose(Axis.YP.rotation(this.rotator2));
                    ItemStack effect1Stack = new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_I.get());
                    this.itemRenderer.render(effect1Stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(effect1Stack, aeroforge.getLevel(), null, 1));
                    poseStack.popPose();
                    break;
                case 2:
                    //render for level 2 altar
                    poseStack.pushPose();
                    poseStack.translate(0.5f, 0.6f, 0.5f);
                    poseStack.mulPose(Axis.YP.rotation(this.rotator2));
                    ItemStack effect2Stack = new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_II.get());
                    this.itemRenderer.render(effect2Stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(effect2Stack, aeroforge.getLevel(), null, 1));
                    poseStack.popPose();
                    break;
            }
        }
    }
}
