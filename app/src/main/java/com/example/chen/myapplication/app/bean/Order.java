package com.example.chen.myapplication.app.bean;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    public static final String ORDER_LIST = "ORDER_LIST";

    int id;

    User user;

    Shop shop;

    Address address;

    int status;

    String totalPrice;// 总价

    String payWay;// 支付方式

    String remark;// 备注


    String sendTime;// 送达时间

    String sendAppointment;// 预约送达时间

    String createdTime;// 创建时间
    String overTime;// 超时时间

    List<GoodsItem> foods;// 购买菜品


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }
    public String getStatusCN() {
        if(status == 0) {
            return "配送中";
        }else if(status == 1) {
            return "待支付";
        }else if(status == 2){
            return "已完成";
        } else {
            return "已取消";
        }
    }

    public String getStatusOperate() {
        if(status == 0) {
            return "确认送达";
        }else if(status == 1) {
            return "立即支付";
        }else {
            return "再来一单";
        }
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }



    public String getSendAppointment() {
        return sendAppointment;
    }

    public void setSendAppointment(String sendAppointment) {
        this.sendAppointment = sendAppointment;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public List<GoodsItem> getFoods() {
        return foods;
    }

    public void setFoods(List<GoodsItem> foods) {
        this.foods = foods;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }
}
