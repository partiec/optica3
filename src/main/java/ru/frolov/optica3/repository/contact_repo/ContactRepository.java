package ru.frolov.optica3.repository.contact_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.frolov.optica3.entity.contact.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
