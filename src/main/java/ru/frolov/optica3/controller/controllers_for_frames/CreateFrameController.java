package ru.frolov.optica3.controller.controllers_for_frames;

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
import ru.frolov.optica3.cache.FiltersPayloadCache;
import ru.frolov.optica3.cache.PageCache;
import ru.frolov.optica3.cache.SpecStatusCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.frame.Frame;
import ru.frolov.optica3.entity.frame.FrameContainer;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.service.CacheService;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;
import ru.frolov.optica3.service.ModelService;

@Controller
@RequiredArgsConstructor
public class CreateFrameController {

    private final FrameService frameService;
    private final FrameContainerService containerService;
    private final ModelService modelService;
    private final CacheService cacheService;


    private FrameContainer xContainer;
    //------------------------------------------------------------------------------------------------------------

    @PostMapping("api/createFrame")
    public String createFrame(@Valid FiltersPayload filters,
                              Model model) {

        System.out.println("___createFrame(); filters(" +
                filters.firm() + "," +
                filters.model() + "," +
                filters.frameInstallType() + "," +
                filters.frameMaterial() + "," +
                filters.details() + "," +
                filters.purchase() + "," +
                filters.sale() + ");");

        // создать new frame
        Frame newFrame = new Frame();
        newFrame.setFirm(filters.firm());
        newFrame.setModel(filters.model());
        newFrame.setFrameInstallType(filters.frameInstallType());
        newFrame.setFrameMaterial(filters.frameMaterial());
        newFrame.setDetails(filters.details());
        newFrame.setPurchase(filters.purchase());
        newFrame.setSale(filters.sale());

        // создать новый контейнер если такого еще нет
        xContainer = this.containerService.findSame(filters.firm(), filters.model());
        if (xContainer == null) {

            xContainer = new FrameContainer();
            xContainer.setFirm(filters.firm());
            xContainer.setModel(filters.model());
            xContainer.setFrameInstallType(filters.frameInstallType());
            xContainer.setFrameMaterial(filters.frameMaterial());
            xContainer.setDetails(filters.details());
            xContainer.setPurchase(filters.purchase());
            xContainer.setSale(filters.sale());
        }

        // связать newFrame и xContainer
        newFrame.setFrameContainer(xContainer);
        xContainer.addToFrameList(newFrame);
        // сохранить контейнер (при этом newFrame сохранится каскадно)
        this.containerService.save(xContainer);


        // Подготавливаем данные для модели
        // -------------------------------->
        // page должна быть no spec
        SpecStatusCache.setApplied(false);
        // page создаем с помощью локального метода getXPageNoSpec()!, поскольку она должна содержать xContainer
        Page<FrameContainer> actualPage = getXPageNoSpec(xContainer);
        // xId нужен будет в html для галочки (которая помечает созданный контейнер)
        Long xId = xContainer.getId();
        // filters уже не нужны, кэшируем костыль
        FiltersPayloadCache.setFiltersPayload(new FiltersPayload(null,null, null, null, null, null, null));
        //----------------------------/

        // В модель
        // -------->
        modelService.transferModel(
                model,
                actualPage,
                null,
                xId,
                null
        );

        // Контрольное кеширование
        cacheService.cacheAttributes(
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
        model.addAttribute("page", PageCache.getPage());
        // filters должны остаться те же
        model.addAttribute("filters", FiltersPayloadCache.getFiltersPayload());

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
