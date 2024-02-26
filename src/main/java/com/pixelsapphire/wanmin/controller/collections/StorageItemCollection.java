package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.ForeignInvoice;
import com.pixelsapphire.wanmin.data.records.Product;
import com.pixelsapphire.wanmin.data.records.StorageItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StorageItemCollection extends WanminCollection<StorageItem> {

    private final @NotNull Provider<Product> productProvider;
    private final @NotNull Provider<ForeignInvoice> foreignInvoiceProvider;

    public StorageItemCollection(@NotNull DatabaseExecutor controller,
                                 @NotNull Provider<Product> productProvider, @NotNull Provider<ForeignInvoice> foreignInvoiceProvider) {
        super(controller);
        this.productProvider = productProvider;
        this.foreignInvoiceProvider = foreignInvoiceProvider;
    }

    @Override
    public @NotNull StorageItem elementFromRecord(@NotNull DictTuple record) {
        return StorageItem.fromRecord(record, productProvider, foreignInvoiceProvider);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull StorageItem storageItem) {
        return storageItem.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_magazyn");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_magazyn (produkt, ilosc, data_waznosci, faktura) VALUES (?,?,?,?)",
                                r.getInt("produkt"), r.getInt("data_waznosci"),
                                r.getOptionalDate("data_waznosci").orElse(null), r.getInt("faktura"));
    }
}
