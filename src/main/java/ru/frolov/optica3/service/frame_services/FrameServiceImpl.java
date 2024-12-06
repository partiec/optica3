package ru.frolov.optica3.service.frame_services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.repository.frame_repo.FrameRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FrameServiceImpl implements FrameService {

    private final FrameRepository frameRepository;
    private final FrameContainerService containerService;


    @Override
    public long foundFramesUnits(List<FrameContainer> foundContainers) {
        long result = 0;
        for (FrameContainer c : foundContainers) {
            result += c.getFrameList().size();
        }
        return result;
    }

    public long dbFramesSize() {
        // единиц товара в бд
        long result = 0;
        for (FrameContainer c : this.containerService.all()) {
            result += c.getFrameList().size();
        }
        return result;
    }
}
