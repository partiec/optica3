package ru.frolov.optica3.service.products.frames;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.frolov.optica3.cache._order.OrderCache;
import ru.frolov.optica3.cache.products.FrameCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.entity.products.frame.FrameContainer;
import ru.frolov.optica3.entity.products.frame.FrameUnit;
import ru.frolov.optica3.enums.frames_enums.FrameInstallType;
import ru.frolov.optica3.enums.frames_enums.FrameMaterial;
import ru.frolov.optica3.enums.frames_enums.FrameUseType;
import ru.frolov.optica3.repository.products.frame_repo.FrameContainerRepository;
import ru.frolov.optica3.repository.products.frame_repo.FrameUnitRepository;
import ru.frolov.optica3.specification.products.FrameSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class FrameContainerService {


    private final FrameContainerRepository containerRepository;
    private final FrameUnitRepository unitRepository;
    private final FrameSpec spec;
    private final FrameCache cache;
    private final OrderCache orderCache;

    //--------------------------------------------------------------------------
    public void transferToModel(
            Model model,
            Page<FrameContainer> pageForModel,
            Integer pageNumberOnlyForFlip,
            Long xId,
            String whichFieldOnInputOnlyForSearch,
            String copyToSearch) {


        /*
         * Метод передает данные в модель. Некоторые данные добывает, прежде чем передать.
         * */

        // transfer
        // --------
        // pageForModel
        model.addAttribute("page", pageForModel);

        // filters
        model.addAttribute("filters", getCache().getDto());                                                         //  !

        // db positions
        List<FrameContainer> dbContainers = containerRepository.findAll();
        model.addAttribute("dbPositions", dbContainers.size());

        // found positions
        model.addAttribute("foundPositions", pageForModel.getTotalElements());


        // db units
        List<FrameUnit> dbUnits = unitRepository.findAll();
        model.addAttribute("dbUnits", dbUnits.size());

        // foundUnits
        if (getCache().getSpec() == null) {
            model.addAttribute("foundUnits", dbUnits.size());
        } else {
            List<FrameContainer> foundContainers = containerRepository.findAll(getCache().getSpec());
            model.addAttribute("foundUnits", howManyUnitsInList(foundContainers));
        }


        // xOrderId
        if (xId != null) {
            model.addAttribute("xId", xId);
        }

        // whichFieldOnInputOnlyForSearch
        if (whichFieldOnInputOnlyForSearch != null) {
            model.addAttribute("whichFieldOnInput", whichFieldOnInputOnlyForSearch);
        }

        if (getCache().getSpec() == null) {
            model.addAttribute("message_itsFullList", "message_itsFullList");
        }

        if (cache.getMode()) {
            model.addAttribute("editMode", "editMode");
        }

        if (copyToSearch != null) {
            model.addAttribute("copyToSearch", "copyToSearch");
        }

        //////////////////////////
        // frame
        model.addAttribute("frameUseTypes", FrameUseType.values());

        model.addAttribute("frameInstallTypes", FrameInstallType.values());

        model.addAttribute("frameMaterials", FrameMaterial.values());
        //////////////////////////


        // currentOrder
        model.addAttribute("currentOrder", getOrderCache().getCurrentOrder());
    }


    public void killDubls(FrameContainer xContainer, List<FrameContainer> sublistExcept0) {
        if (!sublistExcept0.isEmpty()) {
            for (FrameContainer c : sublistExcept0) {
                List<FrameUnit> dublUnitList = c.getUnitList();
                List<FrameUnit> xContainerUnitList = xContainer.getUnitList();
                xContainerUnitList.addAll(dublUnitList);
                containerRepository.delete(c);
            }
        }
    }

    public List<FrameContainer> getListContainersNOSpec() {
        return containerRepository.findAll();
    }


    public Page<FrameContainer> getPageNOSpec(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return containerRepository.findAll(pageable);
    }

    public List<FrameContainer> getListContainersBYSpec(Specification<FrameContainer> specification) {
        return containerRepository.findAll(specification);
    }

    public Page<FrameContainer> getPageBYSpec(Integer pageNumber, Specification<FrameContainer> specification) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return containerRepository.findAll(specification, pageable);
    }


    public long howManyUnitsInList(List<FrameContainer> foundContainers) {
        long result = 0;
        for (FrameContainer c : foundContainers) {
            result += c.getUnitList().size();
        }
        return result;
    }

    public long howManyUnitsBySpec(FrameDto filters) {

        Specification<FrameContainer> specification = spec.allContains(filters);
        List<FrameContainer> containersBySpec = getListContainersBYSpec(specification);
        return howManyUnitsInList(containersBySpec);
    }


    public List<FrameUnit> getListUnitsNOSpec() {
        return unitRepository.findAll();
    }


    public Page<FrameContainer> getXPage(FrameContainer xContainer) {
        /*
         * Метод ищет page, содержащую нужный контейнер. (На xContainer будет установлена 'галочка').
         * */


        Page<FrameContainer> xPage = null;

        if (xContainer != null) {


            // переменная для выхода из цикла
            boolean catchXPage = false;

            // перебираем страницы, пока не найдем нужную
            for (int i = 0; ; i++) {

                // если нужная страница найдена, выходим из цикла
                if (catchXPage) break;

                // На каждой итерации создать:
                //  pageable i
                Pageable pageable = PageRequest.of(
                        i,
                        Defaults.PAGE_SIZE,
                        Sort.by(Sort.Direction.ASC, "firm"));
                // страницу i
                xPage = containerRepository.findAll(pageable);

                // перебирать content каждой страницы пока не найдем страницу, содержащую xContainer
                for (FrameContainer container : xPage.getContent()) {
                    // если нашелся xContainer, то найдена нужная страница
                    if (container.equals(xContainer)) {
                        catchXPage = true;
                        System.out.println("break!");
                        break;
                    }
                }
            }
        }


        return xPage;
    }


    public void save(FrameContainer containerForNew) {
        containerRepository.save(containerForNew);
    }


    public Optional<FrameContainer> findById(long id) {
        return containerRepository.findById(id);
    }


    public void delete(FrameContainer xContainer) {
        containerRepository.delete(xContainer);
    }


    public void deleteById(Long xId) {
        containerRepository.deleteById(xId);
    }


    public FrameContainer createNewEmptyContainer() {
        return new FrameContainer();
    }


    public List<FrameContainer> dublsByX(FrameContainer xContainer) {
        FrameDto filters = new FrameDto();
        filters.setProductCategory(xContainer.getProductCategory());
        filters.setFirm(xContainer.getFirm());
        filters.setModel(xContainer.getModel());
        filters.setDetails(xContainer.getDetails());
        filters.setPurchase(xContainer.getPurchase());
        filters.setSale(xContainer.getSale());
        ////////////////////////////
        filters.setFrameUseType(xContainer.getFrameUseType());
        filters.setFrameInstallType(xContainer.getFrameInstallType());
        filters.setFrameMaterial(xContainer.getFrameMaterial());
        ////////////////////////////

        return dublsByDto(filters);
    }

    public List<FrameContainer> dublsByDto(FrameDto filters) {
        Specification<FrameContainer> allLike = spec.allLike(filters);
        return allLike == null ? new ArrayList<>() : containerRepository.findAll(allLike);
    }


    public Specification<FrameContainer> allContains(FrameDto filters) {
        return spec.allContains(filters);
    }


    public Specification<FrameContainer> allLike(FrameDto filters) {
        return spec.allLike(filters);
    }
}

