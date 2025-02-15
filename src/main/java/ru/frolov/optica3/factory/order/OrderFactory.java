package ru.frolov.optica3.factory.order;

import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.enums.order_enums.OrderPayment;
import ru.frolov.optica3.enums.order_enums.OrderStage;

@Component
public class OrderFactory {

    public static _Order createOrderInstance(OrderAndClientDto dto) {

        _Order instance = new _Order();

        instance.setCurrent(dto.getCurrent() == null ? false : dto.getCurrent());
        instance.setStage(dto.getStage() == null ? OrderStage.NOT_SELECTED : dto.getStage());
        instance.setPayment(dto.getPayment() == null ? OrderPayment.NOT_SELECTED : dto.getPayment());
        instance.setOrderDetails(dto.getOrderDetails());
        instance.setPrice(dto.getPrice());

        //////////////////////////////////////////////
        // client's fields
        instance.setLastName(dto.getLastName());
        instance.setFirstName(dto.getFirstName());
        instance.setPatronymic(dto.getPatronymic());
        instance.setBirthday(dto.getBirthday());
        instance.setPassport(dto.getPassport());
        instance.setClientDetails(dto.getClientDetails());
        //////////////////////////////////////////////

        return instance;
    }


}
