package ru.frolov.optica3.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.frolov.optica3.entity.Frame;

import java.util.List;

public interface FrameRepository extends JpaRepository<Frame, Long> {

    List<Frame> findAllByFirmContainingIgnoreCase(String filter1);
    List<Frame> findAllByModelContainingIgnoreCase(String filter1);
    List<Frame> findAllByDetailsContainingIgnoreCase(String filter1);



}
