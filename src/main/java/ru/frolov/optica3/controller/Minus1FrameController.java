package ru.frolov.optica3.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.entity.FrameContainer;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;
import ru.frolov.optica3.spec.FrameSpec;

import java.util.List;

@Controller
public class Minus1FrameController {

    private final FrameContainerService containerService;
    private final FrameService frameService;

    public Minus1FrameController(FrameContainerService containerService, FrameService frameService) {
        this.containerService = containerService;
        this.frameService = frameService;
    }


//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/minus1")
    public String minus1(@RequestParam(name = "xId") Long xId,
                         @RequestParam(name = "pageNumber") Integer pageNumber) {

        System.out.println("___minus1()...");

        // Находим нужный контейнер по полученному id
        FrameContainer xContainer = this.containerService.findById(xId).get();
        List<Frame> xList = xContainer.getFrameList();

        // Если в контейнере осталась лишь одна оправа, то удалить весь контейнер из БД
        if (xList.size() <= 1) {
            this.containerService.delete(xContainer);
        } else {
            // Иначе удалить из контейнера только НУЛЕВУЮ (по-сути первую) оправу
            Frame firstElement = xContainer.getFrameList().get(0);
            xContainer.getFrameList().remove(firstElement);
            // сохранить контейнер в БД
            this.containerService.save(xContainer);
        }

        return "redirect:/api/display/unknownSpec/%d".formatted(pageNumber);
    }
}
