package ru.frolov.optica3.repository.glass_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.frolov.optica3.entity.glass.GlassContainer;

@Repository
public interface GlassContainerRepository
        extends JpaRepository<GlassContainer, Long>,
        JpaSpecificationExecutor<GlassContainer> {

}