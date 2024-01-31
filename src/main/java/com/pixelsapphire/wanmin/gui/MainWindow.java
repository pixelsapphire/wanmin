package com.pixelsapphire.wanmin.gui;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.Wanmin;
import com.pixelsapphire.wanmin.controller.WanminDBController;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow extends JFrame {

    private final Layout loginForm = new LoginForm();
    private WanminDBController controller;

    public MainWindow() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginForm.apply();
    }

    private class LoginForm extends Layout {

        private final JLabel
                welcomeLabel = new JLabel("Witaj w Wanmin Restaurant!"),
                loginLabel = new JLabel("Nazwa użytkownika:"),
                passwordLabel = new JLabel("Hasło:");
        private final JTextField loginField = new JTextField();
        private final JPasswordField passwordField = new JPasswordField();
        private final JButton loginButton = new JButton("Zaloguj");

        private LoginForm() {
            super(MainWindow.this);
        }

        @Override
        protected void init(@NotNull JFrame window) {
            final var fill = GridBagConstraints.HORIZONTAL;
            window.add(welcomeLabel, Layout.params("gridy=0;fill=?;insets=12,8,8,8", fill));
            window.add(loginLabel, Layout.params("gridy=1;fill=?;insets=8,8,4,8", fill));
            window.add(loginField, Layout.params("gridy=2;fill=?;insets=0,8,8,8", fill));
            window.add(passwordLabel, Layout.params("gridy=3;fill=?;insets=8,8,4,8", fill));
            window.add(passwordField, Layout.params("gridy=4;fill=?;insets=0,8,8,8", fill));
            window.add(loginButton, Layout.params("gridy=5;insets=4,8,12,8"));
            loginButton.addActionListener(ev -> {
                new Message("Logowanie...", null).apply();
                new Thread(() -> {
                    try {
                        controller = new WanminDBController(loginField.getText(), passwordField.getPassword());
                    } catch (final DatabaseException e) {
                        Logger.getLogger(Wanmin.class.getName()).log(Level.SEVERE, e.getMessage() + ": " + e.getCause().getMessage());
                        new Message("Nie udało się zalogować", () -> {
                            passwordField.setText("");
                            loginForm.apply();
                        }).apply();
                    }
                }).start();
            });
        }
    }

    private class Message extends Layout {

        private final JLabel messageLabel;
        private final JButton okButton = new JButton("OK");
        private final Runnable onDismiss;

        private Message(@NotNull String message, @Nullable Runnable onDismiss) {
            super(MainWindow.this);
            messageLabel = new JLabel(message);
            this.onDismiss = onDismiss;
        }

        @Override
        protected void init(@NotNull JFrame window) {
            final var fill = GridBagConstraints.HORIZONTAL;
            window.add(messageLabel, Layout.params("gridy=0;fill=" + fill + ";insets=24,16,24,16"));
            if (onDismiss != null) {
                window.add(okButton, Layout.params("gridy=1;insets=0,24,16,24"));
                okButton.addActionListener(e -> onDismiss.run());
            }
        }
    }
}
