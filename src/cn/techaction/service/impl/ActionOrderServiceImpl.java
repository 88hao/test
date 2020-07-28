//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.service.impl;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.ActionOrderItem;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.utils.DateUtils;
import cn.techaction.utils.PageBean;
import cn.techaction.utils.ConstUtil.OrderStatus;
import cn.techaction.utils.ConstUtil.PaymentType;
import cn.techaction.vo.ActionAddressVo;
import cn.techaction.vo.ActionOrderItemVo;
import cn.techaction.vo.ActionOrderVo;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionOrderServiceImpl implements ActionOrderService {
    @Autowired
    private ActionCartDao aCartDao;
    @Autowired
    private ActionProductDao aProductDao;
    @Autowired
    private ActionOrderDao aOrderDao;
    @Autowired
    private ActionOrderItemDao aOrderItemDao;
    @Autowired
    private ActionAddressDao aAddressDao;

    public ActionOrderServiceImpl() {
    }

    public SverResponse<ActionOrderVo> generateOrder(Integer uid, Integer addrId) {
        List<ActionCart> carts = this.aCartDao.findCartByUser(uid);
        SverResponse resp = this.cart2OrderItem(uid, carts);
        if (!resp.isSuccess()) {
            return resp;
        } else {
            List<ActionOrderItem> orderItems = (List)resp.getData();
            BigDecimal totalPrice = this.calcOrderTotalPrice(orderItems);
            ActionOrder order = this.saveOrder(uid, addrId, totalPrice);
            if (order == null) {
                return SverResponse.createByErrorMessage("订单生产错误，请检查后重新提交！");
            } else if (CollectionUtils.isEmpty(orderItems)) {
                return SverResponse.createByErrorMessage("订单项为空，请选择要购买的商品！");
            } else {
                Iterator<ActionOrderItem> var9 = orderItems.iterator();

                ActionOrderItem orderItem;
                while(var9.hasNext()) {
                    orderItem = (ActionOrderItem)var9.next();
                    orderItem.setOrderNo(order.getOrderNo());
                }

                this.aOrderItemDao.batchInsert(orderItems);
                var9 = orderItems.iterator();

                while(var9.hasNext()) {
                    orderItem = (ActionOrderItem)var9.next();
                    ActionProduct product = this.aProductDao.findProductById(orderItem.getGoodsId());
                    product.setStock(product.getStock() - orderItem.getQuantity());
                    product.setUpdated(new Date());
                    this.aProductDao.updateProduct(product);
                }

                this.aCartDao.deleteCartByUserId(uid);
                ActionOrderVo orderVo = this.createOrderVo(order, orderItems);
                return SverResponse.createRespBySuccess(orderVo);
            }
        }
    }

    private ActionOrder saveOrder(Integer uid, Integer addrId, BigDecimal totalPrice) {
        ActionOrder order = new ActionOrder();
        long currentTime = System.currentTimeMillis();
        Long orderNo = currentTime + (long)(new Random()).nextInt(100);
        order.setOrderNo(orderNo);
        order.setStatus(1);
        order.setFreight(0);
        order.setType(1);
        order.setAmount(totalPrice);
        order.setAddrId(addrId);
        order.setUid(uid);
        order.setUpdated(new Date());
        order.setCreated(new Date());
        int rs = this.aOrderDao.insertOrder(order);
        return rs > 0 ? order : null;
    }

    private SverResponse cart2OrderItem(Integer uid, List<ActionCart> carts) {
        List<ActionOrderItem> items = Lists.newArrayList();
        if (CollectionUtils.isEmpty(carts)) {
            return SverResponse.createByErrorMessage("购物车为空，请选择要购买的商品！");
        } else {
            Iterator<ActionCart> var5 = carts.iterator();

            while(var5.hasNext()) {
                ActionCart cart = (ActionCart)var5.next();
                ActionProduct product = this.aProductDao.findProductById(cart.getProductId());
                if (2 != product.getStatus()) {
                    return SverResponse.createByErrorMessage("商品" + product.getName() + "已经下架，不能在线购买！");
                }

                if (cart.getQuantity() > product.getStock()) {
                    return SverResponse.createByErrorMessage("商品" + product.getName() + "库存不足！");
                }

                ActionOrderItem orderItem = new ActionOrderItem();
                orderItem.setUid(uid);
                orderItem.setGoodsName(product.getName());
                orderItem.setGoodsId(product.getId());
                orderItem.setIconUrl(product.getIconUrl());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(cart.getQuantity());
                orderItem.setTotalPrice(CalcUtil.mul(orderItem.getPrice().doubleValue(), orderItem.getQuantity().doubleValue()));
                orderItem.setCreated(new Date());
                orderItem.setUpdated(new Date());
                items.add(orderItem);
            }

            return SverResponse.createRespBySuccess(items);
        }
    }

    private BigDecimal calcOrderTotalPrice(List<ActionOrderItem> orderItems) {
        BigDecimal totalPrice = new BigDecimal("0");

        ActionOrderItem item;
        for(Iterator var4 = orderItems.iterator(); var4.hasNext(); totalPrice = CalcUtil.add(totalPrice.doubleValue(), item.getTotalPrice().doubleValue())) {
            item = (ActionOrderItem)var4.next();
        }

        return totalPrice;
    }

    private ActionOrderVo createOrderVo(ActionOrder order, List<ActionOrderItem> orderItems) {
        ActionOrderVo orderVo = new ActionOrderVo();
        this.setNormalProperty(order, orderVo);
        this.setAddressProperty(order, orderVo, true);
        this.setOrderItemProperty(orderItems, orderVo);
        return orderVo;
    }

    private ActionOrderVo createOrderVo1(ActionOrder order, boolean hasAddress) {
        ActionOrderVo orderVo = new ActionOrderVo();
        this.setNormalProperty(order, orderVo);
        this.setAddressProperty(order, orderVo, hasAddress);
        List<ActionOrderItem> orderItems = this.aOrderItemDao.getItemsByOrderNo(order.getOrderNo());
        this.setOrderItemProperty(orderItems, orderVo);
        return orderVo;
    }

    private void setOrderItemProperty(List<ActionOrderItem> orderItems, ActionOrderVo orderVo) {
        List<ActionOrderItemVo> items = Lists.newArrayList();
        Iterator var5 = orderItems.iterator();

        while(var5.hasNext()) {
            ActionOrderItem orderItem = (ActionOrderItem)var5.next();
            items.add(this.createOrderItemVo(orderItem));
        }

        orderVo.setOrderItems(items);
    }

    private void setAddressProperty(ActionOrder order, ActionOrderVo orderVo, boolean flag) {
        ActionAddress address = this.aAddressDao.findAddrsById(order.getAddrId());
        if (address != null) {
            orderVo.setDeliveryName(address.getName());
            if (flag) {
                orderVo.setAddress(this.createAddressVo(address));
            } else {
                orderVo.setAddress((ActionAddressVo)null);
            }
        }

    }

    private void setNormalProperty(ActionOrder order, ActionOrderVo orderVo) {
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setAmount(order.getAmount());
        orderVo.setType(order.getType());
        orderVo.setTypeDesc(PaymentType.getTypeDesc(order.getType()));
        orderVo.setFreight(order.getFreight());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(OrderStatus.getStatusDesc(order.getStatus()));
        orderVo.setAddrId(order.getAddrId());
        orderVo.setPaymentTime(DateUtils.date2Str(order.getPaymentTime()));
        orderVo.setDeliveryTime(DateUtils.date2Str(order.getDeliveryTime()));
        orderVo.setFinishTime(DateUtils.date2Str(order.getFinishTime()));
        orderVo.setCloseTime(DateUtils.date2Str(order.getCloseTime()));
        orderVo.setCreated(DateUtils.date2Str(order.getCreated()));
    }

    private ActionAddressVo createAddressVo(ActionAddress address) {
        ActionAddressVo addressVo = new ActionAddressVo();
        addressVo.setName(address.getName());
        addressVo.setMobile(address.getMobile());
        addressVo.setPhone(address.getPhone());
        addressVo.setProvince(address.getProvince());
        addressVo.setCity(address.getCity());
        addressVo.setDistrict(address.getDistrict());
        addressVo.setAddr(address.getAddr());
        addressVo.setZip(address.getZip());
        return addressVo;
    }

    private ActionOrderItemVo createOrderItemVo(ActionOrderItem orderItem) {
        ActionOrderItemVo itemVo = new ActionOrderItemVo();
        itemVo.setOrderNo(orderItem.getOrderNo());
        itemVo.setGoodsId(orderItem.getGoodsId());
        itemVo.setGoodsName(orderItem.getGoodsName());
        itemVo.setIconUrl(orderItem.getIconUrl());
        itemVo.setCurPrice(orderItem.getPrice());
        itemVo.setTotalPrice(orderItem.getTotalPrice());
        itemVo.setQuantity(orderItem.getQuantity());
        return itemVo;
    }

    public SverResponse<PageBean<ActionOrderVo>> findOrders(Integer uid, Integer status, int pageNum, int pageSize) {
        if (uid == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            int totalRecord = this.aOrderDao.getTotalRecords(uid, status);
            PageBean<ActionOrderVo> pageBean = new PageBean<ActionOrderVo>(pageNum, pageSize, totalRecord);
            List<ActionOrder> orders = this.aOrderDao.findOrders(uid, status, pageBean.getStartIndex(), pageSize);
            List<ActionOrderVo> voList = Lists.newArrayList();
            Iterator<ActionOrder> var10 = orders.iterator();

            while(var10.hasNext()) {
                ActionOrder order = (ActionOrder)var10.next();
                voList.add(this.createOrderVo1(order, false));
            }

            pageBean.setData(voList);
            return SverResponse.createRespBySuccess(pageBean);
        }
    }

    public SverResponse<String> cancelOrder(Integer uid, Long orderNo) {
        ActionOrder order = this.aOrderDao.findOrderByUserAndOrderNo(uid, orderNo);
        if (order == null) {
            return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
        } else if (order.getStatus() == 2) {
            return SverResponse.createByErrorMessage("该订单已经付款，无法取消！");
        } else {
            ActionOrder updateOrder = new ActionOrder();
            updateOrder.setId(order.getId());
            updateOrder.setUpdated(new Date());
            int row;
            if (order.getStatus() == 1) {
                updateOrder.setStatus(6);
                row = this.aOrderDao.updateOrder(updateOrder);
                if (row > 0) {
                    return SverResponse.createRespBySuccessMessage("该订单已经取消！");
                }
            }

            if (order.getStatus() == 3) {
                updateOrder.setStatus(4);
                row = this.aOrderDao.updateOrder(updateOrder);
                if (row > 0) {
                    return SverResponse.createRespBySuccessMessage("订单已确认收货！");
                }
            }

            return SverResponse.createByErrorMessage("失败！");
        }
    }

    public SverResponse<ActionOrderVo> findOrderDetail(Integer uid, Long orderNo) {
        if (uid != null && orderNo != null) {
            ActionOrder order = this.aOrderDao.findOrderByUserAndOrderNo(uid, orderNo);
            if (order == null) {
                return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
            } else {
                ActionOrderVo orderVo = this.createOrderVo1(order, true);
                return SverResponse.createRespBySuccess(orderVo);
            }
        } else {
            return SverResponse.createByErrorMessage("参数错误！");
        }
    }

    public SverResponse<PageBean<ActionOrderVo>> mgrOrders(int pageNum, int pageSize) {
        int totalRecord = this.aOrderDao.mgrTotalRecords();
        PageBean<ActionOrderVo> pageBean = new PageBean(pageNum, pageSize, totalRecord);
        List<ActionOrder> orders = this.aOrderDao.mgrOrders(pageBean.getStartIndex(), pageSize);
        List<ActionOrderVo> voList = Lists.newArrayList();
        Iterator var8 = orders.iterator();

        while(var8.hasNext()) {
            ActionOrder order = (ActionOrder)var8.next();
            voList.add(this.createOrderVo1(order, false));
        }

        pageBean.setData(voList);
        return SverResponse.createRespBySuccess(pageBean);
    }

    public SverResponse<List<ActionOrderVo>> findOrdersForNoPages(Long orderNo) {
        List<ActionOrder> orders = this.aOrderDao.searchOrders(orderNo);
        List<ActionOrderVo> voList = Lists.newArrayList();
        Iterator var5 = orders.iterator();

        while(var5.hasNext()) {
            ActionOrder order = (ActionOrder)var5.next();
            voList.add(this.createOrderVo1(order, false));
        }

        return SverResponse.createRespBySuccess(voList);
    }

    public SverResponse<ActionOrderVo> mgrDetail(Long orderNo) {
        if (orderNo == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            ActionOrder order = this.aOrderDao.findOrderDetailByNo(orderNo);
            if (order == null) {
                return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
            } else {
                ActionOrderVo orderVo = this.createOrderVo1(order, true);
                return SverResponse.createRespBySuccess(orderVo);
            }
        }
    }

    public SverResponse<PageBean<ActionOrderVo>> searchOrderByNo(Long orderNo, int pageNum, int pageSize) {
        if (orderNo == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            ActionOrder order = this.aOrderDao.findOrderDetailByNo(orderNo);
            if (order == null) {
                return SverResponse.createByErrorMessage("该用户订单不存在，或已删除！");
            } else {
                PageBean<ActionOrderVo> pageBean = new PageBean(pageNum, pageSize, 1);
                ActionOrderVo orderVo = this.createOrderVo1(order, true);
                List<ActionOrderVo> orders = Lists.newArrayList();
                orders.add(orderVo);
                pageBean.setData(orders);
                return SverResponse.createRespBySuccess(pageBean);
            }
        }
    }

    public SverResponse<String> deliverGoods(Long orderNo) {
        if (orderNo == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            ActionOrder order = this.aOrderDao.findOrderDetailByNo(orderNo);
            if (order != null) {
                if (order.getStatus() == 2) {
                    order.setStatus(3);
                    order.setDeliveryTime(new Date());
                    order.setUpdated(new Date());
                    this.aOrderDao.updateOrder(order);
                    return SverResponse.createRespBySuccessMessage("该订单发货成功！");
                } else {
                    return SverResponse.createRespBySuccessMessage("该订单尚未付款，不能发货！");
                }
            } else {
                return SverResponse.createByErrorMessage("该订单不存在");
            }
        }
    }
}
