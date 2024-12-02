package ru.frolov.optica3.controller.frame_controllers;

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
import ru.frolov.optica3.cache.frame_caches.FiltersPayload_FrameCache;
import ru.frolov.optica3.cache.frame_caches.Page_FrameCache;
import ru.frolov.optica3.cache.frame_caches.SpecStatus_FrameCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.frame.Frame;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.frame_payloads.Filters_FramePayload;
import ru.frolov.optica3.service.frame_services.Cache_FrameService;
import ru.frolov.optica3.service.frame_services.FrameContainerService;
import ru.frolov.optica3.service.frame_services.FrameService;
import ru.frolov.optica3.service.frame_services.Model_FrameService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Create_FrameController {

    private final FrameService frameService;
    private final FrameContainerService containerService;
    private final Model_FrameService modelFrameService;
    private final Cache_FrameService cacheFrameService;


    //------------------------------------------------------------------------------------------------------------

    @PostMapping("api/createFrame")
    public String createFrame(@Valid Filters_FramePayload filters,
                              Model model) {

        System.out.println("___createFrame(); filters(" +
                filters.firm() + "," +
                filters.model() + "," +
                //////////////////////////////////
                filters.useType() + "," +
                filters.installType() + "," +
                filters.material() + "," +
                //////////////////////////////////
                filters.details() + "," +
                filters.purchase() + "," +
                filters.sale() + ");");


        FrameContainer xContainer;
        // существуют ли уже такие контейнеры?
        List<FrameContainer> dubls = this.containerService.dubls(filters);
        // если такого еще нет
        if (dubls.isEmpty()) {
            System.out.println("****************ТАКИХ КОНТЕЙНЕРОВ НЕТУ! СОЗДАЕМ НОВЫЙ. В модель -> ничего");
            // создать новый контейнер
            xContainer = new FrameContainer();
            xContainer.setFirm(filters.firm());
            xContainer.setModel(filters.model());
            ///////////////////////////////////////////
            xContainer.setUseType(filters.useType());
            xContainer.setInstallType(filters.installType());
            xContainer.setMaterial(filters.material());
            ///////////////////////////////////////////
            xContainer.setDetails(filters.details());
            xContainer.setPurchase(filters.purchase());
            xContainer.setSale(filters.sale());
        } else {
            // если такой уже есть
            xContainer = dubls.get(0);
            this.containerService.killDubls(xContainer, dubls.subList(1, dubls.size()));
            // оповестить пользователя, что такие контейнеры уже есть и предложить +1
            model.addAttribute("sameContainerAlreadyExists", "sameContainerAlreadyExists");
            System.out.println("****************** ТАКОЙ КОНТЕЙНЕР УЖЕ ЕСТЬ! В модель ->  sameContainerAlreadyExists !!!");

        }

        // создать new frame
        Frame newFrame = new Frame();
        newFrame.setFirm(xContainer.getFirm());
        newFrame.setModel(xContainer.getModel());
        //////////////////////////////////////////////////////
        newFrame.setUseType(xContainer.getUseType());
        newFrame.setInstallType(xContainer.getInstallType());
        newFrame.setMaterial(xContainer.getMaterial());
        //////////////////////////////////////////////////////
        newFrame.setDetails(xContainer.getDetails());
        newFrame.setPurchase(xContainer.getPurchase());
        newFrame.setSale(xContainer.getSale());

        // связать newFrame и xContainer
        newFrame.setFrameContainer(xContainer);
        xContainer.addToFrameList(newFrame);
        // сохранить контейнер (при этом newFrame сохранится каскадно)
        this.containerService.save(xContainer);


        // Подготавливаем данные для модели
        // -------------------------------->
        // page должна быть no spec
        SpecStatus_FrameCache.setApplied(false);
        // page создаем с помощью локального метода getXPageNoSpec()!, поскольку она должна содержать xContainer
        Page<FrameContainer> actualPage = getXPageNoSpec(xContainer);
        // xId нужен будет в html для галочки (которая помечает созданный контейнер)
        Long xId = xContainer.getId();
        // filters уже не нужны, кэшируем костыль
        FiltersPayload_FrameCache.setFiltersFramePayload(new Filters_FramePayload(null, null, null, null, null, null, null, null));
        //----------------------------/

        // В модель
        // -------->
        modelFrameService.transferModel(
                model,
                actualPage,
                null,
                xId,
                null
        );

        // Контрольное кеширование
        cacheFrameService.cacheAttributes(
                actualPage, // (page) - with xContainer
                null, // (specStatus) - уже был кэширован при подготовке данных для модели
                null, // (filters) - уже был кэширован пустой костыль при подготовке данных для модели
                null   // (framePayload) - здесь не нужна
        );

        return "displayFrames";

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleErrors(MethodArgumentNotValidException notValidException,
                               Model model,
                               BindingResult br) {

        // в модель:
        // errors для отображения messages
        model.addAttribute("errors", br.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        // page должна остаться как была
        model.addAttribute("page", Page_FrameCache.getPage());
        // filters должны остаться те же
        model.addAttribute("filters", FiltersPayload_FrameCache.getFiltersPayload());

        // cache не требуется

        return "displayFrames";
    }


    private Page<FrameContainer> getXPageNoSpec(FrameContainer xContainer) {
        // нужна page с галочкой на xContainer.
        //-----------------------------------------------------------------------------
        Page<FrameContainer> xPage = null;

        if (xContainer != null) {


            // если catchXPage = true, то выходим из цикла
            boolean catchXPage = false;

            // перебираем страницы, пока не найдем нужную
            for (int i = 0; ; i++) {

                System.out.println("итерация № " + i);

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
                for (FrameContainer container : xPage.getContent()) {
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
