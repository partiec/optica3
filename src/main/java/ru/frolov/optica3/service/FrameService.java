package ru.frolov.optica3.service;

import org.springframework.data.domain.Page;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.entity.FrameContainer;

import java.util.List;
import java.util.Optional;

public interface FrameService {
    List<Frame> findAll();




    Optional<Frame> findById(Long id);

    void deleteById(Long id);

    Page<Frame> getPage(int offset, int size);

    Iterable<Frame> getListSortedBy(String field);

    Page<Frame> getPageSortedBy(int offset, int size, String field);


    List<Frame> filterResult(String filter);

    List<Frame> getPageFiltered(List<Frame> all, int filteredPageNumber, int size);


    void save(Frame newFrame);
}
