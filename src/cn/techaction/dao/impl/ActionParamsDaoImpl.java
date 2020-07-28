

package cn.techaction.dao.impl;

import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

@Repository
public class ActionParamsDaoImpl implements ActionParamsDao {
    @Resource
    private QueryRunner queryRunner;

    public ActionParamsDaoImpl() {
    }

    public int insertParam(ActionParam param) {
        String sql = "insert into action_params(parent_id,name,sort_order,status,created,updated,level) values(?,?,?,?,?,?,?)";
        Object[] params = new Object[]{param.getParent_id(), param.getName(), param.getSort_order(), param.getStatus(), param.getCreated(), param.getUpdated(), param.getLevel()};

        try {
            return this.queryRunner.update(sql, params);
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int updateParam(ActionParam pra) {
        String sql = "update action_params set name = ? ,sort_order = ?,level =?,status =?,updated = ? where id = ?";
        Object[] params = new Object[]{pra.getName(), pra.getSort_order(), pra.getLevel(), pra.getStatus(), pra.getUpdated(), pra.getId()};

        try {
            return this.queryRunner.update(sql, params);
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public ActionParam findParamById(Integer id) {
        String sql = "select id, parent_id,name,sort_order,status,created,updated,level from  action_params where id = ?";

        try {
            return (ActionParam)this.queryRunner.query(sql, new BeanHandler(ActionParam.class), new Object[]{id});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public List<ActionParam> findParamsByParentId(Integer parentId) {
        String sql = "select id, parent_id,name,sort_order,status,created,updated,level from  action_params where parent_id = ? order by sort_order";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(ActionParam.class), new Object[]{parentId});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public int deleteParam(Integer id) {
        String sql = "delete from action_params where id = ?";

        try {
            return this.queryRunner.update(sql, id);
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public ActionParam findParamByParentIdandName(Integer parentId, String name) {
        String sql = "select * from action_params where parent_id = ? and name = ?";

        try {
            return (ActionParam)this.queryRunner.query(sql, new BeanHandler(ActionParam.class), new Object[]{parentId, name});
        } catch (SQLException var5) {
            var5.printStackTrace();
            return null;
        }
    }
}
