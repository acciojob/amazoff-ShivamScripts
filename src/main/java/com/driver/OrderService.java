package com.driver;

import java.util.List;

public class OrderService {
    OrderRepository orderRepository=new OrderRepository();

    public String addOrder(Order order){
        return orderRepository.addOrder(order);
    }
    public String addPartner(String partnerId){
        return orderRepository.addPartner(partnerId);

    }
    public String addOrderPartnerPair(String orderId,String partnerId){
        return orderRepository.addOrderPartner(orderId,partnerId);
    }
    public Order getOrderById(String orderId){
        return orderRepository.getOrderById(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return orderRepository.getPartnerById(partnerId);

    }
    public List<String> getOrderByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrder(){
        return orderRepository.getAllOrders();
    }
    public int getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }

    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }
    public String deletePartnerById(String partnerId){
        return orderRepository.deletePartnerById(partnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }
    public String deleteOrderById(String orderId){
        return orderRepository.deleteOrderById(orderId);
    }
    public int getOrderCountByPartnerId(String partnerId){
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }
}