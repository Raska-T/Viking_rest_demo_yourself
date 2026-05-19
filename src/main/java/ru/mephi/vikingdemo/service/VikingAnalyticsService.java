package ru.mephi.vikingdemo.service;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.model.BeardStyle;
import ru.mephi.vikingdemo.model.HairColor;
import ru.mephi.vikingdemo.repository.VikingStorage;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class VikingAnalyticsService {
    private final VikingStorage vikingStorage;

    public VikingAnalyticsService(VikingStorage vikingStorage) {
        this.vikingStorage = vikingStorage;
    }

    public long countByAgeCondition(String condition, int age1, int age2) {
        List<Viking> list = vikingStorage.findAll();
        return switch (condition.toLowerCase()) {
            case "greater" -> list.stream().filter(v -> v.age() > age1).count();
            case "less" -> list.stream().filter(v -> v.age() < age1).count();
            case "range" -> list.stream().filter(v -> v.age() >= age1 && v.age() <= age2).count();
            case "out_of_range" -> list.stream().filter(v -> v.age() < age1 || v.age() > age2).count();
            default -> 0L;
        };
    }

    public long countByBeardAndHair(String beardStyle, String hairColor) {
        return vikingStorage.findAll().stream()
                .filter(v -> v.beardStyle().name().equalsIgnoreCase(beardStyle)
                        && v.hairColor().name().equalsIgnoreCase(hairColor))
                .count();
    }

    public long countWithAxes() {
        return vikingStorage.findAll().stream()
                .filter(v -> {
                    int count = (int) v.equipment().stream()
                        .filter(item -> item.name().toLowerCase().contains("axe"))
                        .count();
                    return count == 1 || count == 2;
                })
                .count();
    }

    public Optional<Viking> getRandomTallViking() {
        List<Viking> tallVikings = vikingStorage.findAll().stream()
                .filter(v -> v.heightCm() > 180)
                .toList();
        if (tallVikings.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(tallVikings.get(new Random().nextInt(tallVikings.size())));
    }

    public List<Viking> getLegendaryVikings() {
        return vikingStorage.findAll().stream()
                .filter(v -> v.equipment().stream()
                        .anyMatch(item -> "LEGENDARY".equalsIgnoreCase(item.quality())))
                .toList();
    }

    public List<Viking> getSortedRedBeardVikings() {
        return vikingStorage.findAll().stream()
                .filter(v -> v.hairColor() == HairColor.Red)
                .filter(v -> v.beardStyle() != BeardStyle.CLEAN_SHAVEN)
                .sorted(Comparator.comparingInt(Viking::age))
                .toList();
    }

    public Optional<Integer> getMaxId(Integer[] ids) {
        return Arrays.stream(ids).max(Integer::compareTo);
    }

    public List<Integer> getEvenIds(Integer[] ids) {
        return Arrays.stream(ids).filter(id -> id % 2 == 0).toList();
    }
}