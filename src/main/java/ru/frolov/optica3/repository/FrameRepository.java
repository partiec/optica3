package ru.frolov.optica3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.frolov.optica3.entity.frame.Frame;

import java.util.List;

@Repository
public interface FrameRepository extends JpaRepository<Frame, Long> {

    List<Frame> findAllByFirmContainingIgnoreCase(String filter1);
    List<Frame> findAllByModelContainingIgnoreCase(String filter1);
    List<Frame> findAllByDetailsContainingIgnoreCase(String filter1);



}
