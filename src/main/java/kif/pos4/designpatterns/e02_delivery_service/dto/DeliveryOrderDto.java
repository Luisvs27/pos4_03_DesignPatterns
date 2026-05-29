package kif.pos4.designpatterns.e02_delivery_service.dto;

import kif.pos4.designpatterns.e02_delivery_service.model.DeliveryType;

public class DeliveryOrderDto {

    private String customerName;
    private DeliveryType deliveryType;
    private String result;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public DeliveryOrderDto(String customerName, DeliveryType deliveryType, String result) {
        this.customerName = customerName;
        this.deliveryType = deliveryType;
        this.result = result;
    }
    public DeliveryOrderDto(){}

    @Override
    public String toString() {
        return "DeliveryOrderDto{" +
                "customerName='" + customerName + '\'' +
                ", deliveryType=" + deliveryType +
                ", result='" + result + '\'' +
                '}';
    }
}
