package ru.frolov.optica3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.repository.FrameRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FrameServiceImpl implements FrameService {

    private final FrameRepository frameRepository;

    @Override
    public Iterable<Frame> findAll() {
        return new ArrayList<>(this.frameRepository.findAll());
    }

    @Override
    public Iterable<Frame> findAllByFilter(String filter) {
        return new ArrayList<>((Collection) this.frameRepository.findAllByFirmContainingIgnoreCase(filter));
    }

    @Override
    public void save(Frame frame) {
        if (frame != null)
            this.frameRepository.save(frame);
    }

    @Override
    public Optional<Frame> findById(Long id) {
        return this.frameRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.frameRepository.deleteById(id);
    }

    @Override
    public Page<Frame> getPage(int pageNumber, int size) {
        return this.frameRepository.findAll(PageRequest.of(pageNumber - 1, size));
    }

    @Override
    public List<Frame> sortBy(String field) {
        return this.frameRepository.findAll(Sort.by(Sort.Direction.DESC, field));
    }

    @Override
    public Page<Frame> getSortedPage(int pageNumber, int size, String field) {
        return this.frameRepository.findAll(PageRequest.of(pageNumber - 1, size).withSort(Sort.by(Sort.Direction.DESC, field)));
    }


}
