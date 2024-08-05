package ru.frolov.optica3.service;

import org.springframework.data.domain.Page;
import ru.frolov.optica3.entity.Frame;

import java.util.List;
import java.util.Optional;

public interface FrameService {
    Iterable<Frame> findAll();

    Iterable<Frame> findAllByFilter(String filter);

    void save(Frame frame);

    Optional<Frame> findById(Long id);

    void deleteById(Long id);

    Page<Frame> getPage(int offset, int size);

    Iterable<Frame> sortBy(String field);

    Page<Frame> getSortedPage(int offset, int size, String field);
}
