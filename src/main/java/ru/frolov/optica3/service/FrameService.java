package ru.frolov.optica3.service;

import ru.frolov.optica3.entity.Frame;

import java.util.List;
import java.util.Optional;

public interface FrameService {
    List<Frame> findAllFrames();

    void saveFrame(Frame frame);

    Optional<Frame> findById(Long id);

    void deleteById(Long id);
}
