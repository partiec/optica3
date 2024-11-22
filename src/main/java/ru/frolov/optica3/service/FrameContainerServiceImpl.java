package ru.frolov.optica3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.repository.FrameContainerRepository;
import ru.frolov.optica3.spec.SpecForFrames;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FrameContainerServiceImpl implements FrameContainerService {

    private final FrameContainerRepository frameContainerRepository;



    @Override
    public Page<FrameContainer> getPage(Pageable pageable) {
        return this.frameContainerRepository.findAll(pageable);
    }

    @Override
    public Page<FrameContainer> getPage(Specification<FrameContainer> specification, Pageable pageable) {
        return this.frameContainerRepository.findAll(specification, pageable);
    }

    @Override
    public FrameContainer findSame(String firm, String model) {
        Specification<FrameContainer> spec = Specification.where(SpecForFrames.firmLike(firm)).and(SpecForFrames.modelLike(model));
        FrameContainer result = this.frameContainerRepository.findAll(spec).stream()
                .findFirst()
                .orElse(null);
        System.out.println("___findSame()...");
        System.out.println("///same = " + result);
        return result;
    }

    @Override
    public void save(FrameContainer container) {
        this.frameContainerRepository.save(container);
    }

    @Override
    public List<FrameContainer> all() {
        return this.frameContainerRepository.findAll();
    }

    @Override
    public Optional<FrameContainer> findById(long id) {
        return this.frameContainerRepository.findById(id);
    }

    @Override
    public List<FrameContainer> allBySpec(Specification<FrameContainer> frameContainerSpecification) {
        return this.frameContainerRepository.findAll(frameContainerSpecification);
    }

    @Override
    public void delete(FrameContainer xContainer) {
        this.frameContainerRepository.delete(xContainer);
    }

    @Override
    public void deleteById(Long xId) {
        this.frameContainerRepository.deleteById(xId);
    }


    //-----------------------------------

}
