package kif.pos4.designpatterns.e02_delivery_service.repository;

import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryOrder;
import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryOrderRepository extends JpaRepository<DeliveryOrder, Integer> {


    boolean existsByDeliveryType(DeliveryType deliveryType);

    List<DeliveryOrder> findAllByDeliveryType(DeliveryType deliveryType);
}
