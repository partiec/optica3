package ru.frolov.optica3.repository.frame_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.frolov.optica3.entity.frame.FrameContainer;

@Repository
public interface FrameContainerRepository
        extends JpaRepository<FrameContainer, Long>,
        JpaSpecificationExecutor<FrameContainer> {

}