package com.pixelsapphire.wanmin.gui.renderers;

import com.pixelsapphire.wanmin.data.records.Customer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class CustomerListRenderer implements ListCellRenderer<Customer> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Customer> list, Customer c, int index, boolean isSelected, boolean cellHasFocus) {
        if (c == null) return new JLabel("Wybierz z listy...");
        return new JLabel("id: " + c.getId() + ", " + c.getFirstName() + ", " + c.getLastName());
    }
}
