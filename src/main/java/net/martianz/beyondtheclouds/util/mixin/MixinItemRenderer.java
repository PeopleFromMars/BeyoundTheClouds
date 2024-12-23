package net.martianz.beyondtheclouds.util.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer implements ResourceManagerReloadListener{

    @Shadow @Final private ItemColors itemColors;

    @Shadow @Final private ItemModelShaper itemModelShaper;


    /**
     * @author
     * @reason
     */
    @Overwrite
    public void renderQuadList(PoseStack poseStack, VertexConsumer buffer, List<BakedQuad> quads, ItemStack itemStack, int combinedLight, int combinedOverlay) {
        boolean flag = !itemStack.isEmpty();
        PoseStack.Pose posestack$pose = poseStack.last();

        for (BakedQuad bakedquad : quads) {
            int i = -1;
            if (flag && bakedquad.isTinted()) {
                i = this.itemColors.getColor(itemStack, bakedquad.getTintIndex());
            }

            float f = (float) ARGB.alpha(i) / 255.0F;
            float f1 = (float)ARGB.red(i) / 255.0F;
            float f2 = (float)ARGB.green(i) / 255.0F;
            float f3 = (float)ARGB.blue(i) / 255.0F;
            CustomData data = itemStack.get(DataComponents.CUSTOM_DATA);
            float alpha = 1;
            if(data != null){
                CompoundTag tag = data.copyTag();
                if(tag.contains(BeyondTheClouds.MODID +"_opacity")){
                    alpha = tag.getFloat(BeyondTheClouds.MODID +"_opacity");
                }
            }
            buffer.putBulkData(posestack$pose, bakedquad, f1, f2, f3, f*alpha, combinedLight, combinedOverlay, true); // Neo: pass readExistingColor=true
        }
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        this.itemModelShaper.invalidateCache();
    }

}
