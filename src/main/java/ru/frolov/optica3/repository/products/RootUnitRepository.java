package ru.frolov.optica3.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.frolov.optica3.entity.AbstractUnit;

@Repository
public interface RootUnitRepository<U extends AbstractUnit>
        extends JpaRepository<U, Long>,
        JpaSpecificationExecutor<U> {

}
