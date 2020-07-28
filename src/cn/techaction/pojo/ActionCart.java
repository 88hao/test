//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.pojo;

import java.util.Date;

public class ActionCart {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Date created;
    private Date updated;
    private Integer checked;

    public ActionCart() {
    }

    public ActionCart(Integer id, Integer userId, Integer productId, Integer quantity, Date created, Date updated, Integer checked) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.created = created;
        this.updated = updated;
        this.checked = checked;
    }

    public Integer getChecked() {
        return this.checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return this.productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
