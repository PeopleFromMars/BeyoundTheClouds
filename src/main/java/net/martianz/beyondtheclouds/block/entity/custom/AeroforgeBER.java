package net.martianz.beyondtheclouds.block.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
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
    @Override
    public void render(AeroforgeBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if(blockEntity.hasValidAltar){
            poseStack.pushPose();
            ItemStack stack = new ItemStack(Items.BLUE_CARPET.asItem());
            poseStack.translate(0.5f, -0.25f, 0.5f);
            poseStack.scale(4, 4, 4);
            this.itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(stack, blockEntity.getLevel(), null, 1));
            poseStack.popPose();
        }
    }
}
