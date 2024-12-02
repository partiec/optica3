package ru.frolov.optica3.service.frame_services;


import ru.frolov.optica3.entity.frame.FrameContainer;

import java.util.List;

public interface FrameService {

    long foundFramesUnits(List<FrameContainer> foundContainers);

    long dbFramesSize();
}
