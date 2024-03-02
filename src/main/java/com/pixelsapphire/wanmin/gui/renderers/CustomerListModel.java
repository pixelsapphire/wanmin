package com.pixelsapphire.wanmin.gui.renderers;

import com.pixelsapphire.wanmin.data.records.Customer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerListModel implements ComboBoxModel<Customer> {

    private final List<Customer> customers;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final List<ListDataListener> listeners;
    private Customer selected;

    public CustomerListModel(@NotNull List<Customer> customers) {
        this.customers = customers;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void setSelectedItem(Object customer) {
        selected = (Customer) customer;
    }

    @Override
    public Customer getSelectedItem() {
        return selected;
    }

    @Override
    public int getSize() {
        return customers.size();
    }

    @Override
    public Customer getElementAt(int index) {
        return customers.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

}
