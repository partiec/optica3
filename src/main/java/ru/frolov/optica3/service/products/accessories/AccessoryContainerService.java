package ru.frolov.optica3.service.products.accessories;


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
import ru.frolov.optica3.cache.products.AccessoryCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.entity.products.accessory.AccessoryContainer;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.enums.accessory_enums.AccessoryCategory;
import ru.frolov.optica3.repository.products.accessory_repo.AccessoryContainerRepository;
import ru.frolov.optica3.repository.products.accessory_repo.AccessoryUnitRepository;
import ru.frolov.optica3.specification.products.AccessorySpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class AccessoryContainerService {


    private final AccessoryContainerRepository containerRepository;
    private final AccessoryUnitRepository unitRepository;
    private final AccessorySpec accessorySpec;
    private final AccessoryCache accessoryCache;
    private final OrderCache orderCache;

    // -------------------------------------------------------------------------
    public void transferToModel(
            Model model,
            Page<AccessoryContainer> pageForModel,
            Integer pageNumberOnlyForFlip,
            Long xContainerId,
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
        model.addAttribute("filters", getAccessoryCache().getDto());                                                         //  !

        // db positions
        List<AccessoryContainer> dbContainers = containerRepository.findAll();
        model.addAttribute("dbPositions", dbContainers.size());

        // found positions
        model.addAttribute("foundPositions", pageForModel.getTotalElements());

        // db units
        List<AccessoryUnit> dbUnits = unitRepository.findAll();
        model.addAttribute("dbUnits", dbUnits.size());

        // foundUnits
        if (getAccessoryCache().getSpec() == null) {
            model.addAttribute("foundUnits", dbUnits.size());
        } else {
            List<AccessoryContainer> foundContainers = containerRepository.findAll(getAccessoryCache().getSpec());
            model.addAttribute("foundUnits", howManyUnitsInList(foundContainers));
        }


        // xContainerId
        if (xContainerId != null) {
            model.addAttribute("xContainerId", xContainerId);
        }

        model.addAttribute("checkedIdAndStringMap", getAccessoryCache().getChecks());

        // whichFieldOnInputOnlyForSearch
        if (whichFieldOnInputOnlyForSearch != null) {
            model.addAttribute("whichFieldOnInput", whichFieldOnInputOnlyForSearch);
        }

        if (getAccessoryCache().getSpec() == null) {
            model.addAttribute("message_itsFullList", "message_itsFullList");
        }

        if (getAccessoryCache().getMode()) {
            model.addAttribute("editMode", "editMode");
        }

        if (copyToSearch != null) {
            model.addAttribute("copyToSearch", "copyToSearch");
        }


        ////////////////////////////
        // accessory
        model.addAttribute("accessoryCategories", AccessoryCategory.values());
        ////////////////////////////

        // currentOrder
        System.out.println("******" + getClass().getSimpleName() + ".transferToModel(){ currentOrder=" + getOrderCache().getCurrentOrder());
        model.addAttribute("currentOrder", getOrderCache().getCurrentOrder());
    }

    public void killDubls(AccessoryContainer xContainer, List<AccessoryContainer> sublistExcept0) {
        if (!sublistExcept0.isEmpty()) {
            for (AccessoryContainer c : sublistExcept0) {
                List<AccessoryUnit> dublUnitList = c.getUnitList();
                List<AccessoryUnit> xContainerUnitList = xContainer.getUnitList();
                xContainerUnitList.addAll(dublUnitList);
                containerRepository.delete(c);
            }
        }
    }

    public List<AccessoryContainer> getListContainersNOSpec() {
        return containerRepository.findAll();
    }


    public Page<AccessoryContainer> getPageNOSpec(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return containerRepository.findAll(pageable);
    }

    public List<AccessoryContainer> getListContainersBYSpec(Specification<AccessoryContainer> specification) {
        return containerRepository.findAll(specification);
    }

    public Page<AccessoryContainer> getPageBYSpec(Integer pageNumber, Specification<AccessoryContainer> specification) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return containerRepository.findAll(specification, pageable);
    }


    public long howManyUnitsInList(List<AccessoryContainer> foundContainers) {
        long result = 0;
        for (AccessoryContainer c : foundContainers) {
            result += c.getUnitList().size();
        }

        return result;
    }


    public long howManyUnitsBySpec(AccessoryDto filters) {

        Specification<AccessoryContainer> specification = accessorySpec.allContains(filters);
        List<AccessoryContainer> containersBySpec = getListContainersBYSpec(specification);
        return howManyUnitsInList(containersBySpec);
    }


    public List<AccessoryUnit> getListUnitsNOSpec() {
        return unitRepository.findAll();
    }


    public Page<AccessoryContainer> getXPage(AccessoryContainer xContainer) {
        /*
         * Метод ищет page, содержащую нужный контейнер. (На xContainer будет установлена 'галочка').
         * */

        Page<AccessoryContainer> xPage = null;

        if (xContainer != null) {

            System.out.println(getClass().getSimpleName() + ".getXPage()...");

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
                for (AccessoryContainer container : xPage.getContent()) {
                    // если нашелся xContainer, то найдена нужная страница
                    if (container.equals(xContainer)) {
                        catchXPage = true;
                        break;
                    }
                }
            }
        }

        return xPage;
    }


    public void save(AccessoryContainer containerForNew) {
        containerRepository.save(containerForNew);
    }


    public Optional<AccessoryContainer> findById(long id) {
        return containerRepository.findById(id);
    }


    public void delete(AccessoryContainer xContainer) {
        containerRepository.delete(xContainer);
    }


    public void deleteById(Long xId) {
        containerRepository.deleteById(xId);
    }

    public List<AccessoryContainer> dublsByX(AccessoryContainer xContainer) {
        AccessoryDto filters = new AccessoryDto();
        filters.setProductCategory(xContainer.getProductCategory());
        filters.setFirm(xContainer.getFirm());
        filters.setModel(xContainer.getModel());
        filters.setDetails(xContainer.getDetails());
        filters.setPurchase(xContainer.getPurchase());
        filters.setSale(xContainer.getSale());
        ///////////////////////////////////
        filters.setAccessoryCategory(xContainer.getAccessoryCategory());
        ///////////////////////////////////

        return dublsByDto(filters);
    }

    public List<AccessoryContainer> dublsByDto(AccessoryDto filters) {
        Specification<AccessoryContainer> allLike = accessorySpec.allLike(filters);
        return allLike == null ? new ArrayList<>() : containerRepository.findAll(allLike);
    }


    public Specification<AccessoryContainer> allContains(AccessoryDto filters) {
        return accessorySpec.allContains(filters);
    }


    public Specification<AccessoryContainer> allLike(AccessoryDto filters) {
        return accessorySpec.allLike(filters);
    }

    public AccessoryContainer createNewEmptyContainer() {
        return new AccessoryContainer();
    }
}
