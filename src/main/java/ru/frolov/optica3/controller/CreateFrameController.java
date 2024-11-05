package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
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
    public String createFrame(FiltersPayload filters,
                              RedirectAttributes ra) {

        System.out.println("___createFrame()...");

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

        // связь newFrame и xContainer
        newFrame.setFrameContainer(xContainer);
        xContainer.addToFrameList(newFrame);
        // сохранить контейнер
        this.containerService.save(xContainer);

        // для display нужен номер страницы, содержащей xContainer
        //------------------------------------------------------------
        Page<FrameContainer> xPage = null;
        int xPageNumber;

        boolean catchXPage = false;

        // перебираем страницы, пока не найдем нужную
        for (int i = 0; ; i++) {

            System.out.println("...итерация № " + i);

            // если нужная страница найдена, выходим из цикла
            if (catchXPage) break;

            // На каждой итерации создать страницу номер i
            Pageable pageable = PageRequest.of(
                    i,
                    Defaults.PAGE_SIZE,
                    Sort.by(Sort.Direction.ASC, "firm"));
            xPage = this.containerService.getPage(pageable);
            System.out.println("...создана xPage № " + xPage.getNumber());

            // перебирать content каждой страницы пока не найдем содержащую xContainer
            for (FrameContainer container : xPage.getContent()) {
                // если нашелся xContainer, то найдена нужная страница
                if (container.equals(xContainer)) {
                    System.out.println("...контейнер найден...итерация все еще № " + i + "  ?????????????");
                    catchXPage = true;
                    break;
                }
            }
        }

        xPageNumber = xPage.getNumber();
        System.out.println("///noSpec/" + xPageNumber);

        ra.addFlashAttribute("xId", xContainer.getId());

        // после create поля для НАЙТИ/СОЗДАТЬ уже не нужны
        Cache.setFiltersPayload(new FiltersPayload(null,null,null,null,null));

        return "redirect:/api/display/noSpec/%d".formatted(xPageNumber);
    }
}
