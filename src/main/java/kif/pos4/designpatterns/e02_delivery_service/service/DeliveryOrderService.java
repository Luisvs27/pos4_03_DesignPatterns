package kif.pos4.designpatterns.e02_delivery_service.service;

import kif.pos4.designpatterns.e02_delivery_service.dto.DeliveryOrderDto;
import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryOrder;
import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryType;
import kif.pos4.designpatterns.e02_delivery_service.repository.DeliveryOrderRepository;
import kif.pos4.designpatterns.e02_delivery_service.strategy.DeliveryContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryOrderService {

    private final DeliveryOrderRepository deliveryOrderRepository;

    private DeliveryType deliveryType;

    public List<DeliveryOrder> getAllOrders (DeliveryType deliveryType) {

        List<DeliveryOrder> orders = deliveryOrderRepository.findAll();

        if (orders.isEmpty()) {

            System.out.println("There are not orders in the list yet");
            // leere Array []
            return orders = new ArrayList<>();

        } else {
            System.out.println("Data orders found!");

            if (deliveryType == null) {

                log.info("this type doesn't exist!!");
            }
            else{
                System.out.println("Order with this delivery type found");
                return deliveryOrderRepository.findAllByDeliveryType(deliveryType);
            }

            return deliveryOrderRepository.saveAll(orders);
        }
    }
    // POST
    public DeliveryOrder createDeliveryOrder(DeliveryOrder deliveryOrder) {
        return deliveryOrderRepository.save(deliveryOrder);
    }

    // PUT
    public Optional<DeliveryOrder> updateDeliveryOrder(Integer id,
                                                       DeliveryType deliveryType){
        Optional<DeliveryOrder> order = deliveryOrderRepository.findById(id);

        if (order.isEmpty())
            return Optional.empty();
        else{
            DeliveryOrder orderUpdate = order.get();
            orderUpdate.setDeliveryType(deliveryType);

            System.out.format("RESOURCE UPDATED SUCCESSFULLY: Character %s was updated",
                    orderUpdate.getCustomerName());

            return Optional.of(deliveryOrderRepository.save(orderUpdate));
        }
    }

    // GET: id and Delivery type
    public Optional<DeliveryOrderDto> delivery(Integer id){

        Optional<DeliveryOrder> optionalDeliveryOrder =
                deliveryOrderRepository.findById(id);
        if (optionalDeliveryOrder.isEmpty())
            return Optional.empty();
        else {
            // Delivery Order herausholen
            DeliveryOrder deliveryOrder = optionalDeliveryOrder.get();

            // and Context auch
            DeliveryContext deliveryContext = new DeliveryContext();

            // Set the strategy
            deliveryContext.setStrategy(deliveryOrder.getDeliveryType());

            // führt durch den Context the execute aus
            String result =
            deliveryContext.executeDelivery(
                    deliveryOrder.getCustomerName(),
                    deliveryOrder.getDeliveryType()
            );
            // Das Ergebnis: Benachrichtung nach Delivery
            DeliveryOrderDto orderDtoResult = new DeliveryOrderDto(
                    deliveryOrder.getCustomerName(),
                    deliveryOrder.getDeliveryType(),
                    result
            );
            return Optional.of(orderDtoResult);
        }
    }




}
