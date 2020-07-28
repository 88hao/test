//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao;

import cn.techaction.pojo.ActionOrderItem;
import java.util.List;

public interface ActionOrderItemDao {
    int[] batchInsert(List<ActionOrderItem> var1);

    List<ActionOrderItem> getItemsByOrderNo(Long var1);
}
