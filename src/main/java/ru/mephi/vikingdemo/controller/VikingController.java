package ru.mephi.vikingdemo.controller;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.service.VikingService;
import ru.mephi.vikingdemo.service.VikingAnalyticsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vikings")
public class VikingController {

    private final VikingService vikingService;
    private final VikingAnalyticsService analyticsService;
    private final VikingListener vikingListener;

    public VikingController(VikingService vikingService, VikingAnalyticsService analyticsService, VikingListener vikingListener) {
        this.vikingService = vikingService;
        this.analyticsService = analyticsService;
        this.vikingListener = vikingListener;
    }

    @GetMapping
    public List<Viking> getAllVikings() {
        return vikingService.getAllVikings();
    }

    @PostMapping("/postCustom")
    public void addCustomViking(@RequestBody Viking viking) {
        vikingListener.addCustom(viking);
    }

    @DeleteMapping("/delete/{vikingId}")
    public void deleteVikingById(@PathVariable("vikingId") Integer vikingId) {
        vikingListener.deleteVikingById(vikingId);
    }

    @PutMapping("/update")
    public void updateViking(@RequestBody Viking viking) {
        vikingListener.updateViking(viking);
    }

    @PostMapping("/generateMassive")
    public String generateMassive(@RequestParam("count") int count) {
        vikingService.generateMassiveVikings(count);
        return "Успешно сгенерировано викингов: " + count;
    }

    @GetMapping("/analytics/red-beards")
    public List<Viking> getSortedRedBeards() {
        return analyticsService.getSortedRedBeardVikings();
    }
}