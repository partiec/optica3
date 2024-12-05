package ru.frolov.optica3.controller.glass_controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.cache.glass_caches.FiltersPayload_GlassCache;
import ru.frolov.optica3.cache.glass_caches.Page_GlassCache;
import ru.frolov.optica3.cache.glass_caches.SpecStatus_GlassCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.glass.Glass;
import ru.frolov.optica3.entity.glass.GlassContainer;
import ru.frolov.optica3.payload.glass_payloads.Filters_GlassPayload;
import ru.frolov.optica3.service.glass_services.Cache_GlassService;
import ru.frolov.optica3.service.glass_services.GlassContainerService;
import ru.frolov.optica3.service.glass_services.GlassService;
import ru.frolov.optica3.service.glass_services.Model_GlassService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Create_GlassController {

    private final GlassService glassService;
    private final GlassContainerService containerService;
    private final Model_GlassService modelService;
    private final Cache_GlassService cacheService;


    //------------------------------------------------------------------------------------------------------------

    @PostMapping("api/createGlass")
    public String createGlass(@Valid Filters_GlassPayload filters,
                              Model model) {

        GlassContainer xContainer;

        // существуют ли уже такие контейнеры?
        List<GlassContainer> dubls = this.containerService.dubls(filters);
        // если такого еще нет
        if (dubls.isEmpty()) {
            // создать новый контейнер
            xContainer = new GlassContainer();
            xContainer.setFirm(filters.firm());
            ///////////////////////////////////////////
            xContainer.setMaterial(filters.material());
            xContainer.setDesign(filters.design());
            xContainer.setCoat(filters.coat());
            ///////////////////////////////////////////
            xContainer.setDetails(filters.details());
            xContainer.setPurchase(filters.purchase());
            xContainer.setSale(filters.sale());
            xContainer.setDioptre(filters.dioptre());
        } else {
            // если такой уже есть
            xContainer = dubls.get(0);
            this.containerService.killDubls(xContainer, dubls.subList(1, dubls.size()));
            // оповестить пользователя, что такие контейнеры уже есть и предложить +1
            model.addAttribute("sameContainerAlreadyExists", "sameContainerAlreadyExists");
        }

        // создать new unit
        Glass newGlass = new Glass();
        newGlass.setFirm(xContainer.getFirm());
        /////////////////////////////////////////
        newGlass.setMaterial(xContainer.getMaterial());
        newGlass.setDesign(xContainer.getDesign());
        newGlass.setCoat(xContainer.getCoat());
        //////////////////////////////////////////////////////
        newGlass.setDetails(xContainer.getDetails());
        newGlass.setPurchase(xContainer.getPurchase());
        newGlass.setSale(xContainer.getSale());
        newGlass.setDioptre(xContainer.getDioptre());

        // связать newUnit и xContainer
        newGlass.setGlassContainer(xContainer);
        xContainer.addToGlassList(newGlass);
        // сохранить контейнер (при этом newFrame сохранится каскадно)
        this.containerService.save(xContainer);


        // Подготавливаем данные для модели
        // -------------------------------->
        // page должна быть no spec
        SpecStatus_GlassCache.setApplied(false);
        // page создаем с помощью локального метода getXPageNoSpec()!, поскольку она должна содержать xContainer
        Page<GlassContainer> actualPage = getXPageNoSpec(xContainer);
        // xId нужен будет в html для галочки (которая помечает созданный контейнер)
        Long xId = xContainer.getId();
        // filters уже не нужны, кэшируем костыль
        FiltersPayload_GlassCache.setFiltersPayload(new Filters_GlassPayload(null, null, null, null, null, null, null, null));
        //----------------------------/

        // В модель
        // -------->
        modelService.transferModel(
                model,
                actualPage,
                null,
                xId,
                null,
                null);

        // Контрольное кеширование
        cacheService.cacheAttributes(
                actualPage, // (page) - with xContainer
                null, // (specStatus) - уже был кэширован при подготовке данных для модели
                null, // (filters) - уже был кэширован пустой костыль при подготовке данных для модели
                null   // (framePayload) - здесь не нужна
        );


        return "displayGlasses";

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleErrors(MethodArgumentNotValidException notValidException,
                               Model model,
                               BindingResult br) {

        // в модель:
        // errors для отображения messages
        model.addAttribute("errors", br.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        // page должна остаться как была
        model.addAttribute("page", Page_GlassCache.getPage());
        // filters должны остаться те же
        model.addAttribute("filters", FiltersPayload_GlassCache.getFiltersPayload());

        // cache не требуется

        return "displayGlasses";
    }


    private Page<GlassContainer> getXPageNoSpec(GlassContainer xContainer) {
        // нужна page с галочкой на xContainer.
        //-----------------------------------------------------------------------------
        Page<GlassContainer> xPage = null;

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
                xPage = this.containerService.getPage(pageable);

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


}
