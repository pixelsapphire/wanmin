package com.pixelsapphire.wanmin.gui.auxiliary;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.List;
import java.util.ArrayList;

public class BasicListModel<T> implements ComboBoxModel<T> {

    private final List<ListDataListener> listeners;
    private final List<T> items;
    private T selected;

    public BasicListModel(@NotNull List<T> items) {
        this.items = items;
        this.listeners = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setSelectedItem(Object item) {
        selected = (T) item;
    }

    @Override
    public T getSelectedItem() {
        return selected;
    }

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public T getElementAt(int index) {
        return items.get(index);
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
