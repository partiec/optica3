package ru.frolov.optica3.service.frame_services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;

import java.util.List;
import java.util.Optional;

public interface FrameContainerService {


    Page<FrameContainer> getPage(Pageable pageable);

    Page<FrameContainer> getPage(Specification<FrameContainer> specification, Pageable pageable);

    List<FrameContainer> dubls(Filters_FramePayload filters);
    List<FrameContainer> dubls(FrameContainer xContainer);

    void save(FrameContainer containerForNew);


    List<FrameContainer> all();

    Optional<FrameContainer> findById(long id);


    List<FrameContainer> allBySpec(Specification<FrameContainer> frameContainerSpecification);

    void delete(FrameContainer xContainer);

    void deleteById(Long xId);

   void killDubls(FrameContainer xContainer, List<FrameContainer> dublContainers);
}

