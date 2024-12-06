package ru.frolov.optica3.repository.contact_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.frolov.optica3.entity.contact.ContactContainer;

@Repository
public interface ContactContainerRepository
        extends JpaRepository<ContactContainer, Long>,
        JpaSpecificationExecutor<ContactContainer> {

}