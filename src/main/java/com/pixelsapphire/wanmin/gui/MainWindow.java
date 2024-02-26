package com.pixelsapphire.wanmin.gui;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.Wanmin;
import com.pixelsapphire.wanmin.controller.WanminDBController;
import com.pixelsapphire.wanmin.gui.layout.Layout;
import com.pixelsapphire.wanmin.gui.layout.LoginForm;
import com.pixelsapphire.wanmin.gui.layout.Message;
import com.pixelsapphire.wanmin.gui.layout.WaiterScreen;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow extends JFrame {

    private WanminDBController controller;

    public MainWindow() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        loginForm.apply();
    }

    private void onLogin(@NotNull String login, char @NotNull [] password) {
        new Message(this, "Logowanie...", null).apply();
        new Thread(() -> {
            try {
                controller = new WanminDBController(login, password);
                if (controller.isRoleEnabled("sbd147412.wm_kelner")) new WaiterScreen(this, controller).apply();
                else new Message(this, "Nie nadano roli. Skontaktuj się z administratorem.", loginForm::apply).apply();
            } catch (final DatabaseException e) {
                Logger.getLogger(Wanmin.class.getName()).log(Level.SEVERE, e.getMessage() + ": " + e.getCause().getMessage());
                new Message(this, "Nie udało się zalogować", loginForm::apply).apply();
            }
        }).start();
    }

    private Layout loginForm = new LoginForm(this, this::onLogin);

}
