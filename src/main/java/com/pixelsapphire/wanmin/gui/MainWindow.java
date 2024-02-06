package com.pixelsapphire.wanmin.gui;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.Wanmin;
import com.pixelsapphire.wanmin.controller.WanminDBController;
import com.pixelsapphire.wanmin.gui.layout.Layout;
import com.pixelsapphire.wanmin.gui.layout.LoginForm;
import com.pixelsapphire.wanmin.gui.layout.Message;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow extends JFrame {

    private WanminDBController controller;

    public MainWindow() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginForm.apply();
    }

    private void onLogin(@NotNull String login, char @NotNull [] password) {
        new Message(this, "Logowanie...", null).apply();
        new Thread(() -> {
            try {
                controller = new WanminDBController(login, password);
            } catch (final DatabaseException e) {
                Logger.getLogger(Wanmin.class.getName()).log(Level.SEVERE, e.getMessage() + ": " + e.getCause().getMessage());
                new Message(this, "Nie udało się zalogować", loginForm::apply).apply();
            }
        }).start();
    }

    private final Layout loginForm = new LoginForm(this, this::onLogin);

}
