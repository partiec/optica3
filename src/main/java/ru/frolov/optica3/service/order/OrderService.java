package ru.frolov.optica3.service.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.frolov.optica3.cache._order.OrderCache;
import ru.frolov.optica3.defaults.Defaults;
import ru.frolov.optica3.dto._order.OrderAndClientDto;
import ru.frolov.optica3.entity.order._Order;
import ru.frolov.optica3.entity.products.accessory.AccessoryUnit;
import ru.frolov.optica3.enums.order_enums.OrderPayment;
import ru.frolov.optica3.enums.order_enums.OrderStage;
import ru.frolov.optica3.repository.client.ClientRepository;
import ru.frolov.optica3.repository.order.OrderRepository;
import ru.frolov.optica3.specification.order_spec.OrderSpec;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Setter
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OrderCache cache;
    private final OrderSpec spec;
    //---------------------------------------------------------

    public void transferToModel(
            Model model,
            Page<_Order> actualPage,
            Integer pageNumberOnlyForFlip,
            Long xOrderId,
            AccessoryUnit unit,
            String whichFieldOnInputOnlyForSearch,
            String copyToSearch) {

        System.out.println("==============================================================================");
        System.out.println(getClass().getSimpleName() + ".transferToModel()...");

        /*
         * Метод передает данные в модель. Некоторые данные добывает, прежде чем передать.
         * */

        // transfer
        // --------
        // actualPage
        model.addAttribute("page", actualPage);

        // filters
        model.addAttribute("filters", getCache().getDto());                                                         //  !

        // db orders
        System.out.println("...поиск всех ордеров в бд...");
        List<_Order> dbOrders = orderRepository.findAll();
        model.addAttribute("dbOrders", dbOrders.size());

        // foundOrders
        if (getCache().getSpec() == null) {
            model.addAttribute("foundOrders", dbOrders.size());
        } else {
            System.out.println("... поиск ордеров по спец ...");
            List<_Order> foundOrders = orderRepository.findAll(getCache().getSpec());
            model.addAttribute("foundOrders", foundOrders.size());
        }


        // xOrderId
        if (xOrderId != null) {
            System.out.println("в модель передается xOrderId=" + xOrderId);
            model.addAttribute("xOrderId", xOrderId);
        }

        // whichFieldOnInputOnlyForSearch
        if (whichFieldOnInputOnlyForSearch != null) {
            model.addAttribute("whichFieldOnInput", whichFieldOnInputOnlyForSearch);
        }

        if (getCache().getSpec() == null) {
            model.addAttribute("message_itsFullList", "message_itsFullList");
        }

        if (getCache().getOrderRefreshed() != null && getCache().getOrderRefreshed()) {
            model.addAttribute("orderRefreshed", "orderRefreshed");
        }

        if (unit != null) {
            model.addAttribute("unit", unit);
        }

        if (getCache().getMode()) {
            model.addAttribute("editMode", "editMode");
        }

        if (copyToSearch != null) {
            model.addAttribute("copyToSearch", "copyToSearch");
        }


        ////////////////////////////
        model.addAttribute("payments", OrderPayment.values());

        model.addAttribute("stages", OrderStage.values());
        ////////////////////////////


        System.out.println("___конец метода transferToModel()");
    }


    public List<_Order> getListNOSpec() {
        return orderRepository.findAll();
    }


    public Page<_Order> getPageNOSpec(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return orderRepository.findAll(pageable);
    }

    public List<_Order> getListBYSpec(Specification<_Order> specification) {
        return orderRepository.findAll(specification);
    }

    public Page<_Order> getPageBYSpec(Integer pageNumber, Specification<_Order> specification) {
        Pageable pageable = PageRequest.of(pageNumber, Defaults.PAGE_SIZE);
        return orderRepository.findAll(specification, pageable);
    }


    public Page<_Order> getXPage(_Order xOrder) {
        /*
         * Метод ищет page, содержащую нужный контейнер. (На xContainer будет установлена 'галочка').
         * */

        Page<_Order> xPage = null;

        if (xOrder != null) {

            // переменная для выхода из цикла
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
                        Sort.by(Sort.Direction.DESC, "createdAt"));
                // страницу i
                xPage = orderRepository.findAll(pageable);

                // перебирать content каждой страницы пока не найдем страницу, содержащую xContainer
                for (_Order order : xPage.getContent()) {
                    // если нашелся xContainer, то найдена нужная страница
                    if (order.equals(xOrder)) {
                        catchXPage = true;
                        break;
                    }
                }
            }
        }

        return xPage;
    }


    public void save(_Order containerForNew) {
        orderRepository.save(containerForNew);
    }


    public Optional<_Order> findById(long id) {
        return orderRepository.findById(id);
    }


    public void delete(_Order xContainer) {
        orderRepository.delete(xContainer);
    }


    public void deleteById(Long xId) {
        orderRepository.deleteById(xId);
    }


    public _Order findByDto(OrderAndClientDto filters) {
        return orderRepository.findAll(spec.allLike(filters)).stream()
                .findFirst()
                .orElse(null);
    }

    public _Order findCurrent() {
        return orderRepository.findByCurrent(true);
    }

    public void transferOrder(
            Model model,
            _Order xOrder) {

        model.addAttribute("order", xOrder);
    }
}
