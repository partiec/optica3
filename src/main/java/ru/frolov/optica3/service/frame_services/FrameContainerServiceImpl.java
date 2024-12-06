package ru.frolov.optica3.service.frame_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.frame.Frame;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.repository.frame_repo.FrameContainerRepository;
import ru.frolov.optica3.spec.frame_spec.SpecForFrames;

import java.util.Iterator;
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
    public List<FrameContainer> dubls(Filters_FramePayload filters) {
        Specification<FrameContainer> byAllFields = SpecForFrames.byAllFieldsLike(filters);
        return this.frameContainerRepository.findAll(byAllFields);
    }

    @Override
    public List<FrameContainer> dubls(FrameContainer xContainer) {
        Specification<FrameContainer> byAllFields = SpecForFrames.byAllFieldsLike(new Filters_FramePayload(
                xContainer.getFirm(),
                xContainer.getModel(),
                xContainer.getUseType(),
                xContainer.getInstallType(),
                xContainer.getMaterial(),
                xContainer.getDetails(),
                xContainer.getPurchase(),
                xContainer.getSale()));
        return this.frameContainerRepository.findAll(byAllFields);
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
    public void killDubls(FrameContainer xContainer, List<FrameContainer> dublContainers) {

        Iterator<FrameContainer> dublIterator = dublContainers.iterator();
        while (dublIterator.hasNext()) {

            // текущий контейнер
            FrameContainer dubl = dublIterator.next();
            // выуживаем из него Frames
            List<Frame> frames = dubl.getFrameList();
            // удаляем currentContainer из бд за ненадобностью
            delete(dubl);

            // значения полей копируем из xContainer
            Iterator<Frame> frameIterator = frames.iterator();
            while (frameIterator.hasNext()) {
                Frame currentFrame = frameIterator.next();
                currentFrame.setFirm(xContainer.getFirm());
                currentFrame.setModel(xContainer.getModel());
                currentFrame.setUseType(xContainer.getUseType());
                currentFrame.setInstallType(xContainer.getInstallType());
                currentFrame.setMaterial(xContainer.getMaterial());
                currentFrame.setDetails(xContainer.getDetails());
                currentFrame.setPurchase(xContainer.getPurchase());
                currentFrame.setSale(xContainer.getSale());
                currentFrame.setFrameContainer(xContainer);
            }

            // frames запихиваем в xContainer
            xContainer.getFrameList().addAll(frames);
        }
    }
}
