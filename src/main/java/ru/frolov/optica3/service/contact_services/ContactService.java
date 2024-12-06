package ru.frolov.optica3.service.contact_services;


import ru.frolov.optica3.entity.contact.ContactContainer;
import ru.frolov.optica3.entity.glass.GlassContainer;

import java.util.List;

public interface ContactService {

    long foundUnitsSize(List<ContactContainer> foundContainers);

    long dbUnitsSize();
}
