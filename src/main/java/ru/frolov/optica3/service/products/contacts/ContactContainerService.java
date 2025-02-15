package ru.frolov.optica3.service.products.contacts;

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
import ru.frolov.optica3.cache.products.ContactCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.defaults.Dioptre;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.entity.products.contact.ContactContainer;
import ru.frolov.optica3.entity.products.contact.ContactUnit;
import ru.frolov.optica3.enums.contact_enums.*;
import ru.frolov.optica3.repository.products.contact_repo.ContactContainerRepository;
import ru.frolov.optica3.repository.products.contact_repo.ContactUnitRepository;
import ru.frolov.optica3.specification.products.ContactSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
public class ContactContainerService {


    private final ContactContainerRepository containerRepository;
    private final ContactUnitRepository unitRepository;
    private final ContactSpec spec;
    private final ContactCache cache;
    private final OrderCache orderCache;

    //----------------------------------------------------------------------------

    public void transferToModel(
            Model model,
            Page<ContactContainer> pageForModel,
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
        model.addAttribute("filters", cache.getDto());                                                         //  !

        // db positions
        List<ContactContainer> dbContainers = containerRepository.findAll();
        model.addAttribute("dbPositions", dbContainers.size());

        // found positions
        model.addAttribute("foundPositions", pageForModel.getTotalElements());

        // db units
        List<ContactUnit> dbUnits = unitRepository.findAll();
        model.addAttribute("dbUnits", dbUnits.size());

        // foundUnits
        if (cache.getSpec() == null) {
            model.addAttribute("foundUnits", dbUnits.size());
        } else {
            List<ContactContainer> foundContainers = containerRepository.findAll(cache.getSpec());
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

        if (cache.getSpec() == null) {
            model.addAttribute("message_itsFullList", "message_itsFullList");
        }

        if (cache.getMode()) {
            model.addAttribute("editMode", "editMode");
        }

        if (copyToSearch != null) {
            model.addAttribute("copyToSearch", "copyToSearch");
        }

        ///////////////////////////
        // contact
        model.addAttribute("contactDesigns", ContactDesign.values());

        model.addAttribute("contactPeriods", ContactPeriod.values());

        model.addAttribute("contactOxygens", ContactOxygen.values());

        model.addAttribute("contactWaters", ContactWater.values());

        model.addAttribute("contactDiameters", ContactDiameter.values());

        model.addAttribute("contactRadiuses", ContactRadius.values());

        model.addAttribute("dioptres", Dioptre.getDioptresStringList());
        ///////////////////////////

        // currentOrder
        model.addAttribute("currentOrder", getOrderCache().getCurrentOrder());
    }

    public void killDubls(ContactContainer xContainer, List<ContactContainer> sublistExcept0) {
        if (!sublistExcept0.isEmpty()) {
            for (ContactContainer c : sublistExcept0) {
                List<ContactUnit> dublUnitList = c.getUnitList();
                List<ContactUnit> xContainerUnitList = xContainer.getUnitList();
                xContainerUnitList.addAll(dublUnitList);
                containerRepository.delete(c);
            }
        }
    }


    public List<ContactContainer> getListContainersNOSpec() {
        return containerRepository.findAll();
    }


    public Page<ContactContainer> getPageNOSpec(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return containerRepository.findAll(pageable);
    }

    public List<ContactContainer> getListContainersBYSpec(Specification<ContactContainer> specification) {
        return containerRepository.findAll(specification);
    }

    public Page<ContactContainer> getPageBYSpec(Integer pageNumber, Specification<ContactContainer> specification) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return containerRepository.findAll(specification, pageable);
    }

    public long howManyUnitsInList(List<ContactContainer> foundContainers) {
        long result = 0;
        for (ContactContainer c : foundContainers) {
            result += c.getUnitList().size();
        }
        return result;
    }

    public long howManyUnitsBySpec(ContactDto filters) {

        Specification<ContactContainer> specification = spec.allContains(filters);
        List<ContactContainer> containersBySpec = getListContainersBYSpec(specification);
        return howManyUnitsInList(containersBySpec);
    }


    public List<ContactUnit> getListUnitsNOSpec() {
        return unitRepository.findAll();
    }


    public Page<ContactContainer> getXPage(ContactContainer xContainer) {
        /*
         * Метод ищет page, содержащую нужный контейнер. (На xContainer будет установлена 'галочка').
         * */

        Page<ContactContainer> xPage = null;

        if (xContainer != null) {


            // если catchXPage = true, то выходим из цикла
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
                for ( ContactContainer container : xPage.getContent()) {
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


    public void save(ContactContainer containerForNew) {
        containerRepository.save(containerForNew);
    }


    public Optional<ContactContainer> findById(long id) {
        return containerRepository.findById(id);
    }


    public void delete(ContactContainer xContainer) {
        containerRepository.delete(xContainer);
    }


    public void deleteById(Long xId) {
        containerRepository.deleteById(xId);
    }


    public ContactContainer createNewEmptyContainer() {
        return new ContactContainer();
    }


    public List<ContactContainer> dublsByX(ContactContainer xContainer) {
        ContactDto filters = new ContactDto();
        filters.setProductCategory(xContainer.getProductCategory());
        filters.setFirm(xContainer.getFirm());
        filters.setModel(xContainer.getModel());
        filters.setDetails(xContainer.getDetails());
        filters.setPurchase(xContainer.getPurchase());
        filters.setSale(xContainer.getSale());
        /////////////////////////
        filters.setContactDesign(xContainer.getContactDesign());
        filters.setContactPeriod(xContainer.getContactPeriod());
        filters.setContactOxygen(xContainer.getContactOxygen());
        filters.setContactWater(xContainer.getContactWater());
        filters.setContactDiameter(xContainer.getContactDiameter());
        filters.setContactRadius(xContainer.getContactRadius());
        filters.setDioptre(xContainer.getDioptre());
        /////////////////////////

        return dublsByDto(filters);
    }
    public List<ContactContainer> dublsByDto(ContactDto filters) {
        Specification<ContactContainer> allLike = spec.allLike(filters);
        return allLike == null ? new ArrayList<>() : containerRepository.findAll(allLike);
    }


    public Specification<ContactContainer> allContains(ContactDto filters) {
        return spec.allContains(filters);
    }


    public Specification<ContactContainer> allLike(ContactDto filters) {
        return spec.allLike(filters);
    }
}
