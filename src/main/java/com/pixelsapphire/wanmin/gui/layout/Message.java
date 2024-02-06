package com.pixelsapphire.wanmin.gui.layout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

public class Message extends Layout {

    private final JLabel messageLabel;
    private final JButton okButton = new JButton("OK");
    private final Runnable onDismiss;

    public Message(@NotNull JFrame window, @NotNull String message, @Nullable Runnable onDismiss) {
        super(window);
        messageLabel = new JLabel(message);
        this.onDismiss = onDismiss;
    }

    @Override
    protected void init(@NotNull JFrame window) {
        final var fill = GridBagConstraints.HORIZONTAL;
        window.add(messageLabel, Layout.params("gridy=0;fill=?;insets=24,16,24,16", fill));
        if (onDismiss != null) {
            window.add(okButton, Layout.params("gridy=1;insets=0,24,16,24"));
            okButton.addActionListener(e -> onDismiss.run());
        }
    }
}
