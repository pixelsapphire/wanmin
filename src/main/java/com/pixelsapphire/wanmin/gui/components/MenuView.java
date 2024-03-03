package com.pixelsapphire.wanmin.gui.components;

import com.pixelsapphire.wanmin.data.records.Menu;
import com.pixelsapphire.wanmin.data.records.Recipe;
import com.pixelsapphire.wanmin.gui.layout.Layout;
import com.pixelsapphire.wanmin.util.ListAdapter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class MenuView extends JFrame {

    private final @NotNull Menu menu;

    public MenuView(@NotNull Menu menu) {
        this.menu = menu;
        setLayout(new GridBagLayout());
        ListAdapter.wrap(menu.getItems()).forEachIndexed((i, item)->{
            add(new JLabel((i + 1) + ". " + item.getName() + " " + item.getPrice() + " zl"),
                    Layout.params("gridx=0;gridy=?;fill=?;insets=8,8,8,8", i, SwingConstants.HORIZONTAL));
            final var button = new JButton("Przepis");
            button.addActionListener(e -> showRecipe(item.getRecipe()));
            add(button, Layout.params("gridx=1;gridy=?;fill=?;insets=0,0,0,0", i, SwingConstants.HORIZONTAL));
        });
        setTitle(menu.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        pack();
    }

    private void showRecipe(@NotNull Recipe recipe) {
        final var recipeWindow = new JFrame();
        recipeWindow.setLayout(new GridBagLayout());
        var sb = new StringBuilder("Skladniki: <br>");
        ListAdapter.wrap(recipe.getRecipeIngredients()).forEachIndexed((i, ingredient) ->
            sb.append((i + 1)).append(". ").append(ingredient.getProduct().getName()).append("<br>"));
        recipeWindow.add(new JLabel("<html>" + sb + "</html>"), Layout.params("insets=4,4,4,4"));
        recipeWindow.setTitle(recipe.getName());
        recipeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        recipeWindow.setLocationRelativeTo(null);
        recipeWindow.setResizable(false);
        recipeWindow.setVisible(true);
        recipeWindow.revalidate();
        recipeWindow.repaint();
        recipeWindow.pack();
    }
}
