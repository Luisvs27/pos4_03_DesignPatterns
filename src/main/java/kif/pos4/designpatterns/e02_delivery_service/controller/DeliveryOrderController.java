package kif.pos4.designpatterns.e02_delivery_service.controller;


import kif.pos4.designpatterns.e02_delivery_service.dto.DeliveryOrderDto;
import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryOrder;
import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryType;
import kif.pos4.designpatterns.e02_delivery_service.service.DeliveryOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class DeliveryOrderController {

    private final DeliveryOrderService deliveryOrderService;

    public DeliveryOrderController(DeliveryOrderService deliveryOrderService) {
        this.deliveryOrderService = deliveryOrderService;
    }

    @GetMapping
    public ResponseEntity<List<DeliveryOrder>> getAllOrders(
            @RequestParam(name = "deliveryType", required = false)
            DeliveryType deliveryType
    ) {
        return ResponseEntity.status(200)
                .body(deliveryOrderService.getAllOrders(deliveryType));
    }

    @PostMapping
    public ResponseEntity<DeliveryOrder> createDeliveryOrder(
            @RequestBody DeliveryOrder deliveryOrder
    ) {
        Optional<DeliveryOrder> postDeliveryOrder =
                Optional.ofNullable(
                        deliveryOrderService.createDeliveryOrder(deliveryOrder));

        return postDeliveryOrder.map(

                order ->
                        ResponseEntity.status(201).body(order))

                .orElseGet(() ->
                        ResponseEntity.notFound().build());

    }
    @PutMapping("/{id}/delivery-type")
    public ResponseEntity<DeliveryOrder> updateDeliveryOrder(
            @PathVariable Integer id,
            @RequestBody DeliveryOrder deliveryOrder
    ) {
        Optional<DeliveryOrder> orderUpdate =
                deliveryOrderService.updateDeliveryOrder(
                        id, deliveryOrder.getDeliveryType()
                );
        return orderUpdate.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("/{id}/delivery-info")
    public ResponseEntity<DeliveryOrderDto> delivery(
            @PathVariable Integer id
    ) {
      Optional<DeliveryOrderDto> deliveryResult =
              deliveryOrderService.delivery(id);

      if (deliveryResult.isEmpty()) {
          return ResponseEntity.notFound().build();
      } else {
          return ResponseEntity.status(200).body(deliveryResult.get());
      }
    }
}
