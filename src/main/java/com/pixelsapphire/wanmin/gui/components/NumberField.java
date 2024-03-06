package com.pixelsapphire.wanmin.gui.components;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.*;
import java.math.BigInteger;

public class NumberField extends JTextField {

    public NumberField(int initialValue) {
        super("" + initialValue);
        ((PlainDocument) getDocument()).setDocumentFilter(new IntegerFilter());
    }

    public int getInt() {
        if (getText().isEmpty()) return 0;
        return Integer.parseInt(getText());
    }

    public long getLong() {
        if (getText().isEmpty()) return 0;
        return Long.parseLong(getText());
    }

    public BigInteger getBigInteger() {
        if (getText().isEmpty()) return BigInteger.ZERO;
        return new BigInteger(getText());
    }

    private static class IntegerFilter extends DocumentFilter {
        @Override
        public void insertString(@NotNull FilterBypass fb, int offset, String string,
                                 AttributeSet attr) throws BadLocationException {
            final Document doc = fb.getDocument();
            final StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.insert(offset, string);
            if (test(sb.toString())) super.insertString(fb, offset, string, attr);
        }

        private boolean test(@NotNull String text) {
            if (text.isEmpty()) return true;
            try {
                Integer.parseInt(text);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        @Override
        public void replace(@NotNull FilterBypass fb, int offset, int length, String text,
                            AttributeSet attrs) throws BadLocationException {
            final Document doc = fb.getDocument();
            final StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.replace(offset, offset + length, text);
            if (test(sb.toString())) super.replace(fb, offset, length, text, attrs);
        }

        @Override
        public void remove(@NotNull FilterBypass fb, int offset, int length) throws BadLocationException {
            final Document doc = fb.getDocument();
            final StringBuilder sb = new StringBuilder();
            sb.append(doc.getText(0, doc.getLength()));
            sb.delete(offset, offset + length);
            if (test(sb.toString())) super.remove(fb, offset, length);
        }
    }
}
