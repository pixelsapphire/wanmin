package com.pixelsapphire.wanmin.gui.layout;

import com.pixelsapphire.wanmin.controller.WanminDBController;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class WaiterScreen extends Layout {

    private final @NotNull WanminDBController database;

    public WaiterScreen(@NotNull JFrame window, @NotNull WanminDBController database) {
        super(window);
        this.database = database;
    }

    @Override
    protected void init(@NotNull JFrame window) {
        window.add(new SideBar(), Layout.params("gridx=0;gridy=0;fill=?;insets=0,0,0,24", SwingConstants.VERTICAL));
    }

    private class SideBar extends JPanel {

        private SideBar() {
            setLayout(new GridBagLayout());
            final var fill = GridBagConstraints.HORIZONTAL;
            int y = 0;
            add(new JLabel("Witaj, " + database.getUsername()), Layout.params("gridy=?;fill=?;insets=24,24,24,24", y++, fill));
            add(new JButton("Moje zamówienia"), Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));
            add(new JButton("Faktury"), Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));
            add(new JButton("Klienci"), Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));
            add(new JButton("Menu"), Layout.params("gridy=?;fill=?;insets=0,24,8,24", y++, fill));
            add(new JButton("Przepisy"), Layout.params("gridy=?;fill=?;insets=0,24,8,24", y, fill));
        }
    }
}
