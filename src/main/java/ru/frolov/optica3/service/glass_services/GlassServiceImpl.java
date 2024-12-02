package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.repository.glass_repositories.GlassRepository;
import ru.frolov.optica3.service.frame_services.FrameContainerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GlassServiceImpl implements GlassService {

    private final GlassRepository repository;
    private final GlassContainerService containerService;


    @Override
    public long foundUnits(List<GlassContainer> foundContainers) {
        long result = 0;
        for (GlassContainer c : foundContainers) {
            result += c.getGlassList().size();
        }
        return result;
    }

    public long dbUnitsSize() {
        // единиц товара в бд
        long result = 0;
        for (GlassContainer c : this.containerService.all()) {
            result += c.getGlassList().size();
        }
        return result;
    }
}
