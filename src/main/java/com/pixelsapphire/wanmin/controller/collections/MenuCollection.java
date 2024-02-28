package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Menu;
import com.pixelsapphire.wanmin.data.records.MenuItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MenuCollection extends WanminCollection<Menu> {

    private final @NotNull Provider<List<MenuItem>> menuItemsProvider;

    public MenuCollection(@NotNull DatabaseExecutor controller, @NotNull Provider<List<MenuItem>> menuItemsProvider) {
        super(controller);
        this.menuItemsProvider = menuItemsProvider;
    }

    @Override
    public @NotNull Menu elementFromRecord(@NotNull DictTuple record) {
        return Menu.fromRecord(record, menuItemsProvider);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull Menu menu) {
        return menu.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_menu");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_menu (nazwa) VALUES (?)", r.getString("nazwa"));
    }

    public void addItemToMenu(int menuId, @NotNull MenuItem menuItem) {
        controller.executeDML("INSERT INTO sbd147412.wm_menu_pozycje (nazwa, cena, przepis, kategoria, menu) values (?,?,?,?,?)",
                              menuItem.getName(), menuItem.getPrice(), menuItem.getRecipe(), menuItem.getCategory(), menuId);
    }

    public void deleteMenu (int menuId) {
        controller.executeDML("DELETE FROM sbd147412.wm_menu_pozycje where menu = ?", menuId);
        controller.executeDML("DELETE FROM sbd147412.wm_menu where id = ?", menuId);
    }

    public void deleteMenuItem (int menuItemId) {
        controller.executeDML("DELETE FROM sbd147412.wm_menu_pozycje where id = ?", menuItemId);
    }

    public void updateMenu (@NotNull Menu menu) {
        controller.executeDML("UPDATE sbd147412.wm_menu set nazwa = ? where id =?", menu.getName(), menu.getId());
    }

    public void updateMenuItem (int menuId, @NotNull MenuItem item) {
        controller.executeDML("UPDATE sbd1474.wm_menu_pozycje set nazwa = ?, cena = ?, przepis = ?, kategoria = ?, menu = ? where id =?",
                item.getName(), item.getPrice(), item.getRecipe().getId(), item.getCategory(), menuId, item.getId());
    }
}
