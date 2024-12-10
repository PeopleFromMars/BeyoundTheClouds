package net.martianz.beyondtheclouds.recipe.custom;

import net.martianz.beyondtheclouds.recipe.Recipez;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class AeroforgeOneRecipe implements Recipe<AeroforgeInput> {

    private final Ingredient inputItem1;
    private final Ingredient inputItem2;
    private final Ingredient inputItem3;
    private final Ingredient inputItem4;
    private final ItemStack result;

    public AeroforgeOneRecipe(Ingredient inputItem1, Ingredient inputItem2, Ingredient inputItem3, Ingredient inputItem4, ItemStack result){
        this.inputItem1 = inputItem1;
        this.inputItem2 = inputItem2;
        this.inputItem3 = inputItem3;
        this.inputItem4 = inputItem4;
        this.result = result;
    }

    public Ingredient getInputItem1(){
        return this.inputItem1;
    }

    public Ingredient getInputItem2(){
        return this.inputItem2;
    }

    public Ingredient getInputItem3(){
        return this.inputItem3;
    }

    public Ingredient getInputItem4(){
        return this.inputItem4;
    }

    public ItemStack getResult(){
        return this.result;
    }

    @Override
    public boolean matches(AeroforgeInput input, Level level) {
        return this.inputItem1.test(input.stack1()) && this.inputItem2.test(input.stack2()) && this.inputItem3.test(input.stack3()) && this.inputItem4.test(input.stack4());
    }

    @Override
    public ItemStack assemble(AeroforgeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return Recipez.AEROFORGE_I_CATEGORY.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeType<? extends Recipe<AeroforgeInput>> getType() {
        return Recipez.AEROFORGE_I_RECIPE_TYPE.get();
    }

    @Override
    public RecipeSerializer<? extends Recipe<AeroforgeInput>> getSerializer() {
        return Recipez.AEROFORGE_I_SERIALIZER.get();
    }
}
