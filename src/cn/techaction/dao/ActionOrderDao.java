//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao;

import cn.techaction.pojo.ActionOrder;
import java.util.List;

public interface ActionOrderDao {
    int insertOrder(ActionOrder var1);

    int getTotalRecords(Integer var1, Integer var2);

    List<ActionOrder> findOrders(Integer var1, Integer var2, int var3, int var4);

    ActionOrder findOrderByUserAndOrderNo(Integer var1, Long var2);

    int updateOrder(ActionOrder var1);

    int mgrTotalRecords();

    List<ActionOrder> mgrOrders(int var1, int var2);

    List<ActionOrder> searchOrders(Long var1);

    ActionOrder findOrderDetailByNo(Long var1);

    List<ActionOrder> findOrderByUid(Integer var1);
}
