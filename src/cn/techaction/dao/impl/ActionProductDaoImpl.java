

package cn.techaction.dao.impl;

import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ActionProductDaoImpl implements ActionProductDao {
    @Resource
    private QueryRunner queryRunner;
    private String alias = "id,name,product_id as productId,parts_id as partsId,icon_url as iconUrl,sub_images as subImages,detail,spec_param as specParam,price,stock,status,is_hot as hot,created,updated ";

    public ActionProductDaoImpl() {
    }

    public ActionProduct findProductById(Integer id) {
        String sql = "select " + this.alias + " from action_products where id = ? ";

        try {
            return (ActionProduct)this.queryRunner.query(sql, new BeanHandler(ActionProduct.class), new Object[]{id});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public int insertProduct(ActionProduct product) {
        String sql = "insert into action_products(name,product_id ,parts_id ,icon_url,sub_images ,detail,spec_param,price,stock,status,is_hot,created,updated ) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{product.getName(), product.getProductId(), product.getPartsId(), product.getIconUrl(), product.getSubImages(), product.getDetail(), product.getSpecParam(), product.getPrice(), product.getStock(), product.getStatus(), product.getHot(), product.getCreated(), product.getUpdated()};

        try {
            return this.queryRunner.update(sql, params);
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int updateProduct(ActionProduct product) {
        String sql = "update action_products set updated=? ";
        List<Object> params = new ArrayList();
        params.add(product.getUpdated());
        if (!StringUtils.isEmpty(product.getName())) {
            sql = sql + ",name = ?";
            params.add(product.getName());
        }

        if (product.getProductId() != null) {
            sql = sql + ",product_id=?";
            params.add(product.getProductId());
        }

        if (product.getPartsId() != null) {
            sql = sql + ",parts_id=?";
            params.add(product.getPartsId());
        }

        if (product.getPrice() != null) {
            sql = sql + ",price = ?";
            params.add(product.getPrice());
        }

        if (product.getStock() != null) {
            sql = sql + ",stock = ?";
            params.add(product.getStock());
        }

        if (!StringUtils.isEmpty(product.getIconUrl())) {
            sql = sql + ",icon_url = ?";
            params.add(product.getIconUrl());
        }

        if (!StringUtils.isEmpty(product.getSubImages())) {
            sql = sql + ",sub_images = ?";
            params.add(product.getSubImages());
        }

        if (product.getStatus() != null) {
            sql = sql + ",status = ?";
            params.add(product.getStatus());
        }

        if (!StringUtils.isEmpty(product.getDetail())) {
            sql = sql + ",detail = ?";
            params.add(product.getDetail());
        }

        if (!StringUtils.isEmpty(product.getSpecParam())) {
            sql = sql + ",spec_param = ?";
            params.add(product.getSpecParam());
        }

        if (product.getHot() != null) {
            sql = sql + ",is_hot = ?";
            params.add(product.getHot());
        }

        sql = sql + " where id = ?";
        params.add(product.getId());

        try {
            return this.queryRunner.update(sql, params.toArray());
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int deleteProductById(Integer id) {
        String sql = "delete from action_products where id = ? ";

        try {
            return this.queryRunner.update(sql, id);
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public Integer getTotalCount(ActionProduct conditon) {
        String sql = "select count(id) as num from action_products where 1=1";
        List<Object> params = new ArrayList();
        if (conditon.getId() != null) {
            sql = sql + " and id = ? ";
            params.add(conditon.getId());
        }

        if (conditon.getName() != null) {
            sql = sql + " and name like ? ";
            params.add("%" + conditon.getName() + "%");
        }

        if (conditon.getStatus() != null) {
            sql = sql + " and status = ? ";
            params.add(conditon.getStatus());
        }

        if (conditon.getProductId() != null) {
            sql = sql + " and product_id = ? ";
            params.add(conditon.getProductId());
        }

        if (conditon.getPartsId() != null) {
            sql = sql + " and parts_id = ? ";
            params.add(conditon.getPartsId());
        }

        try {
            return ((Long)((List)this.queryRunner.query(sql, new ColumnListHandler("num"), params.toArray())).get(0)).intValue();
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public List<ActionProduct> findProducts(ActionProduct conditon, int offset, int pageSize) {
        String sql = "select " + this.alias + " from action_products where 1=1 ";
        List<Object> params = new ArrayList();
        if (conditon.getId() != null) {
            sql = sql + " and id = ? ";
            params.add(conditon.getId());
        }

        if (conditon.getName() != null) {
            sql = sql + " and name like ? ";
            params.add("%" + conditon.getName() + "%");
        }

        if (conditon.getStatus() != null) {
            sql = sql + " and status = ? ";
            params.add(conditon.getStatus());
        }

        if (conditon.getProductId() != null) {
            sql = sql + " and product_id = ? ";
            params.add(conditon.getProductId());
        }

        if (conditon.getPartsId() != null) {
            sql = sql + " and parts_id = ? ";
            params.add(conditon.getPartsId());
        }

        sql = sql + " limit ?,?";
        params.add(offset);
        params.add(pageSize);

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionProduct.class), params.toArray());
        } catch (SQLException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public List<ActionProduct> findProductsNoPage(ActionProduct conditon) {
        String sql = "select " + this.alias + " from action_products where 1=1 ";
        List<Object> params = new ArrayList();
        if (conditon.getId() != null) {
            sql = sql + " and id = ? ";
            params.add(conditon.getId());
        }

        if (conditon.getName() != null) {
            sql = sql + " and name like ? ";
            params.add(conditon.getName());
        }

        if (conditon.getStatus() != null) {
            sql = sql + " and status = ? ";
            params.add(conditon.getStatus());
        }

        if (conditon.getProductId() != null) {
            sql = sql + " and product_id = ? ";
            params.add(conditon.getProductId());
        }

        if (conditon.getPartsId() != null) {
            sql = sql + " and parts_id = ? ";
            params.add(conditon.getPartsId());
        }

        sql = sql + " order by created,id desc";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionProduct.class), params.toArray());
        } catch (SQLException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public List<ActionProduct> findHotProducts(Integer num) {
        String sql = "select " + this.alias + " from action_products where is_hot=1 and status=2";
        sql = sql + " order by updated,id desc ";
        if (num != null) {
            sql = sql + " limit 0 , ?";
        }

        try {
            return num != null ? (List)this.queryRunner.query(sql, new BeanListHandler(ActionProduct.class), new Object[]{num}) : (List)this.queryRunner.query(sql, new BeanListHandler(ActionProduct.class));
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public List<ActionProduct> findProductsByProductCategory(Integer categoryId) {
        String sql = "select " + this.alias + " from action_products where product_id= ? and status=2 order by updated desc ";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionProduct.class), new Object[]{categoryId});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public List<ActionProduct> findProductsByPartsId(Integer partsId) {
        String sql = "select " + this.alias + " from action_products where parts_id= ? order by updated desc ";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionProduct.class), new Object[]{partsId});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
