//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao;

import cn.techaction.pojo.ActionCart;
import java.util.List;

public interface ActionCartDao {
    ActionCart findCartByUserAndProductId(Integer var1, Integer var2);

    int insertCart(ActionCart var1);

    int updateCartById(ActionCart var1);

    int updateCartByUserIdAndProductId(ActionCart var1);

    int deleteCart(Integer var1, Integer var2);

    List<ActionCart> findCartByUser(Integer var1);

    int deleteCartByUserId(Integer var1);

    int getCartCountByUserId(Integer var1);
}
