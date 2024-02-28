package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Product;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductsCollection extends WanminCollection<Product> {

    public ProductsCollection(@NotNull DatabaseExecutor controller) {
        super(controller);
    }

    @Override
    public @NotNull Product elementFromRecord(@NotNull DictTuple record) {
        return Product.fromRecord(record);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull Product product) {
        return product.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_produkty");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_produkty (nazwa, jednostka) VALUES (?,?)",
                              r.getString("nazwa"), r.getOptionalString("jednostka").orElse(null));
    }

    public void deleteProduct (int productId) {
        controller.executeDML("delete  from sbd147412.wm_produkty where id = ?", productId);
    }

    public void updateProduct (@NotNull Product product) {
        controller.executeDML("update sbd147412.wm_produkty set nazwa = ?, jednostka = ? where id = ?",
                product.getName(), product.getUnit());
    }
}
