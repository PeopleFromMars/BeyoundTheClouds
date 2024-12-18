package net.martianz.beyondtheclouds.block.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.Util;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.renderable.BakedModelRenderable;
import org.joml.Vector3f;

public class AeroforgeBER implements BlockEntityRenderer<AeroforgeBlockEntity> {
    ItemRenderer itemRenderer;

    public AeroforgeBER(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public boolean shouldRender(AeroforgeBlockEntity blockEntity, Vec3 cameraPos) {
        return BlockEntityRenderer.super.shouldRender(blockEntity, cameraPos);
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

            if(!aeroforge.standby){
                float hammerYP = 0.0f;
                Vector3f hammerPos = new Vector3f(0.5f, 0.0f, 0.5f);
                Vector3f selectorPos = new Vector3f(0.5f, 0.0f, 0.5f);
                switch (aeroforge.itemSelector){
                    case 0:
                        selectorPos = new Vector3f(4.5f, 1.0f, 0.5f);
                        hammerPos = new Vector3f(5.5f, 1.0f, 0.5f);
                        hammerYP = (float)Math.PI/2.0f;

                        break;
                    case 1:
                        selectorPos = new Vector3f(-3.5f, 1.0f, 0.5f);
                        hammerPos = new Vector3f(-4.5f, 1.0f, 0.5f);
                        hammerYP = -1*(float)Math.PI/2.0f;
                        break;
                    case 2:
                        selectorPos = new Vector3f(0.5f, 1.0f, 4.5f);
                        hammerPos = new Vector3f(0.5f, 1.0f, 5.5f);
                        hammerYP = 0;
                        break;
                    case 3:
                        selectorPos = new Vector3f(0.5f, 1.0f, -3.5f);
                        hammerPos = new Vector3f(0.5f, 1.0f, -4.5f);
                        hammerYP = (float)Math.PI;
                        break;
                    case 4:
                        selectorPos = new Vector3f(3.5f, 2.0f, -2.5f);
                        hammerPos = new Vector3f(4.5f, 2.0f, -3.5f);
                        hammerYP = 3.0f*(float)Math.PI/4.0f;
                        break;
                    case 5:
                        selectorPos = new Vector3f(-2.5f, 2.0f, -2.5f);
                        hammerPos = new Vector3f(-3.5f, 2.0f, -3.5f);
                        hammerYP = 5.0f*(float)Math.PI/4.0f;
                        break;
                    case 6:
                        selectorPos = new Vector3f(-2.5f, 2.0f, 3.5f);
                        hammerPos = new Vector3f(-3.5f, 2.0f, 4.5f);
                        hammerYP = -1.0f*(float)Math.PI/4.0f;
                        break;
                    case 7:
                        selectorPos = new Vector3f(3.5f, 2.0f, 3.5f);
                        hammerPos = new Vector3f(4.5f, 2.0f, 4.5f);
                        hammerYP = (float)Math.PI/4.0f;
                        break;
                }

                //selector ring
                poseStack.pushPose();
                poseStack.translate(selectorPos.x, selectorPos.y, selectorPos.z);
                poseStack.mulPose(Axis.YP.rotation(aeroforge.rotator1));
                renderItem(Itemz.AEROFORGE_EFFECT_LAYER_SELECTOR.get(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());
                poseStack.popPose();

                //hammer
                poseStack.pushPose();
                poseStack.translate(hammerPos.x, hammerPos.y, hammerPos.z);
                //2*PI radians is 360° btw
                poseStack.mulPose(Axis.YP.rotation(hammerYP));
                double D1 = ((Math.sin((aeroforge.craftingTimer/(((double)AeroforgeBlockEntity.RECIPE_TIME_TICKS/8)/Math.PI)) - (Math.PI/2)))*0.5f)+0.5f;
                double D1eased = easeInOutCirc(D1);
                float hammerXN = (float) (D1eased * (0.1f+Math.PI/2.0f));
                poseStack.mulPose(Axis.XN.rotation(hammerXN));
                renderItem(Itemz.AEROFORGE_EFFECT_LAYER_HAMMER.get(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());
                //TODO transparency: fade in the hammer
                //bufferSource.getBuffer(RenderType.TRANSLUCENT).setWhiteAlpha(Math.toIntExact((long) (D1*24)));
                poseStack.popPose();
            }
        }

    }

    public void renderItem(Item item, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Level level){
        ItemStack stack = new ItemStack(item);
        this.itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, this.itemRenderer.getModel(stack, level, null, 1));

    }

    public static double easeInExpo(double x) {
        if (x == 0) {
            return 0;
        } else {
            return Math.pow(2, 10 * x - 10);
        }
    }

    public static double easeInOutCirc(double x) {
        if (x < 0.5) {
            return (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2;
        } else {
            return (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2;
        }
    }

    public static double easeInQuint(double x) {
        return x * x * x * x * x;
    }

}
