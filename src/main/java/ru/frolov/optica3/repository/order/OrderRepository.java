package ru.frolov.optica3.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.frolov.optica3.entity.order._Order;

public interface OrderRepository
        extends JpaRepository<_Order, Long>, JpaSpecificationExecutor<_Order> {
    _Order findByCurrent(boolean current);
}
