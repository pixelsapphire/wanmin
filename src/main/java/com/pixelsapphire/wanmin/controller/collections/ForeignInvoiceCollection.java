package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Contractor;
import com.pixelsapphire.wanmin.data.records.ForeignInvoice;
import com.pixelsapphire.wanmin.data.records.ForeignInvoiceItem;
import com.pixelsapphire.wanmin.data.records.Product;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ForeignInvoiceCollection extends WanminCollection<ForeignInvoice> {

    private final @NotNull Provider<Contractor> contractors;
    private final @NotNull Provider<Product> products;

    public ForeignInvoiceCollection(@NotNull DatabaseExecutor controller,
                                    @NotNull Provider<Contractor> contractors, @NotNull Provider<Product> products) {
        super(controller);
        this.contractors = contractors;
        this.products = products;
    }

    @Override
    public @NotNull ForeignInvoice elementFromRecord(@NotNull DictTuple record) {
        return ForeignInvoice.fromRecord(record, contractors, id -> controller.executeQuery(
                "SELECT * FROM sbd147412.wm_faktury_obce_pozycje WHERE faktura = ?", id).stream().map(
                (it) -> ForeignInvoiceItem.fromRecord(it, products)).toList());
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull ForeignInvoice invoice) {
        return invoice.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_faktury_obce");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_faktury_obce (kontrahent, data, nr_obcy) VALUES (?,?,?)",
                              r.getInt("kontrahent"), r.getDate("data"), r.getString("nr_obcy"));
    }

    public void addItemToForeignInvoice(int foreignInvoiceId, @NotNull ForeignInvoiceItem item) {
        controller.executeDML("INSERT INTO sbd147412.wm_faktury_obce_pozycje (faktura, produkt, cena, ilosc, data_waznosci) VALUES (?,?,?,?,?)",
                              foreignInvoiceId, item.getProduct(), item.getPrice(), item.getAmount(), item.getExpires());
    }

    public void deleteForeignInvoice(int foreignInvoiceId) {
        controller.executeDML("DELETE FROM sbd147412.wm_faktury_obce_pozycje WHERE faktura = ?", foreignInvoiceId);
        controller.executeDML("DELETE FROM sbd147412.wm_faktury_obce WHERE ID = ?", foreignInvoiceId);
    }

    public void deleteForeignInvoiceItem(int foreignInvoiceItemId) {
        controller.executeDML("DELETE FROM sbd147412.wm_faktury_obce_pozycje WHERE ID = ?", foreignInvoiceItemId);
    }

    public void updateForeignInvoice(@NotNull ForeignInvoice foreignInvoice) {
        controller.executeDML("UPDATE sbd147412.wm_faktury_obce SET kontrahent = ?, data = ?, nr_obcy = ? WHERE id = ?",
                              foreignInvoice.getContractor(), foreignInvoice.getDate(), foreignInvoice.getNumber(), foreignInvoice.getId());
    }
}
