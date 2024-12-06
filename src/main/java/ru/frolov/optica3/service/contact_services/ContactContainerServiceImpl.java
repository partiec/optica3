package ru.frolov.optica3.service.contact_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.contact.Contact;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.payload.contact_payloads.Filters_ContactPayload;
import ru.frolov.optica3.repository.contact_repo.ContactContainerRepository;
import ru.frolov.optica3.spec.contact_spec.SpecForContacts;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactContainerServiceImpl implements ContactContainerService {

    private final ContactContainerRepository containerRepository;


    @Override
    public Page<ContactContainer> getPage(Pageable pageable) {
        return this.containerRepository.findAll(pageable);
    }

    @Override
    public Page<ContactContainer> getPage(Specification<ContactContainer> specification, Pageable pageable) {
        return this.containerRepository.findAll(specification, pageable);
    }

    @Override
    public List<ContactContainer> dubls(Filters_ContactPayload filters) {
        Specification<ContactContainer> byAllFields = SpecForContacts.byAllLike(filters);
        return this.containerRepository.findAll(byAllFields);
    }

    @Override
    public List<ContactContainer> dubls(ContactContainer xContainer) {
        Specification<ContactContainer> byAllFields = SpecForContacts.byAllLike(new Filters_ContactPayload(
                xContainer.getFirm(),
                xContainer.getDesign(),
                xContainer.getPeriod(),
                xContainer.getOxygen(),
                xContainer.getWater(),
                xContainer.getDetails(),
                xContainer.getPurchase(),
                xContainer.getSale(),
                xContainer.getDiameter(),
                xContainer.getRadius(),
                xContainer.getDioptre()));
        return this.containerRepository.findAll(byAllFields);
    }

    @Override
    public void save(ContactContainer container) {
        this.containerRepository.save(container);
    }

    @Override
    public List<ContactContainer> all() {
        return this.containerRepository.findAll();
    }

    @Override
    public Optional<ContactContainer> findById(long id) {
        return this.containerRepository.findById(id);
    }

    @Override
    public List<ContactContainer> allBySpec(Specification<ContactContainer> containerSpecification) {
        return this.containerRepository.findAll(containerSpecification);
    }

    @Override
    public void delete(ContactContainer xContainer) {
        this.containerRepository.delete(xContainer);
    }

    @Override
    public void deleteById(Long xId) {
        this.containerRepository.deleteById(xId);
    }


    //-----------------------------------
    public void killDubls(ContactContainer xContainer, List<ContactContainer> dublContainers) {

        Iterator<ContactContainer> dublIterator = dublContainers.iterator();
        while (dublIterator.hasNext()) {

            // текущий контейнер
            ContactContainer dubl = dublIterator.next();
            // выуживаем из него Frames
            List<Contact> glasses = dubl.getContactList();
            // удаляем currentContainer из бд за ненадобностью
            delete(dubl);

            // значения полей копируем из xContainer
            Iterator<Contact> glassIterator = glasses.iterator();
            while (glassIterator.hasNext()) {
                Contact currentContact = glassIterator.next();
                currentContact.setFirm(xContainer.getFirm());
                currentContact.setDesign(xContainer.getDesign());
                currentContact.setPeriod(xContainer.getPeriod());
                currentContact.setOxygen(xContainer.getOxygen());
                currentContact.setWater(xContainer.getWater());
                currentContact.setDetails(xContainer.getDetails());
                currentContact.setPurchase(xContainer.getPurchase());
                currentContact.setSale(xContainer.getSale());
                currentContact.setDiameter(xContainer.getDiameter());
                currentContact.setRadius(xContainer.getRadius());
                currentContact.setDioptre(xContainer.getDioptre());
                currentContact.setContactContainer(xContainer);
            }

            // frames запихиваем в xContainer
            xContainer.getContactList().addAll(glasses);
        }
    }
}
