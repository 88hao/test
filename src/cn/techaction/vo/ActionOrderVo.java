//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.vo;

import java.math.BigDecimal;
import java.util.List;

public class ActionOrderVo {
    private Long orderNo;
    private BigDecimal amount;
    private Integer type;
    private String typeDesc;
    private Integer freight;
    private Integer status;
    private String statusDesc;
    private String paymentTime;
    private String deliveryTime;
    private String finishTime;
    private String closeTime;
    private String created;
    private List<ActionOrderItemVo> orderItems;
    private Integer addrId;
    private String deliveryName;
    private ActionAddressVo address;

    public ActionOrderVo() {
    }

    public Long getOrderNo() {
        return this.orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return this.typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Integer getFreight() {
        return this.freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return this.statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getPaymentTime() {
        return this.paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getDeliveryTime() {
        return this.deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getCloseTime() {
        return this.closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<ActionOrderItemVo> getOrderItems() {
        return this.orderItems;
    }

    public void setOrderItems(List<ActionOrderItemVo> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getAddrId() {
        return this.addrId;
    }

    public void setAddrId(Integer addrId) {
        this.addrId = addrId;
    }

    public String getDeliveryName() {
        return this.deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public ActionAddressVo getAddress() {
        return this.address;
    }

    public void setAddress(ActionAddressVo address) {
        this.address = address;
    }
}
