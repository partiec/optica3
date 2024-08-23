package ru.frolov.optica3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.repository.FrameRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FrameServiceImpl implements FrameService {

    private final FrameRepository frameRepository;

    @Override
    public List<Frame> getList() {
        return new ArrayList<>(this.frameRepository.findAll());
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
    public List<Frame> getListSortedBy(String field) {
        return this.frameRepository.findAll(Sort.by(Sort.Direction.DESC, field));
    }

    @Override
    public Page<Frame> getPageSortedBy(int pageNumber, int size, String field) {
        return this.frameRepository.findAll(PageRequest.of(pageNumber - 1, size).withSort(Sort.by(Sort.Direction.DESC, field)));
    }

    @Override
    public List<Frame> filterResult(String filter) {

        List<Frame> frames = this.frameRepository.findAllByFirmContainingIgnoreCase(filter);
        frames.addAll(this.frameRepository.findAllByModelContainingIgnoreCase(filter));
        frames.addAll(this.frameRepository.findAllByDetailsContainingIgnoreCase(filter));

        return Collections.unmodifiableList(frames);
    }

    @Override
    public List<Frame> getPageFiltered(List<Frame> total, int pageNumberF, int pageSize) {

        int beginIndex = (pageNumberF - 1) * pageSize;
        int bortIndex = beginIndex + pageSize;

        return total.stream()
                .filter(frame -> (total.indexOf(frame) >= beginIndex) && (total.indexOf(frame) < bortIndex))
                .distinct()
                .toList();
    }


}
