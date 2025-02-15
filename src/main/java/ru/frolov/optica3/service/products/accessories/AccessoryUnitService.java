package ru.frolov.optica3.service.products.accessories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.repository.products.accessory_repo.AccessoryUnitRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessoryUnitService {

    private final AccessoryUnitRepository unitRepository;


    public AccessoryUnit createNewEmptyUnit() {
        return new AccessoryUnit();
    }

    public Optional<AccessoryUnit> findById(Long id) {
        return unitRepository.findById(id);
    }

    public void save(AccessoryUnit unit) {
        unitRepository.save(unit);
    }


    public void remove(AccessoryUnit unit) {
        unitRepository.delete(unit);
    }

    public List<AccessoryUnit> findAll() {
        return unitRepository.findAll();
    }


}
