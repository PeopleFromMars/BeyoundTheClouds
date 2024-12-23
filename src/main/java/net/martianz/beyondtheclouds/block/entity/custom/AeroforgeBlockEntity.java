package net.martianz.beyondtheclouds.block.entity.custom;

import io.netty.buffer.ByteBuf;
import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.block.entity.BlockEntitiez;
import net.martianz.beyondtheclouds.particle.Particlez;
import net.martianz.beyondtheclouds.particle.custom.FadingItemParticle;
import net.martianz.beyondtheclouds.particle.custom.FadingItemParticleOptions;
import net.martianz.beyondtheclouds.particle.custom.FadingItemParticleProvider;
import net.martianz.beyondtheclouds.recipe.Recipez;
import net.martianz.beyondtheclouds.recipe.custom.AeroforgeInput;
import net.martianz.beyondtheclouds.recipe.custom.AeroforgeOneRecipe;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Optional;

public class AeroforgeBlockEntity extends BlockEntity implements Container {
    //stored values
    public Boolean hasValidAltar = false;
    public int altarLevel = 0;
    private final NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
    public Boolean standby = true;

    //unstored but packet-synced to client
    public int craftingTimer = 0;
    public int itemSelector = 0;

    //maths
    public static final int RECIPE_TIME_TICKS = 300; //l
    private static final float C = (float) 4 /RECIPE_TIME_TICKS;
    public static final float ANIM_ON_TIME = 1.0f;


    //unstored values for some reason
    float rotator1 = 0.0f;
    float rotator2 = ((float)Math.PI*2);
    ItemStack result = ItemStack.EMPTY;
    int hitCount = 0;
    float onTimer = 0;

