package ru.mephi.vikingdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.model.VikingEntity;
import ru.mephi.vikingdemo.repository.EquipmentItemRepository; // Добавили импорт
import ru.mephi.vikingdemo.repository.VikingRepository;        // Добавили импорт
import ru.mephi.vikingdemo.repository.VikingStorage;

import java.util.List;

@Service
public class VikingService {

    private final VikingFactory vikingFactory;
    private final VikingStorage vikingStorage;
    private final VikingRepository vikingRepository;
    private final EquipmentItemRepository equipmentItemRepository;


    @Autowired
    public VikingService(
            VikingFactory vikingFactory,
            VikingStorage vikingStorage,
            VikingRepository vikingRepository,
            EquipmentItemRepository equipmentItemRepository
    )

    {
        this.vikingFactory = vikingFactory;
        this.vikingStorage = vikingStorage;
        this.vikingRepository = vikingRepository;
        this.equipmentItemRepository = equipmentItemRepository;
    }

    @Transactional
    public Viking addViking(Viking viking) {
        return vikingStorage.save(viking);
    }

    @Transactional
    public void deleteViking(int id) {
        equipmentItemRepository.deleteByVikingId(id);
        vikingRepository.deleteById(id);
    }

    @Transactional
    public Viking updateViking(int id, VikingEntity updatedEntity) {
        vikingRepository.update(id, updatedEntity);
        return findAll().stream()
                .filter(v -> v.name().equals(updatedEntity.name()))
                .findFirst()
                .orElse(null);
    }

    public List<Viking> findAll() {
        return vikingStorage.findAll();
    }

    public Viking createRandomViking() {
        Viking viking = vikingFactory.createRandomViking();
        return vikingStorage.save(viking);
    }

    public void deleteById(int id) {
        vikingStorage.deleteById(id);
    }
}