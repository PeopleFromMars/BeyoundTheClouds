package net.martianz.beyondtheclouds.block.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.item.Itemz;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class AeroforgeBER implements BlockEntityRenderer<AeroforgeBlockEntity> {
    ItemRenderer itemRenderer;
    public static final ItemStack HAMMER_STACK = new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_HAMMER.get());

    public AeroforgeBER(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
        //this.itemRenderer2 = (ItemRenderer2) context.getItemRenderer();
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
        //Base ring
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.1f, 0.5f);
        float scale = 2*aeroforge.onTimer;
        poseStack.scale(scale, scale, scale);
        poseStack.mulPose(Axis.YP.rotation(aeroforge.rotator1));
        ItemStack effectBaseStack = new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_BASE.get());
        renderItemWithOpacity(effectBaseStack, aeroforge.onTimer, poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());
        poseStack.popPose();

        //level ring
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.6f, 0.5f);
        poseStack.scale(aeroforge.onTimer, aeroforge.onTimer, aeroforge.onTimer);
        poseStack.mulPose(Axis.YP.rotation(aeroforge.rotator2));
        new ItemStack(Blocks.AIR.asItem());
        ItemStack effect2Stack = switch (aeroforge.altarLevel) {
            case 2 -> new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_II.get());
            default -> new ItemStack(Itemz.AEROFORGE_EFFECT_LAYER_I.get());
        };
        renderItemWithOpacity(effect2Stack, aeroforge.onTimer, poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());
        poseStack.popPose();

        if(aeroforge.hasValidAltar){

            //Carpet under anvil
            poseStack.pushPose();
            ItemStack carpetStack = new ItemStack(Items.BLUE_CARPET.asItem());
            poseStack.translate(0.5f, -0.25f, 0.5f);
            poseStack.scale(4, 4, 4);
            this.itemRenderer.render(carpetStack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, itemRenderer.getModel(carpetStack, aeroforge.getLevel(), null, 1));
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

            //Selector ring
            poseStack.pushPose();
            double lerpVal = thirtiethPowEase(aeroforge.craftingTimer/((double)AeroforgeBlockEntity.RECIPE_TIME_TICKS/4) % 1);
            boolean shouldStopAfter4 = true;
            if(aeroforge.altarLevel == 2) shouldStopAfter4 = false;
            int selectorI = shouldStopAfter4 && aeroforge.itemSelector > 3 || aeroforge.standby ? 10 : aeroforge.itemSelector;
            Vector3f selectorPos = lerpTriple(aeroforge.standby ? 0 : 1-lerpVal, getSelectorPos(selectorI+1), getSelectorPos(selectorI));
            poseStack.translate(selectorPos.x, selectorPos.y, selectorPos.z);
            poseStack.mulPose(Axis.YP.rotation(aeroforge.rotator1));
            renderItem(Itemz.AEROFORGE_EFFECT_LAYER_SELECTOR.get(), poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());
            poseStack.popPose();

            if(!aeroforge.standby){
                float hammerYP = 0.0f;
                Vector3f hammerPos = new Vector3f(0.5f, 0.0f, 0.5f);

                switch (aeroforge.itemSelector){
                    case 0:
                        hammerPos = new Vector3f(5.5f, 1.0f, 0.5f);
                        hammerYP = (float)Math.PI/2.0f;
                        break;
                    case 1:
                        hammerPos = new Vector3f(-4.5f, 1.0f, 0.5f);
                        hammerYP = -1*(float)Math.PI/2.0f;
                        break;
                    case 2:
                        hammerPos = new Vector3f(0.5f, 1.0f, 5.5f);
                        hammerYP = 0;
                        break;
                    case 3:
                        hammerPos = new Vector3f(0.5f, 1.0f, -4.5f);
                        hammerYP = (float)Math.PI;
                        break;
                    case 4:
                        hammerPos = new Vector3f(4.5f, 2.0f, -3.5f);
                        hammerYP = 3.0f*(float)Math.PI/4.0f;
                        break;
                    case 5:
                        hammerPos = new Vector3f(-3.5f, 2.0f, -3.5f);
                        hammerYP = 5.0f*(float)Math.PI/4.0f;
                        break;
                    case 6:
                        hammerPos = new Vector3f(-3.5f, 2.0f, 4.5f);
                        hammerYP = -1.0f*(float)Math.PI/4.0f;
                        break;
                    case 7:
                        hammerPos = new Vector3f(4.5f, 2.0f, 4.5f);
                        hammerYP = (float)Math.PI/4.0f;
                        break;
                }

                //hammer
                poseStack.pushPose();
                poseStack.translate(hammerPos.x, hammerPos.y, hammerPos.z);
                //2*PI radians is 360° btw
                poseStack.mulPose(Axis.YP.rotation(hammerYP));
                double D1 = ((Math.sin((aeroforge.craftingTimer/(((double)AeroforgeBlockEntity.RECIPE_TIME_TICKS/8)/Math.PI)) - (Math.PI/2)))*0.5f)+0.5f;
                double D1eased = easeInOutCirc(D1);
                float hammerXN = (float) (D1eased * (0.1f+Math.PI/2.0f));
                poseStack.mulPose(Axis.XN.rotation(hammerXN));
                renderItemWithOpacity(HAMMER_STACK, (float) D1, poseStack, bufferSource, packedLight, packedOverlay, aeroforge.getLevel());
                poseStack.popPose();
            }
        }

    }

    public void renderItemWithOpacity(ItemStack stack, float alpha, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, Level level){
        CompoundTag tag = new CompoundTag();
        tag.putFloat(BeyondTheClouds.MODID +"_opacity", alpha);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
        this.itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, this.itemRenderer.getModel(stack, level, null, 1));
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

    public static Vector3f getSelectorPos(int itemSelector){
        return switch (itemSelector) {
            case 0 -> new Vector3f(4.5f, 1.0f, 0.5f);
            case 1 -> new Vector3f(-3.5f, 1.0f, 0.5f);
            case 2 -> new Vector3f(0.5f, 1.0f, 4.5f);
            case 3 -> new Vector3f(0.5f, 1.0f, -3.5f);
            case 4 -> new Vector3f(3.5f, 2.0f, -2.5f);
            case 5 -> new Vector3f(-2.5f, 2.0f, -2.5f);
            case 6 -> new Vector3f(-2.5f, 2.0f, 3.5f);
            case 7 -> new Vector3f(3.5f, 2.0f, 3.5f);
            default -> new Vector3f(0.5f, 0.0f, 0.5f);
        };
    }

    public static Vector3f lerpTriple(double fraction, Vector3f start, Vector3f end){
        return new Vector3f(lerp(start.x, end.x, fraction), lerp(start.y, end.y, fraction), lerp(start.z, end.z, fraction));
    }

    public static float lerp(float a, float b, double fraction) {
        return (float) (a * (1.0 - fraction) + (b * fraction));
    }

    public static double thirtiethPowEase(double x){
        return Math.pow(x, 30);
    }
}
