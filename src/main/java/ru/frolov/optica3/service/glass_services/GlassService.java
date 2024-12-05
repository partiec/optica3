package ru.frolov.optica3.service.glass_services;


import ru.frolov.optica3.entity.glass.GlassContainer;

import java.util.List;

public interface GlassService {

    long foundUnitsSize(List<GlassContainer> foundContainers);

    long dbUnitsSize();
}
