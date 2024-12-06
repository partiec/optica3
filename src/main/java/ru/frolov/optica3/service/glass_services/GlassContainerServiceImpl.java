package ru.frolov.optica3.service.glass_services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.frolov.optica3.entity.glass.Glass;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.repository.glass_repo.GlassContainerRepository;
import ru.frolov.optica3.spec.glass_spec.SpecForGlasses;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GlassContainerServiceImpl implements GlassContainerService {

    private final GlassContainerRepository containerRepository;


    @Override
    public Page<GlassContainer> getPage(Pageable pageable) {
        return this.containerRepository.findAll(pageable);
    }

    @Override
    public Page<GlassContainer> getPage(Specification<GlassContainer> specification, Pageable pageable) {
        return this.containerRepository.findAll(specification, pageable);
    }

    @Override
    public List<GlassContainer> dubls(Filters_GlassPayload filters) {
        Specification<GlassContainer> byAllFields = SpecForGlasses.byAllLike(filters);
        return this.containerRepository.findAll(byAllFields);
    }

    @Override
    public List<GlassContainer> dubls(GlassContainer xContainer) {
        Specification<GlassContainer> byAllFields = SpecForGlasses.byAllLike(new Filters_GlassPayload(
                xContainer.getFirm(),
                xContainer.getMaterial(),
                xContainer.getDesign(),
                xContainer.getCoat(),
                xContainer.getDetails(),
                xContainer.getPurchase(),
                xContainer.getSale(),
                xContainer.getDioptre()));
        return this.containerRepository.findAll(byAllFields);
    }

    @Override
    public void save(GlassContainer container) {
        this.containerRepository.save(container);
    }

    @Override
    public List<GlassContainer> all() {
        return this.containerRepository.findAll();
    }

    @Override
    public Optional<GlassContainer> findById(long id) {
        return this.containerRepository.findById(id);
    }

    @Override
    public List<GlassContainer> allBySpec(Specification<GlassContainer> containerSpecification) {
        return this.containerRepository.findAll(containerSpecification);
    }

    @Override
    public void delete(GlassContainer xContainer) {
        this.containerRepository.delete(xContainer);
    }

    @Override
    public void deleteById(Long xId) {
        this.containerRepository.deleteById(xId);
    }


    //-----------------------------------
    public void killDubls(GlassContainer xContainer, List<GlassContainer> dublContainers) {

        Iterator<GlassContainer> dublIterator = dublContainers.iterator();
        while (dublIterator.hasNext()) {

            // текущий контейнер
            GlassContainer dubl = dublIterator.next();
            // выуживаем из него Frames
            List<Glass> glasses = dubl.getGlassList();
            // удаляем currentContainer из бд за ненадобностью
            delete(dubl);

            // значения полей копируем из xContainer
            Iterator<Glass> glassIterator = glasses.iterator();
            while (glassIterator.hasNext()) {
                Glass currentGlass = glassIterator.next();
                currentGlass.setFirm(xContainer.getFirm());
                currentGlass.setMaterial(xContainer.getMaterial());
                currentGlass.setDesign(xContainer.getDesign());
                currentGlass.setCoat(xContainer.getCoat());
                currentGlass.setDetails(xContainer.getDetails());
                currentGlass.setPurchase(xContainer.getPurchase());
                currentGlass.setSale(xContainer.getSale());
                currentGlass.setDioptre(xContainer.getDioptre());
                currentGlass.setGlassContainer(xContainer);
            }

            // frames запихиваем в xContainer
            xContainer.getGlassList().addAll(glasses);
        }
    }
}
