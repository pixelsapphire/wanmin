package com.pixelsapphire.wanmin.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.Component;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntConsumer;

public class SwingUtils {

    private SwingUtils() {
    }

    private static void resizeColumnWidth(@NotNull JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                final TableCellRenderer renderer = table.getCellRenderer(row, column);
                final Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) width = 300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    @Contract("_, _, _, _ -> new")
    public static <T> @NotNull JScrollPane createTable(@NotNull String @NotNull [] columns, @NotNull List<T> data,
                                                       @NotNull Function<T, String[]> rowMapper, @Nullable IntConsumer onRowSelected) {
        final var table = new StaticJTable(data.stream().map(rowMapper).toArray(String[][]::new), columns);
        if (onRowSelected == null) table.setRowSelectionAllowed(false);
        else table.getSelectionModel().addListSelectionListener(e -> onRowSelected.accept(table.getSelectedRow()));
        resizeColumnWidth(table);
        return new JScrollPane(table);
    }

    @Contract("_, _, _ -> new")
    public static <T> @NotNull JScrollPane createTable(@NotNull String @NotNull [] columns, @NotNull List<T> data,
                                                       @NotNull Function<T, String[]> rowMapper) {
        return createTable(columns, data, rowMapper, null);
    }

    private static class StaticJTable extends JTable {

        public StaticJTable(@NotNull Object[][] rowData, @NotNull Object[] columnNames) {
            super(rowData, columnNames);
            setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
