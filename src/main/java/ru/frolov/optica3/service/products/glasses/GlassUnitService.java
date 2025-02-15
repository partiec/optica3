package ru.frolov.optica3.service.products.glasses;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.products.glass.GlassUnit;
import ru.frolov.optica3.repository.products.glass_repo.GlassUnitRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GlassUnitService {

    private final GlassUnitRepository unitRepository;
    //--------------------------------------------------------------------------------------------------


    public GlassUnit createNewEmptyUnit() {
        return new GlassUnit();
    }

    public Optional<GlassUnit> findById(Long id) {
        return unitRepository.findById(id);
    }

    public void save(GlassUnit unit) {
        unitRepository.save(unit);
    }


    public void delete(GlassUnit unit) {
        unitRepository.delete(unit);
    }
}
