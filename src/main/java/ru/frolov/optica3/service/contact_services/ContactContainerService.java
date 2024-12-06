package ru.frolov.optica3.service.contact_services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;

import java.util.List;
import java.util.Optional;

public interface ContactContainerService {


    Page<ContactContainer> getPage(Pageable pageable);

    Page<ContactContainer> getPage(Specification<ContactContainer> specification, Pageable pageable);

    List<ContactContainer> dubls(Filters_ContactPayload filters);
    List<ContactContainer> dubls(ContactContainer xContainer);

    void save(ContactContainer containerForNew);


    List<ContactContainer> all();

    Optional<ContactContainer> findById(long id);


    List<ContactContainer> allBySpec(Specification<ContactContainer> containerSpecification);

    void delete(ContactContainer xContainer);

    void deleteById(Long xId);

    void killDubls(ContactContainer xContainer, List<ContactContainer> dublContainers);
}
