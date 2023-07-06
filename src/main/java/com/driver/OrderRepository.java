package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {

    Map<String ,Order> ordersDb = new HashMap<>();
    Map<String , List<String>> partnerOrderDb = new HashMap<>();
    Map<String , String>orderPartnerDb = new HashMap<>();
    Map<String , DeliveryPartner>partnerDb = new HashMap<>();



    public void addOrder(Order order) {
        ordersDb.put(order.getId() , order);

    }

    public void addPartner(String partnerId) {
        partnerDb.put(partnerId , new DeliveryPartner(partnerId));
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {

        if(ordersDb.containsKey(orderId) &&partnerDb.containsKey(partnerId)){
            orderPartnerDb.put(orderId ,partnerId);
            List<String>currentOrder = new ArrayList<>();
            if(partnerOrderDb.containsKey(partnerId)){
                currentOrder = partnerOrderDb.get(partnerId);
            }
            currentOrder.add(orderId);
//            System.out.println("start");
//            for (String list : currentOrder){
//                System.out.println(list);
//            }
//            System.out.println("end");
            partnerOrderDb.put(partnerId,currentOrder);
//            increase the no of order of partner
            DeliveryPartner deliveryPartner = partnerDb.get(partnerId);
            deliveryPartner.setNumberOfOrders(currentOrder.size());
        }
    }
    public  Order getOrderById(String orderId) {
        return ordersDb.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerDb.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
//        List<String> list = partnerOrderDb.get(partnerId);

//        return list.size();
        DeliveryPartner deliveryPartner = partnerDb.get(partnerId);
        return deliveryPartner.getNumberOfOrders();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return partnerOrderDb.get(partnerId);

    }

    public List<String> getAllOrders() {
        List<String> orders = new ArrayList<>();
        for (String order : ordersDb.keySet()){
            orders.add(order);
        }
        return orders;
    }

    public Integer getCountOfUnassignedOrders() {
        return ordersDb.size()-orderPartnerDb.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(int time, String partnerId) {
        int count = 0;
        List<String > orders = partnerOrderDb.get(partnerId);
        for(String orderId : orders){
            int deliveryTime = ordersDb.get(orderId).getDeliveryTime();
            if(deliveryTime > time){
                count++;
            }

        }
        return count;
    }

    public int getLastDeliveryTimeByPartnerId(String partnerId) {
        int maxTime = 0;
        List<String> list = partnerOrderDb.get(partnerId);
        for(String orders : list){
            int currTime = ordersDb.get(orders).getDeliveryTime();
            maxTime = Math.max(maxTime , currTime);
        }
        return maxTime;
    }

    public void deletePartnerById(String partnerId) {
        partnerDb.remove(partnerId);
        List<String>orders = partnerOrderDb.get(partnerId);
        partnerOrderDb.remove(partnerId);
        for(String orderId : orders){
            orderPartnerDb.remove(orderId);
        }
    }

    public void deleteOrderById(String orderId) {
        ordersDb.remove(orderId);
        String partnerId = orderPartnerDb.get(orderId);
        orderPartnerDb.remove(orderId);
        partnerOrderDb.get(partnerId).remove(orderId);
        partnerDb.get(partnerId).setNumberOfOrders(partnerOrderDb.get(partnerId).size());
    }
}