    public record AeroforgeData(int x, int y, int z, Boolean standby, int craftingTimer, int itemSelector, Boolean shouldCleanse) implements CustomPacketPayload{
        public static final CustomPacketPayload.Type<AeroforgeData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BeyondTheClouds.MODID, "aeroforge_data"));
        public static final StreamCodec<ByteBuf, AeroforgeData> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT,
                AeroforgeData::x,
                ByteBufCodecs.VAR_INT,
                AeroforgeData::y,
                ByteBufCodecs.VAR_INT,
                AeroforgeData::z,
                ByteBufCodecs.BOOL,
                AeroforgeData::standby,
                ByteBufCodecs.VAR_INT,
                AeroforgeData::craftingTimer,
                ByteBufCodecs.VAR_INT,
                AeroforgeData::itemSelector,
                ByteBufCodecs.BOOL,
                AeroforgeData::shouldCleanse,
                AeroforgeData::new
        );

        @Override
        public @NotNull Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static AeroforgeData getDataFromForge(AeroforgeBlockEntity forge, boolean shouldCleanse){
            return new AeroforgeData(forge.getBlockPos().getX(), forge.getBlockPos().getY(), forge.getBlockPos().getZ(), forge.standby, forge.craftingTimer, forge.itemSelector, shouldCleanse);
        }
    }

    public AeroforgeBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntitiez.AEROFORGE_BE.get(), pos, blockState);
    }

    public boolean checkAltarValidity(Level level, BlockPos anvilPos){
        this.hasValidAltar = false;
        this.altarLevel = 0;

        if(level.getBlockState(anvilPos.below()).getBlock() == Blocks.GOLD_BLOCK){ //Check if theres gold below so we dont do a lot of if checks until theres proof that there might be a full altar
            boolean hasUniversalBase =
                    //Gold blocks
                    level.getBlockState(anvilPos.below()).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().east()).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().east().north()).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().east().south()).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().north()).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().south()).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().west()).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().west().north()).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().west().south()).getBlock() == Blocks.GOLD_BLOCK &&
                    //carpets on top of gold
                    level.getBlockState(anvilPos.east()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.east().north()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.east().south()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.north()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.south()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.west()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.west().north()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.west().south()).getBlock() == Blocks.BLUE_CARPET &&
                    //carpets 3 blocks away on the cardinals, on the level of gold blocks
                    level.getBlockState(anvilPos.below().east(3)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west(3)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().south(3)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().north(3)).getBlock() == Blocks.BLUE_CARPET &&
                    //carpets beside the gold blocks, on the level of gold blocks
                    level.getBlockState(anvilPos.below().east(2).north()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().east(2).north(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().east().north(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().east(2).south()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().east(2).south(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().east().south(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west(2).south()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west(2).south(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west().south(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west(2).north()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west(2).north(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west().north(2)).getBlock() == Blocks.BLUE_CARPET &&
                    //the four andesite pedestals
                    level.getBlockState(anvilPos.below().east(4)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.east(4)).getBlock() == Blocks.POLISHED_ANDESITE_SLAB &&
                    level.getBlockState(anvilPos.below().west(4)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.west(4)).getBlock() == Blocks.POLISHED_ANDESITE_SLAB &&
                    level.getBlockState(anvilPos.below().north(4)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.north(4)).getBlock() == Blocks.POLISHED_ANDESITE_SLAB &&
                    level.getBlockState(anvilPos.below().south(4)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.south(4)).getBlock() == Blocks.POLISHED_ANDESITE_SLAB
            ;
            boolean hasLvl1 =
                    //four carpets
                    level.getBlockState(anvilPos.below().east(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().north(2)).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().south(2)).getBlock() == Blocks.BLUE_CARPET
            ;
            boolean hasLvl2 =
                    //four gold blocks in place of the carpets
                    level.getBlockState(anvilPos.below().east(2)).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().west(2)).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().north(2)).getBlock() == Blocks.GOLD_BLOCK &&
                    level.getBlockState(anvilPos.below().south(2)).getBlock() == Blocks.GOLD_BLOCK &&
                    //eight blue carpets on the cardinals, two on each axis
                    level.getBlockState(anvilPos.below().east(3).north()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().east(3).south()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west(3).north()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().west(3).south()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().north(3).west()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().north(3).east()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().south(3).west()).getBlock() == Blocks.BLUE_CARPET &&
                    level.getBlockState(anvilPos.below().south(3).east()).getBlock() == Blocks.BLUE_CARPET &&
                    //eight candles
                    level.getBlockState(anvilPos.below().east(4).north(2)).getBlock() == Blocks.WHITE_CANDLE &&
                    level.getBlockState(anvilPos.below().east(2).north(4)).getBlock() == Blocks.WHITE_CANDLE &&
                    level.getBlockState(anvilPos.below().east(4).south(2)).getBlock() == Blocks.WHITE_CANDLE &&
                    level.getBlockState(anvilPos.below().east(2).south(4)).getBlock() == Blocks.WHITE_CANDLE &&
                    level.getBlockState(anvilPos.below().west(4).north(2)).getBlock() == Blocks.WHITE_CANDLE &&
                    level.getBlockState(anvilPos.below().west(2).north(4)).getBlock() == Blocks.WHITE_CANDLE &&
                    level.getBlockState(anvilPos.below().west(4).south(2)).getBlock() == Blocks.WHITE_CANDLE &&
                    level.getBlockState(anvilPos.below().west(2).south(4)).getBlock() == Blocks.WHITE_CANDLE &&
                    //four additional pedestals
                    level.getBlockState(anvilPos.below().east(3).north(3)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.east(3).north(3)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.above().east(3).north(3)).getBlock() == Blocks.POLISHED_ANDESITE_SLAB &&
                    level.getBlockState(anvilPos.below().east(3).south(3)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.east(3).south(3)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.above().east(3).south(3)).getBlock() == Blocks.POLISHED_ANDESITE_SLAB &&
                    level.getBlockState(anvilPos.below().west(3).north(3)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.west(3).north(3)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.above().west(3).north(3)).getBlock() == Blocks.POLISHED_ANDESITE_SLAB &&
                    level.getBlockState(anvilPos.below().west(3).south(3)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.west(3).south(3)).getBlock() == Blocks.ANDESITE_WALL &&
                    level.getBlockState(anvilPos.above().west(3).south(3)).getBlock() == Blocks.POLISHED_ANDESITE_SLAB
            ;

            if(hasUniversalBase && hasLvl1){
                this.hasValidAltar = true;
                this.altarLevel = 1;
            }else if(hasUniversalBase && hasLvl2){
                this.hasValidAltar = true;
                this.altarLevel = 2;
            }
        }

        level.sendBlockUpdated(anvilPos, level.getBlockState(anvilPos), level.getBlockState(anvilPos), Block.UPDATE_ALL_IMMEDIATE);
        return this.hasValidAltar;
    }

    public int getAltarLevel(){
        return this.altarLevel;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.hasValidAltar = tag.getBoolean("hasValidAltar");
        this.altarLevel = tag.getInt("altarLevel");
        ContainerHelper.loadAllItems(tag, this.items, registries);
        this.standby = tag.getBoolean("standby");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("hasValidAltar", this.hasValidAltar);
        tag.putInt("altarLevel", this.altarLevel);
        ContainerHelper.saveAllItems(tag, this.items, registries);
        tag.putBoolean("standby", this.standby);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
        this.saveAdditional(packet.getTag(), registries);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(blockEntity instanceof AeroforgeBlockEntity aeroforge){
            //Crafting sequence
            if(!aeroforge.standby){
                float n = (float) RECIPE_TIME_TICKS /4.0f;
                float calc = (float)(C*(n*Math.floor(aeroforge.craftingTimer/n)));
                aeroforge.itemSelector = Math.round(calc);
                aeroforge.setChanged();
                aeroforge.updateForgeOnClient(aeroforge, level, false);

                if(aeroforge.craftingTimer >= RECIPE_TIME_TICKS){
                    aeroforge.craftingTimer = 0;
                    aeroforge.itemSelector = 0;
                    aeroforge.finishCraftingProcedure();
                }else{
                    double D1 = ((Math.sin((aeroforge.craftingTimer/(((double)RECIPE_TIME_TICKS/8)/Math.PI)) - (Math.PI/2)))*0.5f)+0.5f;
                    if(D1>0.85d && aeroforge.hitCount == aeroforge.itemSelector){
                        aeroforge.hitCount++;
                        aeroforge.smashIngredient(aeroforge.itemSelector, level);
                    }
                    aeroforge.craftingTimer++;
                }
            }

            //rendering shenaniganz
            if(level.isClientSide){
                if(aeroforge.hasValidAltar){
                    if(aeroforge.onTimer < 1.0f){
                        aeroforge.onTimer+=0.015f;
                    }else{
                        aeroforge.onTimer = 1.0f;
                    }
                    aeroforge.rotator1+=0.01f;
                    if(aeroforge.rotator1 > (2 * Math.PI)) aeroforge.rotator1 = 0;
                    aeroforge.rotator2-=0.01f;
                    if(aeroforge.rotator2 < 0.0f) aeroforge.rotator2 = ((float)Math.PI*2);
                }else{
                    if(aeroforge.onTimer > 0.0f){
                        aeroforge.onTimer-=0.1f;
                    }else{
                        aeroforge.onTimer = 0.0f;
                    }
                }
            }
        }
    }

    public void smashIngredient(int ingredient, Level level){
        if(!level.isClientSide){
            Vector3f pos = new Vector3f(0.5f, 0.0f, 0.5f);
            switch (ingredient){
                case 0: pos = new Vector3f(4.5f, 1.0f, 0.5f); break;
                case 1: pos = new Vector3f(-3.5f, 1.0f, 0.5f); break;
                case 2: pos = new Vector3f(0.5f, 1.0f, 4.5f); break;
                case 3: pos = new Vector3f(0.5f, 1.0f, -3.5f); break;
                case 4: pos = new Vector3f(3.5f, 2.0f, -2.5f); break;
                case 5: pos = new Vector3f(-2.5f, 2.0f, -2.5f); break;
                case 6: pos = new Vector3f(-2.5f, 2.0f, 3.5f); break;
                case 7: pos = new Vector3f(3.5f, 2.0f, 3.5f); break;
            }
            ServerLevel serverLevel = level.getServer().getLevel(level.dimension());
            if(serverLevel != null){
                for (int i = 0; i < 12; i++) {
                    RandomSource rndsc = level.getRandom();
                    double x = this.worldPosition.getX() + pos.x + (rndsc.nextFloat()*0.4);
                    double y = this.worldPosition.getY() + (pos.y-0.5f) + (rndsc.nextFloat()*0.4);
                    double z = this.worldPosition.getZ() + pos.z + (rndsc.nextFloat()*0.4);
                    serverLevel.sendParticles(new FadingItemParticleOptions(this.items.get(ingredient), RECIPE_TIME_TICKS-this.craftingTimer), x, y, z, 1, 0, 0, 0, 0);
                }
            }
        }
        this.items.set(ingredient, ItemStack.EMPTY);
    }

    public void updateForgeOnClient(AeroforgeBlockEntity f, Level level, boolean shouldCleanse){
        if(!level.isClientSide) PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, level.getChunkAt(f.getBlockPos()).getPos(), AeroforgeData.getDataFromForge(f, shouldCleanse));
    }

    @Override
    public int getContainerSize() {
        return this.altarLevel*4;
    }

    @Override
    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack stack = ContainerHelper.removeItem(this.items, slot, amount);
        this.setChanged();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack stack = ContainerHelper.takeItem(this.items, slot);
        this.setChanged();
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        stack.limitSize(this.getMaxStackSize(stack));
        this.items.set(slot, stack);
        this.setChanged();
    }

    public boolean addItem(Item item){
        int firstFreeSlot = this.getContainerSize()+10;
        for (int i = this.getContainerSize()-1; i >= 0; i--) {
            firstFreeSlot = this.items.get(i).isEmpty() ? i : firstFreeSlot;
        }
        if(firstFreeSlot < this.getContainerSize()){
            this.setItem(firstFreeSlot, new ItemStack(item));
            return true;
        }
        return false;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
        this.setChanged();
    }

    public void dropContents(){
        for (ItemStack stack : this.items) {
            this.level.addFreshEntity(new ItemEntity(this.level, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), stack));
        }
        this.clearContent();
    }

    public void tryCraft(ServerLevel serverLevel, AeroforgeBlockEntity aeroforge){
        RecipeManager recipes = serverLevel.recipeAccess();
        AeroforgeInput input = new AeroforgeInput(items.get(0), items.get(1), items.get(2), items.get(3));
        Optional<RecipeHolder<AeroforgeOneRecipe>> optional = recipes.getRecipeFor(Recipez.AEROFORGE_I_RECIPE_TYPE.get(), input, serverLevel);
        optional.map(RecipeHolder::value).ifPresent(recipe ->{
            aeroforge.startCraftingProcedure(recipe.assemble(input, serverLevel.registryAccess()));
        });
    }

    public void startCraftingProcedure(ItemStack resultItem){
        this.standby = false;
        this.result = resultItem;
        this.updateForgeOnClient(this, this.level, false);
    }

    public void finishCraftingProcedure(){
        this.standby = true;
        this.level.addFreshEntity(new ItemEntity(this.level, this.getBlockPos().getX(), this.getBlockPos().above().getY(), this.getBlockPos().getZ(), this.result));
        this.result = ItemStack.EMPTY;
        this.hitCount = 0;
        this.clearContent();
        this.updateForgeOnClient(this, this.level, true);
    }
}
