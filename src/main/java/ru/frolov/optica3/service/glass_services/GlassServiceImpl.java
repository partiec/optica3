package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.repository.glass_repo.GlassRepository;
import ru.frolov.optica3.service.contact_services.ContactContainerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassServiceImpl implements GlassService {

    private final GlassRepository repository;
    private final ContactContainerService containerService;


    @Override
    public long foundUnitsSize(List<GlassContainer> foundContainers) {
        long result = 0;
        for (GlassContainer c : foundContainers) {
            result += c.getGlassList().size();
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
