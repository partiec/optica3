package ru.frolov.optica3.controller.order_controllers;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.client.Client;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.factory.client.ClientFactory;
import ru.frolov.optica3.factory.order.OrderFactory;
import ru.frolov.optica3.repository.client.ClientRepository;
import ru.frolov.optica3.service.order.OrderService;

@Controller
public class Create_OrderController
        extends AbstractOrderController {

    private final ClientRepository clientRepository;

    public Create_OrderController(OrderService orderService, ClientRepository clientRepository) {
        super(orderService);
        this.clientRepository = clientRepository;
    }


    //-------------------------------------------------------------------------------------------------


    @Transactional(isolation = Isolation.SERIALIZABLE)
    @PostMapping("create")
    public String create(OrderAndClientDto filters,
                         Model model) {

        System.out.println("\n\n=====================================");
        System.out.println("\t" + getClass().getSimpleName() + ".create()...");
        System.out.println("принято dto=" + filters.toString());

        /*
         * Задача:
         *   Создать новый order, а так же клиента которому он принадлежит.
         *   Отобразить page, содержащую новый order.
         * */

        // Сначала создадим клиента
        Client newClient = ClientFactory.createClientInstance(filters);
        clientRepository.save(newClient);
        System.out.println("создан клиент: " + clientRepository.findById(newClient.getId()) + " (вынул из бд)");

        // создать новый заказ
        _Order newOrder = OrderFactory.createOrderInstance(filters);
        orderService.save(newOrder);
        System.out.println("создан заказ: " +
                orderService.findById(newOrder.getId()) + " (вынул из бд)" +
                " у него accessoryUnits().size()=" + orderService.findById(newOrder.getId()).get().getAccessoryUnits().size());

        // связать
        newClient.getOrderList().add(newOrder);
        newOrder.setClient(newClient);


        // Подготавливаем данные для модели
        // -------------------------------->
        // page должна быть no spec
        orderService.getOrderCache().setSpec(null);
        // page создаем с помощью локального метода getXPageNoSpec()!, поскольку она должна содержать xContainer
        Page<_Order> actualPage = orderService.getXPage(newOrder);
        // xOrderId нужен будет в html для галочки (которая помечает созданный контейнер)

        System.out.println("=============");
        System.out.println("Переберем page и посмотрим сколько в заказе accessoryUnits:");
        for (_Order o : actualPage.getContent()){
            System.out.println("o.getAccessoryUnits().size()=" + o.getAccessoryUnits().size());
        }
        System.out.println("=============");

        // filters уже не нужны, кэшируем костыль
        orderService.getOrderCache().setDto(new OrderAndClientDto());
        //----------------------------/

        // В модель
        // -------->
        orderService.transferToModel(
                model,
                actualPage,
                null,
                newOrder.getId(),
                null,
                null,
                null);

        // Контрольное кеширование
        orderService.getOrderCache().cacheAttributesIfNotNull(
                actualPage,
                null,
                null,
                null,
                null,
                null
        );

        return "displayOrders";

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleErrors(MethodArgumentNotValidException notValidException,
                               Model model,
                               BindingResult br) {

        // в модель:
        // errors для отображения messages
        model.addAttribute("errors", br.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        // page должна остаться как была
        model.addAttribute("page", orderService.getOrderCache().getPage());
        // filters должны остаться те же
        model.addAttribute("filters", orderService.getOrderCache().getDto());

        // cache не требуется

        return "displayOrders";
    }
}
