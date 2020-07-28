//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;
import java.util.List;

public interface ActionOrderService {
    SverResponse<ActionOrderVo> generateOrder(Integer var1, Integer var2);

    SverResponse<PageBean<ActionOrderVo>> findOrders(Integer var1, Integer var2, int var3, int var4);

    SverResponse<String> cancelOrder(Integer var1, Long var2);

    SverResponse<ActionOrderVo> findOrderDetail(Integer var1, Long var2);

    SverResponse<PageBean<ActionOrderVo>> mgrOrders(int var1, int var2);

    SverResponse<List<ActionOrderVo>> findOrdersForNoPages(Long var1);

    SverResponse<ActionOrderVo> mgrDetail(Long var1);

    SverResponse<PageBean<ActionOrderVo>> searchOrderByNo(Long var1, int var2, int var3);

    SverResponse<String> deliverGoods(Long var1);
}
