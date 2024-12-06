package ru.frolov.optica3.service.contact_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.repository.contact_repo.ContactRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository repository;
    private final ContactContainerService containerService;


    @Override
    public long foundUnitsSize(List<ContactContainer> foundContainers) {
        long result = 0;
        for (ContactContainer c : foundContainers) {
            result += c.getContactList().size();
        }
        return result;
    }

    public long dbUnitsSize() {
        // единиц товара в бд
        long result = 0;
        for (ContactContainer c : this.containerService.all()) {
            result += c.getContactList().size();
        }
        return result;
    }
}
