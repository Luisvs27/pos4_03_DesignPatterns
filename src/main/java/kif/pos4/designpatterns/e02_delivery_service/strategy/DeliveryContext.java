package kif.pos4.designpatterns.e02_delivery_service.strategy;

import kif.pos4.designpatterns.e01_battle_arena.strategy.IAttackStrategy;
import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DeliveryContext {

    private IDeliveryStrategy strategy;


    public void setStrategy(DeliveryType deliveryType){
        switch (deliveryType){
            case CAR -> this.strategy = new CarDeliveryStrategy();
            case BIKE -> this.strategy = new BikeDeliveryStrategy();
            case PICKUP -> this.strategy = new PickupDeliveryStrategy();
            case EXPRESS -> this.strategy = new ExpressDeliveryStrategy();
            default -> throw new IllegalArgumentException("Unknown Delivery");
        }
    }

    public String executeDelivery(String customerName ,
                                  DeliveryType deliveryType){
        if (strategy == null) {
            return customerName + " has not defined the delivery";
        }else {
            return strategy.delivery(customerName, deliveryType);
        }
    }


}
