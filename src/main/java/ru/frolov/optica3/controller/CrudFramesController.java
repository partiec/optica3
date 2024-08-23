package ru.frolov.optica3.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.frolov.optica3.entity.Frame;
import ru.frolov.optica3.payload.FramePayload;
import ru.frolov.optica3.service.Defaults;
import ru.frolov.optica3.service.FrameService;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/crud")
public class CrudFramesController {
    /*
    CrudFramesController выполняет crud операции.

    - create
    - begin (read)
    - update
    - delete
 */


    private final FrameService frameService;

    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->

    // 1.
    @PostMapping("createFrame")
    @Transactional
    public String createFrame(@Valid FramePayload framePayload,
                              BindingResult bindingResult,
                              RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            ra.addFlashAttribute("errors", errors);
            ra.addFlashAttribute("framePayload", framePayload);
        } else {
            if (framePayload != null) {

                // если такой объект уже есть
                // ...


                Frame frame = new Frame();

                frame.setFirm(framePayload.firm());
                frame.setModel(framePayload.model());
                frame.setDetails(framePayload.details());
                frame.setPurchasePrice(framePayload.purchasePrice());
                frame.setSalePrice(framePayload.salePrice());
                this.frameService.save(frame);
            }
        }
        return "redirect:/api/arrange/pagedAndSortedFrames/%d/%s".formatted(1, Defaults.SORT_FIELD);
    }

    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->

    // 2.
    @GetMapping("readFrames")
    public String readFrames() {

        return "redirect:/api/arrange/pagedAndSortedFrames/%d/%s".formatted(1, Defaults.SORT_FIELD);
    }

//---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->

    // 3.
    @PostMapping("updateFrame")
    @Transactional
    public String updateFrame(
            @RequestParam(name = "firm", required = false) String firm,
            @RequestParam(name = "model", required = false) String model,
            @RequestParam(name = "details", required = false) String details,

            @RequestParam(name = "purchasePrice", required = false) BigDecimal purchasePrice,
            @RequestParam(name = "salePrice", required = false) BigDecimal salePrice,

            @RequestParam(name = "whichField") String whichField,
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "pageNumber", required = false) int pageNumber,
            RedirectAttributes ra
    ) {


        Frame frame = this.frameService.findById(id).get();

        if (whichField.equals("firm") && (!firm.isBlank())) {
            frame.setFirm(firm);
        } else if (whichField.equals("model") && (!model.isBlank())) {
            frame.setModel(model);
        } else if (whichField.equals("details")) {
            frame.setDetails(details);
        } else if (whichField.equals("purchasePrice")) {
            frame.setPurchasePrice(purchasePrice);
        } else if (whichField.equals("salePrice")) {
            frame.setSalePrice(salePrice);
        }
        this.frameService.save(frame);

        ra.addFlashAttribute("pageNumber", pageNumber);

        return "redirect:/api/arrange/pagedAndSortedFrames/%d/%s".formatted(1, Defaults.SORT_FIELD);
    }

    //---------------------------need for view---------------------------------------------------- -->
    // ${errors} -->
    // ${frames} -->
    // ${pageNumber} -->
    // ${totalFrames} -->
    // ${totalPages} -->
    // ${} -->
    // -------------------------------------------------------------------------------------------- -->

    // 4.
    @PostMapping("deleteFrame/{id:\\d+}")
    @Transactional
    public String deleteFrame(
            RedirectAttributes ra,
            @PathVariable("id") Long id,
            @RequestParam("pageNumber") int pageNumber
    ) {

        this.frameService.deleteById(id);

        ra.addFlashAttribute("pageNumber", pageNumber);

        return "redirect:/api/arrange/pagedAndSortedFrames/%d/%s".formatted(pageNumber, Defaults.SORT_FIELD);
    }


}
