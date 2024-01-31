package com.pixelsapphire.wanmin.gui;

import com.pixelsapphire.wanmin.util.StringUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Arrays;

public abstract class Layout {

    private final JFrame window;

    public Layout(@NotNull JFrame window) {
        this.window = window;
    }

    @Contract("_, _ -> new")
    public static @NotNull GridBagConstraints params(@NotNull String params, @NotNull Number @NotNull ... placeholders) {
        final var placeholderCount = StringUtil.countCharacters(params, '?');
        if (placeholderCount != placeholders.length)
            throw new IllegalArgumentException("Number of substitution values (" + placeholders.length +
                                               ") does not match the number of placeholders (" + placeholderCount + ")");
        for (final var placeholder : placeholders) params = params.replaceFirst("\\?", placeholder.toString());
        final var constraints = new GridBagConstraints();
        try {
            Arrays.stream(params.split(";")).forEach(param -> {
                final var key = param.substring(0, param.indexOf('='));
                final var value = param.substring(param.indexOf('=') + 1);
                switch (key) {
                    case "gridx" -> constraints.gridx = Integer.parseInt(value);
                    case "gridy" -> constraints.gridy = Integer.parseInt(value);
                    case "gridwidth" -> constraints.gridwidth = Integer.parseInt(value);
                    case "gridheight" -> constraints.gridheight = Integer.parseInt(value);
                    case "weightx" -> constraints.weightx = Double.parseDouble(value);
                    case "weighty" -> constraints.weighty = Double.parseDouble(value);
                    case "anchor" -> constraints.anchor = Integer.parseInt(value);
                    case "fill" -> constraints.fill = Integer.parseInt(value);
                    case "insets" -> {
                        final var insets = value.split(",");
                        constraints.insets.set(Integer.parseInt(insets[0]), Integer.parseInt(insets[1]),
                                               Integer.parseInt(insets[2]), Integer.parseInt(insets[3]));
                    }
                    case "ipadx" -> constraints.ipadx = Integer.parseInt(value);
                    case "ipady" -> constraints.ipady = Integer.parseInt(value);
                }
            });
        } catch (final Exception e) {
            throw new IllegalArgumentException("Could not compile layout parameters: " + params, e);
        }
        return constraints;
    }

    protected abstract void init(@NotNull JFrame window);

    public final void refresh() {
        window.pack();
        window.setLocationRelativeTo(null);
    }

    public final void apply() {
        window.getContentPane().removeAll();
        window.setLayout(new GridBagLayout());
        init(window);
        refresh();
    }
}
