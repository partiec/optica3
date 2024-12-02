package ru.frolov.optica3.service.glass_services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;

import java.util.List;
import java.util.Optional;

public interface GlassContainerService {


    Page<GlassContainer> getPage(Pageable pageable);

    Page<GlassContainer> getPage(Specification<GlassContainer> specification, Pageable pageable);

    List<GlassContainer> dubls(Filters_GlassPayload filters);
    List<GlassContainer> dubls(GlassContainer xContainer);

    void save(GlassContainer containerForNew);


    List<GlassContainer> all();

    Optional<GlassContainer> findById(long id);


    List<GlassContainer> allBySpec(Specification<GlassContainer> containerSpecification);

    void delete(GlassContainer xContainer);

    void deleteById(Long xId);

   void killDubls(GlassContainer xContainer, List<GlassContainer> dublContainers);
}

