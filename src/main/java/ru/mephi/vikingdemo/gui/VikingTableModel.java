package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.Viking;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VikingTableModel extends AbstractTableModel {
    private final List<Viking> data;
    private final String[] columnNames = {"ID", "Имя", "Возраст", "Рост (см)", "Цвет волос", "Стиль бороды"};
    public VikingTableModel() {
        this.data = new ArrayList<>();
    }

    public VikingTableModel(List<Viking> initialData) {
        this.data = new ArrayList<>(initialData);
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Viking viking = data.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> viking.id();
            case 1 -> viking.name();
            case 2 -> viking.age();
            case 3 -> viking.heightCm();
            case 4 -> viking.hairColor();
            case 5 -> viking.beardStyle();
            default -> null;
        };
    }

    public void addViking(Viking viking) {
        data.add(viking);
        fireTableRowsInserted(data.size() - 1, data.size() - 1);
    }

    public void addVikings(List<Viking> vikings) {
        if (vikings == null || vikings.isEmpty()) return;
        int startIndex = data.size();
        data.addAll(vikings);
        fireTableRowsInserted(startIndex, data.size() - 1);
    }

    public void deleteViking(Integer vikingId) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).id() != null && data.get(i).id().equals(vikingId)) {
                data.remove(i);
                fireTableRowsDeleted(i, i);
                return;
            }
        }
    }

    public void updateViking(Viking updatedViking) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).id() != null && data.get(i).id().equals(updatedViking.id())) {
                data.set(i, updatedViking);
                fireTableRowsUpdated(i, i);
                return;
            }
        }
    }
}