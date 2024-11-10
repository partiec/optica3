package ru.frolov.optica3.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FiltersPayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;

@Controller
public class CreateFrameController {

    private final FrameService frameService;
    private final FrameContainerService containerService;

    public CreateFrameController(FrameService frameService, FrameContainerService containerService) {
        this.frameService = frameService;
        this.containerService = containerService;
    }
    //------------------------------------------------------------------------------------------------------------

    @PostMapping("api/createFrame")
    public String createFrame(@Valid FiltersPayload filters,
                              RedirectAttributes ra,
                              BindingResult bindingResult) {

        System.out.println("___createFrame()...");
        System.out.println("...bindingResult no errors...");


        // создать new frame
        Frame newFrame = new Frame();
        newFrame.setFirm(filters.firm());
        newFrame.setModel(filters.model());
        newFrame.setDetails(filters.details());
        newFrame.setPurchase(filters.purchase());
        newFrame.setSale(filters.sale());
        // создать новый контейнер
        FrameContainer xContainer = new FrameContainer();
        xContainer.setFirm(filters.firm());
        xContainer.setModel(filters.model());
        xContainer.setDetails(filters.details());
        xContainer.setPurchase(filters.purchase());
        xContainer.setSale(filters.sale());

        // связать newFrame и xContainer
        newFrame.setFrameContainer(xContainer);
        xContainer.addToFrameList(newFrame);
        // сохранить контейнер (newFrame сохранится каскадно)
        this.containerService.save(xContainer);

        // вычислить номер страницы, в которой xContainer
        int xPageNumber = evaluateXPageNumber(xContainer);

        ra.addFlashAttribute("xId", xContainer.getId());

        // поля НАЙТИ/СОЗДАТЬ отобразятся пустыми
        Cache.setFiltersPayload(new FiltersPayload(null, null, null, null, null));

        return "redirect:/api/display/noSpec/%d".formatted(xPageNumber);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleErrors(MethodArgumentNotValidException notValidException,
                               RedirectAttributes ra,
                               BindingResult br){

        System.out.println("...@ExceptionHandler...");

        ra.addFlashAttribute("errors", br.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());

        return "redirect:/api/display/noSpec/0";
    }


    private int evaluateXPageNumber(FrameContainer xContainer) {
        // В display нужно будет отобразить страницу с xContainer. Высчитываем номер этой страницы
        //------------------------------------------------------------
        Page<FrameContainer> xPage = null;
        // номер страницы, содержащей xContainer
        int xPageNumber;

        // найдена ли нужная страница
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
            for (FrameContainer container : xPage.getContent()) {
                // если нашелся xContainer, то найдена нужная страница
                if (container.equals(xContainer)) {
                    catchXPage = true;
                    break;
                }
            }
        }

        xPageNumber = xPage.getNumber();
        return xPageNumber;
    }


}
