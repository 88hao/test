//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao.impl;

import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.pojo.ActionOrderItem;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

@Repository
public class ActionOrderItemDaoImpl implements ActionOrderItemDao {
    @Resource
    private QueryRunner queryRunner;
    private String alias = "id,uid,order_no as orderNo,goods_id as goodsId,goods_name as goodsName,icon_url as iconUrl,price,quantity,total_price as totalPrice,created,updated";

    public ActionOrderItemDaoImpl() {
    }

    public int[] batchInsert(List<ActionOrderItem> orderItems) {
        String sql = "insert into action_order_items(uid,order_no,goods_id,goods_name,icon_url,price,quantity,total_price,created,updated) values(?,?,?,?,?,?,?,?,?,?)";
        Object[][] params = new Object[orderItems.size()][];

        for(int i = 0; i < orderItems.size(); ++i) {
            ActionOrderItem item = (ActionOrderItem)orderItems.get(i);
            params[i] = new Object[]{item.getUid(), item.getOrderNo(), item.getGoodsId(), item.getGoodsName(), item.getIconUrl(), item.getPrice(), item.getQuantity(), item.getTotalPrice(), item.getCreated(), item.getUpdated()};
        }

        try {
            return this.queryRunner.batch(sql, params);
        } catch (SQLException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public List<ActionOrderItem> getItemsByOrderNo(Long orderNo) {
        String sql = "select " + this.alias + " from action_order_items where order_no = ? ";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionOrderItem.class), new Object[]{orderNo});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
