package net.martianz.beyondtheclouds.recipe;

import net.martianz.beyondtheclouds.BeyondTheClouds;
import net.martianz.beyondtheclouds.recipe.custom.AeroforgeOneRecipe;
import net.martianz.beyondtheclouds.recipe.custom.AeroforgeOneRecipeSerializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Recipez {


    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPEZ = DeferredRegister.create(Registries.RECIPE_TYPE, BeyondTheClouds.MODID);
    public static final DeferredRegister<RecipeBookCategory> RECIPE_BOOK_CATEGORIES = DeferredRegister.create(Registries.RECIPE_BOOK_CATEGORY, BeyondTheClouds.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERZ = DeferredRegister.create(Registries.RECIPE_SERIALIZER, BeyondTheClouds.MODID);

    public static final Supplier<RecipeBookCategory> AEROFORGE_I_CATEGORY = RECIPE_BOOK_CATEGORIES.register("aeroforge_1", RecipeBookCategory::new);
    public static final Supplier<RecipeSerializer<AeroforgeOneRecipe>> AEROFORGE_I_SERIALIZER = RECIPE_SERIALIZERZ.register("aeroforge_i_type", AeroforgeOneRecipeSerializer::new);
    public static final Supplier<RecipeType<AeroforgeOneRecipe>> AEROFORGE_I_RECIPE_TYPE = RECIPE_TYPEZ.register("aeroforge_i_type", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(BeyondTheClouds.MODID, "aeroforge_i_type")));


    public static void register(IEventBus eventBus){
        RECIPE_BOOK_CATEGORIES.register(eventBus);
        RECIPE_SERIALIZERZ.register(eventBus);
        RECIPE_TYPEZ.register(eventBus);
    }
}
