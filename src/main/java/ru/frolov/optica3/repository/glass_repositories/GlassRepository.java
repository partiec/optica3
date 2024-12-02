package ru.frolov.optica3.repository.glass_repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.frolov.optica3.entity.frame.Frame;
import ru.frolov.optica3.entity.glass.Glass;

@Repository
public interface GlassRepository extends JpaRepository<Glass, Long> {

}
