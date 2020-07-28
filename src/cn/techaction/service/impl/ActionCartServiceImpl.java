//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.service.impl;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionCartService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.vo.ActionCartListVo;
import cn.techaction.vo.ActionCartVo;
import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionCartServiceImpl implements ActionCartService {
    @Autowired
    private ActionCartDao aCartDao;
    @Autowired
    private ActionProductDao aProductDao;

    public ActionCartServiceImpl() {
    }

    public SverResponse<String> saveOrUpdate(Integer userId, Integer productId, Integer count) {
        if (userId != null && productId != null && count != null) {
            ActionCart actionCart = this.aCartDao.findCartByUserAndProductId(userId, productId);
            if (actionCart == null) {
                ActionCart cart = new ActionCart();
                cart.setUserId(userId);
                cart.setProductId(productId);
                cart.setQuantity(count);
                cart.setCreated(new Date());
                cart.setUpdated(new Date());
                this.aCartDao.insertCart(cart);
            } else {
                int cartCount = actionCart.getQuantity() + count;
                actionCart.setQuantity(cartCount);
                this.aCartDao.updateCartById(actionCart);
            }

            return SverResponse.createRespBySuccessMessage("商品已成功加入购物车！");
        } else {
            return SverResponse.createByErrorMessage("参数错误！");
        }
    }

    public SverResponse<ActionCartVo> findAllCarts(Integer userId) {
        if (userId == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            List<ActionCart> list = this.aCartDao.findCartByUser(userId);
            ActionCartVo cartVo = this.createCartVo(list);
            return SverResponse.createRespBySuccess(cartVo);
        }
    }

    private ActionCartVo createCartVo(List<ActionCart> carts) {
        ActionCartVo cartVo = new ActionCartVo();
        List<ActionCartListVo> list = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        ActionCartListVo listVo;
        if (CollectionUtils.isNotEmpty(carts)) {
            for(Iterator var6 = carts.iterator(); var6.hasNext(); list.add(listVo)) {
                ActionCart cart = (ActionCart)var6.next();
                listVo = new ActionCartListVo();
                listVo.setId(cart.getId());
                listVo.setUserId(cart.getUserId());
                listVo.setProductId(cart.getProductId());
                listVo.setChecked(cart.getChecked());
                ActionProduct product = this.aProductDao.findProductById(listVo.getProductId());
                if (product != null) {
                    listVo.setName(product.getName());
                    listVo.setStatus(product.getStatus());
                    listVo.setPrice(product.getPrice());
                    listVo.setStock(product.getStock());
                    listVo.setIconUrl(product.getIconUrl());
                    listVo.setQuantity(cart.getQuantity());
                    BigDecimal totalPrice = CalcUtil.mul(listVo.getPrice().doubleValue(), listVo.getQuantity().doubleValue());
                    listVo.setTotalPrice(totalPrice);
                    if (cart.getChecked() == 1) {
                        cartTotalPrice = CalcUtil.add(cartTotalPrice.doubleValue(), listVo.getTotalPrice().doubleValue());
                    }
                }
            }
        }

        cartVo.setTotalPrice(cartTotalPrice);
        cartVo.setLists(list);
        return cartVo;
    }

    public SverResponse<ActionCartVo> deleteCart(Integer userId, Integer productId) {
        if (userId != null && productId != null) {
            int rs = this.aCartDao.deleteCart(userId, productId);
            return rs > 0 ? this.findAllCarts(userId) : SverResponse.createByErrorMessage("商品删除失败！");
        } else {
            return SverResponse.createByErrorMessage("参数错误！");
        }
    }

    public SverResponse<String> clearCart(Integer userId) {
        if (userId == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            int rs = this.aCartDao.deleteCartByUserId(userId);
            return rs > 0 ? SverResponse.createRespBySuccessMessage("成功清空购物车！") : SverResponse.createByErrorMessage("清空购物车失败！");
        }
    }

    public SverResponse<String> updateCart(Integer userId, Integer productId, Integer count, Integer checked) {
        if (userId != null && productId != null && count != null) {
            ActionCart actionCart = new ActionCart();
            actionCart.setUserId(userId);
            actionCart.setProductId(productId);
            actionCart.setQuantity(count);
            actionCart.setChecked(checked);
            this.aCartDao.updateCartByUserIdAndProductId(actionCart);
            return SverResponse.createRespBySuccess();
        } else {
            return SverResponse.createByErrorMessage("参数错误！");
        }
    }

    public SverResponse<Integer> getCartCount(Integer userId) {
        if (userId == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            int count = this.aCartDao.getCartCountByUserId(userId);
            return SverResponse.createRespBySuccess(count);
        }
    }
}
