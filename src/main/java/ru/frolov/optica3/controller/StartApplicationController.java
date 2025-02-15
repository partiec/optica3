package ru.frolov.optica3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.frolov.optica3.cache._order.OrderCache;
import ru.frolov.optica3.cache.products.AccessoryCache;
import ru.frolov.optica3.cache.products.ContactCache;
import ru.frolov.optica3.cache.products.FrameCache;
import ru.frolov.optica3.cache.products.GlassCache;
import ru.frolov.optica3.controller.products.frame_controllers.Start_FrameController;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.dto.products.AccessoryDto;
import ru.frolov.optica3.dto.products.ContactDto;
import ru.frolov.optica3.dto.products.FrameDto;
import ru.frolov.optica3.dto.products.GlassDto;

@Controller
@RequiredArgsConstructor
public class StartApplicationController {

    private final Start_FrameController startFrameController;
    private final OrderCache orderCache;
    private final AccessoryCache accessoryCache;
    private final ContactCache contactCache;
    private final FrameCache frameCache;
    private final GlassCache glassCache;


    @GetMapping("api/start")
    public String startApplication(Model model) {
        System.out.println("\t! " + getClass().getSimpleName() + ".startApplication()");

        // заполнить кэш начальными значениями
        orderCache.cacheAttributesIfNotNull(
                Page.empty(),
                new OrderAndClientDto(),
                null,
                null,
                false,
                false
        );
        accessoryCache.cacheAttributesNotNull(
                Page.empty(),
                new AccessoryDto(),
                null,
                null,
                null,
                false
        );
        contactCache.cacheAttributesNotNull(
                Page.empty(),
                new ContactDto(),
                null,
                null,
                false
        );
        frameCache.cacheAttributesNotNull(
                Page.empty(),
                new FrameDto(),
                null,
                null,
                false
        );
        glassCache.cacheAttributesNotNull(
                Page.empty(),
                new GlassDto(),
                null,
                null,
                false
        );


        return this.startFrameController.start(model);
    }
}
