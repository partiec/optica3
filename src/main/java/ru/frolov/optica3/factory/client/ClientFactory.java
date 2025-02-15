package ru.frolov.optica3.factory.client;

import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.dto.products.*;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.entity.AbstractUnit;
import ru.frolov.optica3.entity.client.Client;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.entity.products.contact.ContactUnit;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.entity.products.frame.FrameUnit;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.entity.products.glass.GlassUnit;
import ru.frolov.optica3.enums.abstract_enums.ProductCategory;
import ru.frolov.optica3.enums.abstract_enums.ProductStatus;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;
import ru.frolov.optica3.enums.order_enums.OrderPayment;
import ru.frolov.optica3.enums.order_enums.OrderStage;

@Component
public class ClientFactory {

    public static Client createClientInstance(OrderAndClientDto dto) {
        Client instance = new Client();

        instance.setLastName(dto.getLastName());
        instance.setFirstName(dto.getFirstName());
        instance.setPatronymic(dto.getPatronymic());
        instance.setBirthday(dto.getBirthday());
        instance.setPassport(dto.getPassport());
        instance.setDetails(dto.getClientDetails());

        return instance;
    }
}
