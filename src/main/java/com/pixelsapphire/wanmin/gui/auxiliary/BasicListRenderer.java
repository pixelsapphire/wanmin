package com.pixelsapphire.wanmin.gui.auxiliary;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public interface BasicListRenderer<T> extends ListCellRenderer<T> {

    @NotNull String render(@NotNull T item);

    @Override
    default Component getListCellRendererComponent(JList<? extends T> list, T item, int index,
                                                   boolean isSelected, boolean cellHasFocus) {
        return new JLabel(item == null ? "Wybierz z listy..." : render(item));
    }
}
