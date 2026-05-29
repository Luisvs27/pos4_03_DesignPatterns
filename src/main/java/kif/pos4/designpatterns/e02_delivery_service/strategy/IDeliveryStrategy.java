package kif.pos4.designpatterns.e02_delivery_service.strategy;

import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryType;

public interface IDeliveryStrategy {

    String delivery(String customerName, DeliveryType deliveryType);

}
