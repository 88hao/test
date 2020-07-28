//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.vo;

import java.math.BigDecimal;
import java.util.List;

public class ActionCartVo {
    private List<ActionCartListVo> lists;
    private BigDecimal totalPrice;

    public ActionCartVo() {
    }

    public List<ActionCartListVo> getLists() {
        return this.lists;
    }

    public void setLists(List<ActionCartListVo> lists) {
        this.lists = lists;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
