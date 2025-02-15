package ru.frolov.optica3.service.products.contacts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.products.contact.ContactUnit;
import ru.frolov.optica3.repository.products.contact_repo.ContactUnitRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactUnitService {

    private final ContactUnitRepository unitRepository;


    public ContactUnit createNewEmptyUnit() {
        return new ContactUnit();
    }

    public Optional<ContactUnit> findById(Long id) {
        return unitRepository.findById(id);
    }
    public void save(ContactUnit unit) {
        unitRepository.save(unit);
    }

    public void delete(ContactUnit unit) {
        unitRepository.delete(unit);
    }

}
