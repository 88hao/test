//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao;

import cn.techaction.pojo.ActionAddress;
import java.util.List;

public interface ActionAddressDao {
    int insertAddress(ActionAddress var1);

    List<ActionAddress> findAddrsByUserId(Integer var1);

    int updateAddress(ActionAddress var1);

    int deleteAddress(Integer var1);

    ActionAddress findAddrsById(Integer var1);

    int findDefaultAddrByUserId(Integer var1);

    ActionAddress findDefaultAddr(Integer var1);
}
