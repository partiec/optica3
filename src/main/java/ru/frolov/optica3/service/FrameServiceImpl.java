package ru.frolov.optica3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.repo.FrameRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FrameServiceImpl implements FrameService {

    private final FrameRepository frameRepository;

    @Override
    public List<Frame> findAllFrames() {
        return this.frameRepository.findAll();
    }

    @Override
    public void saveFrame(Frame frame) {
        this.frameRepository.save(frame);
    }

    @Override
    public Optional<Frame> findById(Long id) {
        return this.frameRepository.findById(id);
    }

    
}
