package ru.frolov.optica3.repository.products.frame_repo;

import org.springframework.context.annotation.Primary;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.repository.products.RootContainerRepository;

@Primary
public interface FrameContainerRepository
        extends RootContainerRepository<FrameContainer> {
}
