package kif.pos4.designpatterns.e02_delivery_service.strategy;

import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryType;

public class CarDeliveryStrategy implements IDeliveryStrategy{

    @Override
    public String delivery(String customerName, DeliveryType deliveryType) {
        return customerName + " will be delivered by " + deliveryType;
    }
}
