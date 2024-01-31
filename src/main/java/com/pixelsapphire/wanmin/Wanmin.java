package com.pixelsapphire.wanmin;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.pixelsapphire.wanmin.gui.MainWindow;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Wanmin {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException ignored) {
        }
        new MainWindow();
    }

}

