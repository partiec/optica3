package ru.frolov.optica3.cache._order;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;

@Component
@Getter
@Setter
public class OrderCache {

    private Page<_Order> page;
    private Specification<_Order> spec;
    private OrderAndClientDto dto;
    private _Order currentOrder;
    private Boolean orderRefreshed;
    private Boolean mode;

    // hint method for cache
    public void cacheAttributesIfNotNull(Page<_Order> page,
                                         OrderAndClientDto dto,
                                         Specification<_Order> specification,
                                         _Order currentOrder,
                                         Boolean orderRefreshed,
                                         Boolean mode) {

        if (page != null)
            this.page = page;
        if (dto != null)
            this.dto = dto;
        if (specification != null)
            this.spec = specification;
        if (currentOrder != null)
            this.currentOrder = currentOrder;
        if (orderRefreshed != null)
            this.orderRefreshed = orderRefreshed;
        if (mode != null)
            this.mode = mode;
    }
}
