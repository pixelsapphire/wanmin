package com.pixelsapphire.wanmin.gui.layout;

import com.pixelsapphire.wanmin.gui.MainWindow;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.util.function.BiConsumer;

public class LoginForm extends Layout {

    private final JLabel
            welcomeLabel = new JLabel("Witaj w Wanmin Restaurant!"),
            loginLabel = new JLabel("Nazwa uzytkownika:"),
            passwordLabel = new JLabel("Haslo:");
    private final JTextField loginField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginButton = new JButton("Zaloguj");

    private final BiConsumer<String, char[]> onLogin;

    public LoginForm(@NotNull MainWindow window, @NotNull BiConsumer<String, char[]> onLogin) {
        super(window);
        this.onLogin = onLogin;
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
        loginButton.addActionListener(ev -> onLogin.accept(loginField.getText(), passwordField.getPassword()));
    }

    @Override
    public void apply() {
        super.apply();
        passwordField.setText("");
    }
}
