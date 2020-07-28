//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao.impl;

import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;
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
public class ActionAddressDaoImpl implements ActionAddressDao {
    @Resource
    private QueryRunner queryRunner;

    public ActionAddressDaoImpl() {
    }

    public int insertAddress(ActionAddress address) {
        String sql = "insert into action_address(user_id,name,phone,mobile,province,city,district,addr,zip,default_addr,created,updated) values(?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] param = new Object[]{address.getUid(), address.getName(), address.getPhone(), address.getMobile(), address.getProvince(), address.getCity(), address.getDistrict(), address.getAddr(), address.getZip(), address.getDefault_addr(), address.getCreated(), address.getUpdated()};

        try {
            return this.queryRunner.update(sql, param);
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public List<ActionAddress> findAddrsByUserId(Integer userId) {
        String sql = "select id ,user_id as uid,name,phone,mobile,province,city,district,addr,zip,default_addr,del_state,created,updated from action_address where user_id=? and del_state=0 order by default_addr desc,updated desc ";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionAddress.class), new Object[]{userId});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public ActionAddress findAddrsById(Integer addrId) {
        String sql = "select id ,user_id as uid,name,phone,mobile,province,city,district,addr,zip,default_addr,del_state,created,updated from action_address where id=? and del_state=0 ";

        try {
            return (ActionAddress)this.queryRunner.query(sql, new BeanHandler(ActionAddress.class), new Object[]{addrId});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public int updateAddress(ActionAddress address) {
        String sql = "update action_address set updated = ?";
        List<Object> params = new ArrayList();
        params.add(address.getUpdated());
        if (!StringUtils.isEmpty(address.getName())) {
            sql = sql + ",name = ?";
            params.add(address.getName());
        }

        if (!StringUtils.isEmpty(address.getPhone())) {
            sql = sql + ",phone = ?";
            params.add(address.getPhone());
        }

        if (!StringUtils.isEmpty(address.getMobile())) {
            sql = sql + ",mobile = ?";
            params.add(address.getMobile());
        }

        if (!StringUtils.isEmpty(address.getProvince())) {
            sql = sql + ",province = ?";
            params.add(address.getProvince());
        }

        if (!StringUtils.isEmpty(address.getCity())) {
            sql = sql + ",city = ?";
            params.add(address.getCity());
        }

        if (!StringUtils.isEmpty(address.getDistrict())) {
            sql = sql + ",district = ?";
            params.add(address.getDistrict());
        }

        if (!StringUtils.isEmpty(address.getAddr())) {
            sql = sql + ",addr = ?";
            params.add(address.getAddr());
        }

        if (!StringUtils.isEmpty(address.getZip())) {
            sql = sql + ",zip = ?";
            params.add(address.getZip());
        }

        if (address.getDefault_addr() != null) {
            sql = sql + ",default_addr = ?";
            params.add(address.getDefault_addr());
        }

        if (address.getDel_state() != null) {
            sql = sql + ",del_state = ?";
            params.add(address.getDel_state());
        }

        sql = sql + " where id = ?";
        params.add(address.getId());

        try {
            return this.queryRunner.update(sql, params.toArray());
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int deleteAddress(Integer id) {
        String sql = "delete from action_address where id = ?";

        try {
            return this.queryRunner.update(sql, id);
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public int findDefaultAddrByUserId(Integer userId) {
        String sql = "select count(id) as num from action_address where user_id=? and default_addr=1";

        try {
            return ((Long)((List)this.queryRunner.query(sql, new ColumnListHandler("num"), new Object[]{userId})).get(0)).intValue();
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public ActionAddress findDefaultAddr(Integer userId) {
        String sql = "select id ,user_id as uid,name,phone,mobile,province,city,district,addr,zip,default_addr,del_state,created,updated from action_address  where user_id=? and default_addr=1 and del_state=0 ";

        try {
            return (ActionAddress)this.queryRunner.query(sql, new BeanHandler(ActionAddress.class), new Object[]{userId});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
