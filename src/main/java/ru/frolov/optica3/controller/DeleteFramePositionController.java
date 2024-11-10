package ru.frolov.optica3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.frolov.optica3.cache.Cache;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.FrameContainerService;
import ru.frolov.optica3.service.FrameService;

@Controller
public class DeleteFramePositionController {

    private final FrameContainerService containerService;
    private final FrameService frameService;

    public DeleteFramePositionController(FrameContainerService containerService, FrameService frameService) {
        this.containerService = containerService;
        this.frameService = frameService;
    }

// Проблема: Наша задача удалить контейнер, а потом отобразить ту же таблицу, но с учетом удаления. При удалении
// текущее состояние таблицы может быть и allFrames, и bySpec. Метод display() должен знать - каково текущее состояние.
//
//-----------------------------------------------------------------------------------------------

    @Transactional
    @PostMapping("api/deletePosition")
    public String deletePosition(@RequestParam(name = "xId") Long xId,
                                 @RequestParam(name = "currentPageNumber") Integer currentPageNumber,
                                 // Нужна для:
                                 // 1. Отображения текущих значений в поиске.
                                 // 2. Для создания ТАКОЙ ЖЕ страницы по таким же критериям.
                                 FramePayload filters) {

        System.out.println("___deletePosition()...");

        this.containerService.deleteById(xId);

        Cache.setFramePayload(filters);


        return "redirect:/api/display/unknownSpec/%d".formatted(currentPageNumber);
    }
}
