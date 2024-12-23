package net.martianz.beyondtheclouds.particle.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class FadingItemParticle extends TextureSheetParticle {
    private final float uo;
    private final float vo;
    private ItemStack stack = new ItemStack(Items.DIAMOND_SWORD);

    public FadingItemParticle(ClientLevel level, double x, double y, double z, ItemStack stack) {
        this(level, x, y, z, stack, 300);
    }

    public FadingItemParticle(ClientLevel level, double x, double y, double z, ItemStack stack, int lifeTime) {
        super(level, x, y, z);
        this.lifetime = lifeTime;
        this.stack = stack;
        this.setSprite(Minecraft.getInstance().getItemRenderer().getModel(stack, level, null, 0).getParticleIcon(net.neoforged.neoforge.client.model.data.ModelData.EMPTY));
        this.uo = this.random.nextFloat() * 3.0F;
        this.vo = this.random.nextFloat() * 3.0F;
        this.quadSize = 0.1f;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.TERRAIN_SHEET;
    }

    @Override
    public void tick() {
        //fade to blue over time
        float col = ((float) this.age / this.lifetime );
        this.setColor(1.0f-col, 1.0f-col, 1);

        //grow over time
        this.quadSize+=0.002f;

        //zupa
        super.tick();
    }

    public void setStack(ItemLike itemLike){
        this.stack = new ItemStack(itemLike);
    }

    @Override
    protected float getU0() {
        return this.sprite.getU((this.uo + 1.0F) / 4.0F);
    }

    @Override
    protected float getU1() {
        return this.sprite.getU(this.uo / 4.0F);
    }

    @Override
    protected float getV0() {
        return this.sprite.getV(this.vo / 4.0F);
    }

    @Override
    protected float getV1() {
        return this.sprite.getV((this.vo + 1.0F) / 4.0F);
    }

}
