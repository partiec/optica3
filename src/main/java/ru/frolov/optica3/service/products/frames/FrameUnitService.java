package ru.frolov.optica3.service.products.frames;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.products.frame.FrameUnit;
import ru.frolov.optica3.repository.products.frame_repo.FrameUnitRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FrameUnitService {

    private final FrameUnitRepository unitRepository;
    //------------------------------------------------------------------------------------------


    public FrameUnit createNewEmptyUnit() {
        return new FrameUnit();
    }

    public Optional<FrameUnit> findById(Long id) {
        return unitRepository.findById(id);
    }

    public void save(FrameUnit unit) {
        unitRepository.save(unit);
    }


    public void delete(FrameUnit unit) {
        unitRepository.delete(unit);
    }
}
