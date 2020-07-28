//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;
import java.util.List;

public interface ActionAddrService {
    SverResponse<List<ActionAddress>> findAddrsByUserId(Integer var1);

    SverResponse<String> addAddress(ActionAddress var1);

    SverResponse<String> delAddress(Integer var1, Integer var2);

    SverResponse<String> updateAddress(ActionAddress var1);

    SverResponse<String> updateAddrDefaultStatus(Integer var1, Integer var2);

    SverResponse<ActionAddress> getAddressById(Integer var1);
}
