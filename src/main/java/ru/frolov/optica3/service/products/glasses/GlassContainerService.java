package ru.frolov.optica3.service.products.glasses;

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
import ru.frolov.optica3.cache.products.GlassCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.defaults.Dioptre;
import ru.frolov.optica3.dto.products.GlassDto;
import ru.frolov.optica3.entity.products.glass.GlassContainer;
import ru.frolov.optica3.entity.products.glass.GlassUnit;
import ru.frolov.optica3.enums.glass_enums.GlassCoat;
import ru.frolov.optica3.enums.glass_enums.GlassDesign;
import ru.frolov.optica3.enums.glass_enums.GlassMaterial;
import ru.frolov.optica3.repository.products.glass_repo.GlassContainerRepository;
import ru.frolov.optica3.repository.products.glass_repo.GlassUnitRepository;
import ru.frolov.optica3.specification.products.GlassSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class GlassContainerService {
    private final GlassContainerRepository containerRepository;
    private final GlassUnitRepository unitRepository;
    private final GlassSpec spec;
    private final GlassCache cache;
    private final OrderCache orderCache;

    //---------------------------------------------------------------------------------------------------------------------------------------------------
    public void transferToModel(
            Model model,
            Page<GlassContainer> pageForModel,
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
        List<GlassContainer> dbContainers = containerRepository.findAll();
        model.addAttribute("dbPositions", dbContainers.size());

        // found positions
        model.addAttribute("foundPositions", pageForModel.getTotalElements());

        // db units
        List<GlassUnit> dbUnits = unitRepository.findAll();
        model.addAttribute("dbUnits", dbUnits.size());

        // foundUnits
        if (getCache().getSpec() == null) {
            model.addAttribute("foundUnits", dbUnits.size());
        } else {
            List<GlassContainer> foundContainers = containerRepository.findAll(getCache().getSpec());
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

        ////////////////////////////
        // glass
        model.addAttribute("glassMaterials", GlassMaterial.values());

        model.addAttribute("glassDesigns", GlassDesign.values());

        model.addAttribute("glassCoats", GlassCoat.values());

        model.addAttribute("dioptres", Dioptre.getDioptresStringList());
        ////////////////////////////


        // currentOrder
        model.addAttribute("currentOrder", getOrderCache().getCurrentOrder());
    }


    public void killDubls(GlassContainer xContainer, List<GlassContainer> sublistExcept0) {
        if (!sublistExcept0.isEmpty()) {
            for (GlassContainer c : sublistExcept0) {
                List<GlassUnit> dublUnitList = c.getUnitList();
                List<GlassUnit> xContainerUnitList = xContainer.getUnitList();
                xContainerUnitList.addAll(dublUnitList);
                containerRepository.delete(c);
            }
        }
    }

    public List<GlassContainer> getListContainersNOSpec() {
        return containerRepository.findAll();
    }


    public Page<GlassContainer> getPageNOSpec(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return containerRepository.findAll(pageable);
    }

    public List<GlassContainer> getListContainersBYSpec(Specification<GlassContainer> specification) {
        return containerRepository.findAll(specification);
    }

    public Page<GlassContainer> getPageBYSpec(Integer pageNumber, Specification<GlassContainer> specification) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return containerRepository.findAll(specification, pageable);
    }

    public long howManyUnitsInList(List<GlassContainer> foundContainers) {
        long result = 0;
        for (GlassContainer c : foundContainers) {
            result += c.getUnitList().size();
        }
        System.out.println("\t\t\tc*.howManyUnitsInList()...принят List:" + foundContainers + "; return:" + result);
        return result;
    }

    public long howManyUnitsBySpec(GlassDto filters) {

        Specification<GlassContainer> specification = spec.allContains(filters);
        List<GlassContainer> containersBySpec = getListContainersBYSpec(specification);
        return howManyUnitsInList(containersBySpec);
    }


    public List<GlassUnit> getListUnitsNOSpec() {
        return unitRepository.findAll();
    }


    public Page<GlassContainer> getXPage(GlassContainer xContainer) {
        /*
         * Метод ищет page, содержащую нужный контейнер. (На xContainer будет установлена 'галочка').
         * */

        Page<GlassContainer> xPage = null;

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
                for (GlassContainer container : xPage.getContent()) {
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


    public void save(GlassContainer containerForNew) {
        containerRepository.save(containerForNew);
    }


    public Optional<GlassContainer> findById(long id) {
        return containerRepository.findById(id);
    }


    public void delete(GlassContainer xContainer) {
        containerRepository.delete(xContainer);
    }


    public void deleteById(Long xId) {
        containerRepository.deleteById(xId);
    }


    public GlassContainer createNewEmptyContainer() {
        return new GlassContainer();
    }


    public List<GlassContainer> dublsByX(GlassContainer xContainer) {
        GlassDto filters = new GlassDto();
        filters.setProductCategory(xContainer.getProductCategory());
        filters.setFirm(xContainer.getFirm());
        filters.setModel(xContainer.getModel());
        filters.setDetails(xContainer.getDetails());
        filters.setPurchase(xContainer.getPurchase());
        filters.setSale(xContainer.getSale());
        ////////////////////////
        filters.setGlassMaterial(xContainer.getGlassMaterial());
        filters.setGlassDesign(xContainer.getGlassDesign());
        filters.setGlassCoat(xContainer.getGlassCoat());
        filters.setDioptre(xContainer.getDioptre());
        ////////////////////////
        return dublsByDto(filters);
    }

    public List<GlassContainer> dublsByDto(GlassDto filters) {
        Specification<GlassContainer> allLike = spec.allLike(filters);
        return allLike == null ? new ArrayList<>() : containerRepository.findAll(allLike);
    }


    public Specification<GlassContainer> allContains(GlassDto filters) {
        return spec.allContains(filters);
    }


    public Specification<GlassContainer> allLike(GlassDto filters) {
        return spec.allLike(filters);
    }
}
