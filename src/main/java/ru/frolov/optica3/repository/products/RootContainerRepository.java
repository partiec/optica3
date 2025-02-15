package ru.frolov.optica3.repository.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import ru.frolov.optica3.entity.AbstractContainer;
import ru.frolov.optica3.entity.AbstractUnit;

@NoRepositoryBean
public interface RootContainerRepository<C extends AbstractContainer>
        extends JpaRepository<C, Long>, JpaSpecificationExecutor<C> {

}
