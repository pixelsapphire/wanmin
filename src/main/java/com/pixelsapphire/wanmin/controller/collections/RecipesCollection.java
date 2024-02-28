package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Product;
import com.pixelsapphire.wanmin.data.records.Recipe;
import com.pixelsapphire.wanmin.data.records.RecipeIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecipesCollection extends WanminCollection<Recipe> {

    private final @NotNull Provider<Product> products;

    public RecipesCollection(@NotNull DatabaseExecutor controller, @NotNull Provider<Product> products) {
        super(controller);
        this.products = products;
    }

    @Override
    public @NotNull Recipe elementFromRecord(@NotNull DictTuple record) {
        return Recipe.fromRecord(record, id -> controller.executeQuery("SELECT * FROM sbd147412.wm_przepisy_skladniki WHERE przepis = ?", id)
                                                         .stream().map((it) -> RecipeIngredient.fromRecord(it, products)).toList());
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull Recipe recipe) {
        return recipe.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_przepisy");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_przepisy (nazwa) VALUES (?)", r.getString("nazwa"));
    }

    public void addIngredientToRecipe(int recipeId, @NotNull RecipeIngredient ingredient) {
        controller.executeDML("INSERT INTO sbd147412.wm_przepisy_skladniki (przepis, produkt, ilosc) VALUES (?,?,?)",
                              recipeId, ingredient.getProduct().getId(), ingredient.getAmount());
    }

    public void deleteRecipe (int recipeId) {
        controller.executeDML("DELETE FROM sbd147412.wm_przepisy_skladniki where przepis = ?", recipeId);
        controller.executeDML("DELETE FROM sbd147412.wm_przepisy where id = ?", recipeId);
    }

    public void deleteRecipeIngredient (int recipeIngredientId) {
        controller.executeDML("DELETE FROM sbd147412.wm_przepisy_skladniki where id = ?", recipeIngredientId);
    }

    public void updateRecipe (@NotNull Recipe recipe) {
        controller.executeDML("UPDATE sbd147412.wm_przepisy set nazwa = ? WHERE id = ?", recipe.getName(), recipe.getId());
    }

    public void updateRecipeIngredient (int recipeId, @NotNull RecipeIngredient ingredient) {
        controller.executeDML("update sbd147412.wm_przepisy_skladniki set przepis = ?, produkt = ?, ilosc = ? where id = ?",
                recipeId, ingredient.getProduct().getId(), ingredient.getAmount(), ingredient.getId());
    }

}
