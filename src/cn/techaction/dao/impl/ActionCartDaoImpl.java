//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao.impl;

import cn.techaction.dao.ActionCartDao;
import cn.techaction.pojo.ActionCart;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.stereotype.Repository;

@Repository
public class ActionCartDaoImpl implements ActionCartDao {
    @Resource
    private QueryRunner queryRunner;
    private String alias = " id, user_id as userId,product_id as productId,quantity,created,updated,checked";

    public ActionCartDaoImpl() {
    }

    public ActionCart findCartByUserAndProductId(Integer userId, Integer productId) {
        String sql = "select " + this.alias + " from action_carts where user_id = ? and product_id = ? ";

        try {
            return (ActionCart)this.queryRunner.query(sql, new BeanHandler(ActionCart.class), new Object[]{userId, productId});
        } catch (SQLException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public int insertCart(ActionCart cart) {
        String sql = "insert into action_carts(user_id,product_id,quantity,created,updated) values(?,?,?,?,?)";
        Object[] params = new Object[]{cart.getUserId(), cart.getProductId(), cart.getQuantity(), cart.getCreated(), cart.getUpdated()};

        try {
            return this.queryRunner.update(sql, params);
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int updateCartById(ActionCart cart) {
        String sql = "update action_carts set quantity = ? where id = ?";
        Object[] params = new Object[]{cart.getQuantity(), cart.getId()};

        try {
            return this.queryRunner.update(sql, params);
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int updateCartByUserIdAndProductId(ActionCart cart) {
        String sql = "update action_carts set quantity = ?";
        List<Object> params = new ArrayList();
        params.add(cart.getQuantity());
        if (cart.getChecked() != null) {
            sql = sql + ",checked =?";
            params.add(cart.getChecked());
        }

        sql = sql + " where user_id = ? and product_id = ?";
        params.add(cart.getUserId());
        params.add(cart.getProductId());

        try {
            return this.queryRunner.update(sql, params.toArray());
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public List<ActionCart> findCartByUser(Integer userId) {
        String sql = "select " + this.alias + " from action_carts where user_id = ?  ";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionCart.class), new Object[]{userId});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public int deleteCart(Integer userId, Integer productId) {
        String sql = "delete from action_carts where product_id = ? and user_id = ?";

        try {
            return this.queryRunner.update(sql, new Object[]{productId, userId});
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int deleteCartByUserId(Integer userId) {
        String sql = "delete from action_carts where user_id = ?";

        try {
            return this.queryRunner.update(sql, userId);
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public int getCartCountByUserId(Integer userId) {
        String sql = "select count(id) as num from action_carts where user_id = ?";

        try {
            return ((Long)((List)this.queryRunner.query(sql, new ColumnListHandler("num"), new Object[]{userId})).get(0)).intValue();
        } catch (SQLException var4) {
            return 0;
        }
    }
}
