package kif.pos4.designpatterns.e02_delivery_service.service;

import jakarta.annotation.PostConstruct;
import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryOrder;
import kif.pos4.designpatterns.e02_delivery_service.repository.DeliveryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbInitService2 {

    private final DeliveryOrderRepository deliveryOrderRepository;

    @PostConstruct
    public void initDeliveryDb() {

        ObjectMapper mapper = new ObjectMapper();

        try{
            List<DeliveryOrder> deliveryOrderList = mapper.readValue(
                    getClass().getClassLoader().getResourceAsStream("orders.json"),
                    new TypeReference<>(){});

            for(DeliveryOrder deliveryOrders : deliveryOrderList){

                List<DeliveryOrder> orders = deliveryOrderRepository.findAll();
                orders.add(deliveryOrders);

                deliveryOrderRepository.saveAll(orders);
            }
            System.out.println("DB INIT 2");

        } catch (JacksonException e) {
            throw new RuntimeException(e);
        }
    }
}
