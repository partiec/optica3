package ru.frolov.optica3.service.glass_services;


import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;

import java.util.List;

public interface GlassService {

    long foundUnits(List<GlassContainer> foundContainers);

    long dbUnitsSize();
}
