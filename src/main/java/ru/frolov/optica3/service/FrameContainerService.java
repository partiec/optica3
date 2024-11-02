package ru.frolov.optica3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.FrameContainer;

import java.util.List;
import java.util.Optional;

public interface FrameContainerService {




    Page<FrameContainer> getPage(Pageable pageable);

    Page<FrameContainer> getPage(Specification<FrameContainer> specification, Pageable pageable);

    FrameContainer alreadyExists(String firm, String model);

    void save(FrameContainer containerForNew);


    List<FrameContainer> all();

    Optional<FrameContainer> findById(long id);


    List<FrameContainer> allBySpec(Specification<FrameContainer> frameContainerSpecification);

    void delete(FrameContainer xContainer);
}

