package ru.mephi.vikingdemo.gui;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.*;
import ru.mephi.vikingdemo.repository.VikingStorage;
import javax.swing.*;
import java.awt.*;

public class VikingDesktopFrame extends JFrame {
    private final VikingTableModel tableModel;
    private final JTextArea analyticsOutputArea = new JTextArea(8, 40);

    public VikingDesktopFrame(VikingService service, VikingStorage vikingStorage, VikingTableModel tableModel) {
        this.tableModel = tableModel;
        VikingAnalyticsService analyticsService = new VikingAnalyticsService(vikingStorage);

        setTitle("Управление Викингами — Панель Аналитики");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        JPanel topPanel = new JPanel();
        JButton createBtn = new JButton("Создать случайного викинга");
        topPanel.add(createBtn);
        add(topPanel, BorderLayout.NORTH);
        createBtn.addActionListener(e -> {
            if (service != null) tableModel.addViking(service.createRandomViking());
        });

        add(new JScrollPane(new JTable(tableModel)), BorderLayout.CENTER);

        JPanel analyticsPanel = new JPanel(new BorderLayout(5, 5));
        analyticsPanel.setBorder(BorderFactory.createTitledBorder("Панель Лямбда-Аналитики"));
        JPanel buttonsGrid = new JPanel(new GridLayout(4, 3, 5, 5));

        String[] titles = {
                "Создать 20 викингов", "Легендарное снаряжение", "Возраст (18-30)",
                "Возраст (Вне 18-30)", "Возраст (> 18)", "Возраст (< 30)",
                "Рыжие с длинной бородой", "> 180 см", "Ровно 1 или 2 топора", "Тест массива ID"
        };

        Runnable[] actions = {
                () -> { service.generateMassiveVikings(20); tableModel.addVikings(service.getAllVikings()); analyticsOutputArea.setText("Создано и добавлено 20 случайных викингов!"); },
                () -> { analyticsOutputArea.setText("Викинги с легендарным снаряжением:\n"); analyticsService.getLegendaryVikings().forEach(v -> analyticsOutputArea.append("- " + v.name() + " (ID: " + v.id() + ")\n")); },
                () -> analyticsOutputArea.setText("Количество викингов от 18 до 30 лет: " + analyticsService.countByAgeCondition("range", 18, 30)),
                () -> analyticsOutputArea.setText("Количество викингов вне диапазона 18-30 лет: " + analyticsService.countByAgeCondition("out_of_range", 18, 30)),
                () -> analyticsOutputArea.setText("Количество викингов старше 18 лет: " + analyticsService.countByAgeCondition("greater", 18, 0)),
                () -> analyticsOutputArea.setText("Количество викингов младше 30 лет: " + analyticsService.countByAgeCondition("less", 30, 0)),
                () -> { analyticsOutputArea.setText("--- РЫЖИЕ С ДЛИННОЙ БОРОДОЙ (СОРТИРОВКА ПО ВОЗРАСТУ) ---\n"); analyticsService.getSortedRedBeardVikings().forEach(v -> analyticsOutputArea.append("- " + v.name() + " | Возраст: " + v.age() + " | Борода: " + v.beardStyle() + "\n")); },
                () -> analyticsService.getRandomTallViking().ifPresentOrElse(v -> analyticsOutputArea.setText("Случайный викинг выше 180 см:\n" + v.name() + " (" + v.heightCm() + " см)"), () -> analyticsOutputArea.setText("В базе нет никого выше 180 см.")),
                () -> analyticsOutputArea.setText("Количество  с ровно 1 или 2 топорами: " + analyticsService.countWithAxes()),
        };

        for (int i = 0; i < titles.length; i++) {
            JButton btn = new JButton(titles[i]);
            final int index = i;
            btn.addActionListener(e -> actions[index].run());
            buttonsGrid.add(btn);
        }

        analyticsPanel.add(buttonsGrid, BorderLayout.NORTH);

        analyticsOutputArea.setEditable(false);
        analyticsOutputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        analyticsPanel.add(new JScrollPane(analyticsOutputArea), BorderLayout.CENTER);
        add(analyticsPanel, BorderLayout.SOUTH);
    }

    public void addNewViking(Viking viking) { if (viking != null) tableModel.addViking(viking); }
    public void onDeleteViking(Integer id) { if (tableModel != null) tableModel.deleteViking(id); }
    public void onUpdateViking(Viking v, boolean ch) { if (ch && tableModel != null) tableModel.updateViking(v); }
}