package ru.frolov.optica3.repository.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.frolov.optica3.entity.client.Client;
import ru.frolov.optica3.entity.order._Order;

public interface ClientRepository
        extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
}
