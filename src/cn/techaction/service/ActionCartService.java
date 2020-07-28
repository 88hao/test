//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {
    SverResponse<String> saveOrUpdate(Integer var1, Integer var2, Integer var3);

    SverResponse<ActionCartVo> findAllCarts(Integer var1);

    SverResponse<ActionCartVo> deleteCart(Integer var1, Integer var2);

    SverResponse<String> updateCart(Integer var1, Integer var2, Integer var3, Integer var4);

    SverResponse<String> clearCart(Integer var1);

    SverResponse<Integer> getCartCount(Integer var1);
}
