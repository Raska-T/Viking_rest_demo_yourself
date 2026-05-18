package ru.mephi.vikingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javax.swing.SwingUtilities;

import ru.mephi.vikingdemo.gui.VikingDesktopFrame;
import ru.mephi.vikingdemo.gui.VikingTableModel;
import ru.mephi.vikingdemo.controller.VikingListener;
import ru.mephi.vikingdemo.service.VikingService;
import ru.mephi.vikingdemo.repository.VikingStorage;

@SpringBootApplication
public class VikingDemoApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(VikingDemoApplication.class);
        app.setHeadless(false);

        ConfigurableApplicationContext context = app.run(args);

        VikingService vikingService = context.getBean(VikingService.class);
        VikingListener vikingListener = context.getBean(VikingListener.class);
        VikingStorage vikingStorage = context.getBean(VikingStorage.class);

        SwingUtilities.invokeLater(() -> {
            VikingTableModel tableModel = new VikingTableModel(vikingService.getAllVikings());
            VikingDesktopFrame frame = new VikingDesktopFrame(vikingService, vikingStorage, tableModel);
            vikingListener.setGui(frame);
            frame.setVisible(true);
        });
    }
}