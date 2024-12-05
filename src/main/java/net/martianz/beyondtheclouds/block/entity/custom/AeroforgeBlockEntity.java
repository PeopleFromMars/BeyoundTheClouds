package net.martianz.beyondtheclouds.block.entity.custom;

import net.martianz.beyondtheclouds.block.entity.BlockEntitiez;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AeroforgeBlockEntity extends BlockEntity {
    public Boolean hasValidAltar = false;
    public int altarLevel = 0;

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
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putBoolean("hasValidAltar", this.hasValidAltar);
        tag.putInt("altarLevel", this.altarLevel);
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

    // Optionally: Run some custom logic when the packet is received.
    // The super/default implementation forwards to #loadAdditional.
    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
        this.hasValidAltar = packet.getTag().getBoolean("hasValidAltar");
        this.altarLevel = packet.getTag().getInt("altarLevel");
    }
}
