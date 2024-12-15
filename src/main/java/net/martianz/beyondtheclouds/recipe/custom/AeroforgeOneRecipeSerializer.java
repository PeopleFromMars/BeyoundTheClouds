package net.martianz.beyondtheclouds.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

public class AeroforgeOneRecipeSerializer implements RecipeSerializer<AeroforgeOneRecipe> {

    public static final MapCodec<AeroforgeOneRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient1").forGetter(AeroforgeOneRecipe::getInputItem1),
            Ingredient.CODEC.fieldOf("ingredient2").forGetter(AeroforgeOneRecipe::getInputItem2),
            Ingredient.CODEC.fieldOf("ingredient3").forGetter(AeroforgeOneRecipe::getInputItem3),
            Ingredient.CODEC.fieldOf("ingredient4").forGetter(AeroforgeOneRecipe::getInputItem4),
            ItemStack.CODEC.fieldOf("result").forGetter(AeroforgeOneRecipe::getResult)
    ).apply(inst, AeroforgeOneRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, AeroforgeOneRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, r -> r.getInputItem1(),
                    Ingredient.CONTENTS_STREAM_CODEC, r -> r.getInputItem2(),
                    Ingredient.CONTENTS_STREAM_CODEC, r -> r.getInputItem3(),
                    Ingredient.CONTENTS_STREAM_CODEC, r -> r.getInputItem4(),
                    ItemStack.STREAM_CODEC, AeroforgeOneRecipe::getResult,
                    AeroforgeOneRecipe::new
            );

    @Override
    public @NotNull MapCodec<AeroforgeOneRecipe> codec() {
        return CODEC;
    }

    @Override
    public @NotNull StreamCodec<RegistryFriendlyByteBuf, AeroforgeOneRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
