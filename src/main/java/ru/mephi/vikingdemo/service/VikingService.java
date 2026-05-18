package ru.mephi.vikingdemo.service;

import ru.mephi.vikingdemo.model.Viking;
import ru.mephi.vikingdemo.repository.VikingStorage;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class VikingService {
    private final VikingStorage vikingStorage;
    private final VikingFactory vikingFactory;

    public VikingService(VikingStorage vikingStorage, VikingFactory vikingFactory) {
        this.vikingStorage = vikingStorage;
        this.vikingFactory = vikingFactory;
    }

    public List<Viking> getAllVikings() {
        return vikingStorage.findAll();
    }

    public Viking createRandomViking() {
        Viking viking = vikingFactory.createRandomViking();
        return vikingStorage.save(viking);
    }

    public Viking createCustomViking(Viking viking) {
        return vikingStorage.save(viking);
    }

    public void deleteById(int id) {
        vikingStorage.deleteById(id);
    }

    public boolean updateViking(Viking viking) {
        return vikingStorage.updateViking(viking);
    }

    public void generateMassiveVikings(int count) {
        IntStream.range(0, count)
                .mapToObj(i -> vikingFactory.createRandomViking())
                .forEach(vikingStorage::save);
    }
}