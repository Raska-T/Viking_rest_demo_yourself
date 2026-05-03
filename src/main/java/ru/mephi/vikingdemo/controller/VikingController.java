package ru.mephi.vikingdemo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.model.VikingEntity;
import ru.mephi.vikingdemo.service.VikingService;

import java.util.List;

/**
 * Контроллер для управления данными о викингах через REST API.
 */
@RestController
@RequestMapping("/api/vikings")
@Tag(name = "Vikings", description = "Операции с викингами")
public class VikingController {

    private final VikingService vikingService;

    // Внедрение зависимости через конструктор
    public VikingController(VikingService vikingService) {
        this.vikingService = vikingService;
    }

    @GetMapping
    @Operation(summary = "Получить список всех викингов")
    public List<Viking> getAll() {
        return vikingService.findAll();
    }

    // 1. Добавление конкретного викинга (принимает JSON из тела запроса)
    @PostMapping
    @Operation(summary = "Добавить конкретного викинга")
    public Viking addCustom(@RequestBody Viking viking) {
        return vikingService.addViking(viking);
    }

    // 2. Удаление викинга по ID (ID передается в пути: /api/vikings/5)
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить викинга по ID")
    public void delete(@PathVariable int id) {
        vikingService.deleteViking(id);
    }

    // 3. Перезапись (обновление) параметров конкретного викинга
    @PutMapping("/{id}")
    @Operation(summary = "Обновить параметры викинга")
    public Viking update(@PathVariable int id, @RequestBody VikingEntity updated) {
        return vikingService.updateViking(id, updated);
    }
}