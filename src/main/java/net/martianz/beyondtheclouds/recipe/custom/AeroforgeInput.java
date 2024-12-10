package net.martianz.beyondtheclouds.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AeroforgeInput(ItemStack stack1, ItemStack stack2, ItemStack stack3, ItemStack stack4) implements RecipeInput {

    @Override
    public ItemStack getItem(int slot) {
        ItemStack stack = null;
        switch (slot){
            case 1: stack = this.stack1; break;
            case 2: stack = this.stack1; break;
            case 3: stack = this.stack1; break;
            case 4: stack = this.stack1; break;
        }
        if(stack != null){
            return stack;
        }else{
            throw new IllegalArgumentException("index was outside of bounds (1-4) or no item on for index " + slot);
        }
    }

    @Override
    public int size() {
        return 4;
    }
}
