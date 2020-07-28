//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao.impl;

import cn.techaction.dao.ActionOrderDao;
import cn.techaction.pojo.ActionOrder;
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
public class ActionOrderDaoImpl implements ActionOrderDao {
    @Resource
    private QueryRunner queryRunner;
    private String alias = "id,order_no as orderNo,uid,addr_id  as addrId,amount,type,freight,status,payment_time as paymentTime,delivery_time as deliveryTime,finish_time as finishTime,close_time as closeTime,updated,created";

    public ActionOrderDaoImpl() {
    }

    public int insertOrder(ActionOrder order) {
        String sql = "insert into action_orders(order_no,uid,addr_id,amount,type,freight,status,payment_time,delivery_time,finish_time,close_time,updated,created) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] param = new Object[]{order.getOrderNo(), order.getUid(), order.getAddrId(), order.getAmount(), order.getType(), order.getFreight(), order.getStatus(), order.getPaymentTime(), order.getDeliveryTime(), order.getFinishTime(), order.getCloseTime(), order.getUpdated(), order.getCreated()};

        try {
            return this.queryRunner.update(sql, param);
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int getTotalRecords(Integer uid, Integer status) {
        String sql = "select count(id) as num from action_orders where uid = ?";
        List<Object> params = new ArrayList();
        params.add(uid);
        if (status != 0) {
            sql = sql + " and status = ?";
            params.add(status);
        }

        try {
            return ((Long)((List)this.queryRunner.query(sql, new ColumnListHandler("num"), params.toArray())).get(0)).intValue();
        } catch (SQLException var6) {
            var6.printStackTrace();
            return 0;
        }
    }

    public List<ActionOrder> findOrders(Integer uid, Integer status, int offset, int pageSize) {
        String sql = "select " + this.alias + " from action_orders where uid = ? ";
        List<Object> params = new ArrayList();
        params.add(uid);
        if (status != 0) {
            sql = sql + " and status = ?";
            params.add(status);
        }

        sql = sql + " limit ?,?";
        params.add(offset);
        params.add(pageSize);

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionOrder.class), params.toArray());
        } catch (SQLException var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public ActionOrder findOrderByUserAndOrderNo(Integer uid, Long orderNo) {
        String sql = "select " + this.alias + " from action_orders where uid = ? and order_no = ?";

        try {
            return (ActionOrder)this.queryRunner.query(sql, new BeanHandler(ActionOrder.class), new Object[]{uid, orderNo});
        } catch (SQLException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public int updateOrder(ActionOrder order) {
        String sql = "update action_orders set updated = ? ";
        List<Object> params = new ArrayList();
        params.add(order.getUpdated());
        if (order.getStatus() != null) {
            sql = sql + ",status = ?";
            params.add(order.getStatus());
        }

        if (order.getPaymentTime() != null) {
            sql = sql + ",payment_time = ?";
            params.add(order.getPaymentTime());
        }

        if (order.getDeliveryTime() != null) {
            sql = sql + ",delivery_time = ?";
            params.add(order.getDeliveryTime());
        }

        if (order.getFinishTime() != null) {
            sql = sql + ",finsih_time = ?";
            params.add(order.getFinishTime());
        }

        if (order.getCloseTime() != null) {
            sql = sql + ",close_time = ?";
            params.add(order.getCloseTime());
        }

        sql = sql + " where id = ?";
        params.add(order.getId());

        try {
            return this.queryRunner.update(sql, params.toArray());
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int mgrTotalRecords() {
        String sql = "select count(id) as num from action_orders ";

        try {
            return ((Long)((List)this.queryRunner.query(sql, new ColumnListHandler("num"))).get(0)).intValue();
        } catch (SQLException var3) {
            var3.printStackTrace();
            return 0;
        }
    }

    public List<ActionOrder> mgrOrders(int offset, int pageSize) {
        String sql = "select " + this.alias + " from action_orders  limit ?,?";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionOrder.class), new Object[]{offset, pageSize});
        } catch (SQLException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public ActionOrder findOrderDetailByNo(Long orderNo) {
        String sql = "select " + this.alias + " from action_orders where  order_no = ?";

        try {
            return (ActionOrder)this.queryRunner.query(sql, new BeanHandler(ActionOrder.class), new Object[]{orderNo});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public List<ActionOrder> searchOrders(Long orderNo) {
        String sql = "select " + this.alias + " from action_orders where  1=1 ";

        try {
            if (orderNo != null) {
                sql = sql + " and order_no = ?";
                return (List)this.queryRunner.query(sql, new BeanListHandler(ActionOrder.class), new Object[]{orderNo});
            } else {
                return (List)this.queryRunner.query(sql, new BeanListHandler(ActionOrder.class));
            }
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public List<ActionOrder> findOrderByUid(Integer uid) {
        String sql = "select * from action_orders where id = ?";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionOrder.class), new Object[]{uid});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
