package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class OrderRepository {
    HashMap<String,Order> orderdb = new HashMap<>();
    HashMap<String,DeliveryPartner> partnerDB = new HashMap<>();
    HashMap<String,List<String>> pairDB = new HashMap<>();
    HashMap<String,String> assignDB=new HashMap<>();

    public String addOrder(Order order){
        orderdb.put(order.getId(),order);
        return "Added";
    }
    public String addPartner(String partnerId){
        DeliveryPartner partner=new DeliveryPartner(partnerId);
        partnerDB.put(partner.getId(),partner);
        return "Added";
    }
    public String addOrderPartner(String orderId,String partnerId){
        List<String> list = pairDB.getOrDefault(partnerId,new ArrayList<>());
        list.add(orderId);
        pairDB.put(partnerId,list);
        assignDB.put(orderId,partnerId);
        DeliveryPartner partner = partnerDB.get(partnerId);
        partner.setNumberOfOrders(list.size());
        return "Added";
    }
    public Order getOrderById(String id){
        for(String s : orderdb.keySet()){
            if(s.equals(id))return orderdb.get(s);
        }
        return null;
    }
    public DeliveryPartner getPartnerById(String partnerId){
        if(partnerDB.containsKey(partnerId))return partnerDB.get(partnerId);
        return null;
    }
    public int getOrderCountByPartnerId(String partnerId){
        int order = pairDB.getOrDefault(partnerId,new ArrayList<>()).size();
        return order;
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return pairDB.getOrDefault(partnerId,new ArrayList<>());
    }
    public List<String> getAllOrders(){
        List<String> orders = new ArrayList<>();
        for(String s : orderdb.keySet()){
            orders.add(s);
        }
        return orders;
    }
    public int getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        int orderCount=0;
        List<String> list = pairDB.get(partnerId);
        int deliveryTime = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3));
        for(String s:list){
            Order order = orderdb.get(s);
            if(order.getDeliveryTime()>deliveryTime)orderCount++;
        }
        return orderCount;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time="";
        List<String> list =pairDB.get(partnerId);
        int deliveryTime=0;
        for(String s:list){
            Order order =orderdb.get(s);
            deliveryTime=Math.max(deliveryTime,order.getDeliveryTime());
        }
        int hour=deliveryTime/60;
        String sHour="";
        if(hour<10)sHour="0"+String.valueOf(hour);
        else sHour=String.valueOf(hour);

        int min=deliveryTime%60;
        String sMin = "";
        if(min<10)sMin = "0" + String.valueOf(min);
        else sMin = String.valueOf(min);

        time=sHour+":"+sMin;
        return time;
    }

    public String deletePartnerById(String partnerId){
        partnerDB.remove(partnerId);
        List<String>list = pairDB.getOrDefault(partnerId,new ArrayList<>());
        ListIterator<String> itr = list.listIterator();
        while(itr.hasNext()){
            assignDB.remove(itr.next());
        }
        pairDB.remove(partnerId);
        return "Deleted";
    }

    public String deleteOrderById(String orderId){
        orderdb.remove(orderId);
        String partnerId =assignDB.get(orderId);
        assignDB.remove(orderId);
        List<String> list =pairDB.get(partnerId);
        ListIterator<String> itr =list.listIterator();
        while(itr.hasNext()){
            String s= itr.next();
            if(s.equals(orderId))itr.remove();
        }
        pairDB.put(partnerId,list);
        return "Deleted";

    }

    public int getCountOfUnassignedOrders(){
        return orderdb.size()-assignDB.size();
    }

}