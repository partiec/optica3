package ru.frolov.optica3.controller.products.contact_controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.frolov.optica3.service.products.contacts.ContactContainerService;
import ru.frolov.optica3.service.products.contacts.ContactUnitService;

@RequestMapping("api/contact")
@RequiredArgsConstructor
public abstract class AbstractContactController {

    protected final ContactContainerService containerService;
    protected final ContactUnitService unitService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleErrors(MethodArgumentNotValidException notValidException,
                               Model model,
                               BindingResult br) {

        // в модель:
        // errors для отображения messages
        model.addAttribute("errors", br.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        // page должна остаться как была
        model.addAttribute("page", containerService.getCache().getPage());
        // filters должны остаться те же
        model.addAttribute("filters", containerService.getCache().getDto());

        // cache не требуется

        return "displayContacts";
    }
}
