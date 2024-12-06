package ru.frolov.optica3.repository.frame_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.frolov.optica3.entity.frame.Frame;

@Repository
public interface FrameRepository extends JpaRepository<Frame, Long> {

}
