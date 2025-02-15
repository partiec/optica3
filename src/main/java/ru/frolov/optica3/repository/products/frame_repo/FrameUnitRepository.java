package ru.frolov.optica3.repository.products.frame_repo;

import org.springframework.context.annotation.Primary;
import ru.frolov.optica3.entity.products.frame.FrameUnit;
import ru.frolov.optica3.repository.products.RootUnitRepository;

@Primary
public interface FrameUnitRepository
        extends RootUnitRepository<FrameUnit> {
}
