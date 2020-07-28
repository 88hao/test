//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao.impl;

import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;
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
public class ActionUserDaoImpl implements ActionUserDao {
    @Resource
    private QueryRunner queryRunner;

    public ActionUserDaoImpl() {
    }

    public User findUserByAccountAndPwd(String account, String password) {
        String sql = "select * from action_users where account = ? and password=?";

        try {
            return (User)this.queryRunner.query(sql, new BeanHandler(User.class), new Object[]{account, password});
        } catch (SQLException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public int checkUserByAccount(String account) {
        String sql = "select count(account) as num from action_users where account = ?";

        try {
            List<Long> rs = (List)this.queryRunner.query(sql, new ColumnListHandler("num"), new Object[]{account});
            return rs.size() > 0 ? ((Long)rs.get(0)).intValue() : 0;
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public int checkUserByEmail(String email) {
        String sql = "select count(account) as num from action_users where email = ?";

        try {
            List<Long> rs = (List)this.queryRunner.query(sql, new ColumnListHandler("num"), new Object[]{email});
            return rs.size() > 0 ? ((Long)rs.get(0)).intValue() : 0;
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public int checkUserByPhone(String phone) {
        String sql = "select count(account) as num from action_users where phone = ?";

        try {
            List<Long> rs = (List)this.queryRunner.query(sql, new ColumnListHandler("num"), new Object[]{phone});
            return rs.size() > 0 ? ((Long)rs.get(0)).intValue() : 0;
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public int insertUser(User user) {
        String sql = "insert into action_users(account,password,email,phone,question,asw,role,create_time,update_time) values(?,?,?,?,?,?,?,?,?)";
        Object[] params = new Object[]{user.getAccount(), user.getPassword(), user.getEmail(), user.getPhone(), user.getQuestion(), user.getAsw(), user.getRole(), user.getCreate_time(), user.getUpdate_time()};

        try {
            return this.queryRunner.update(sql, params);
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public String findUserQuestion(String account) {
        String sql = "select question from action_users where account = ?";

        try {
            List<String> questions = (List)this.queryRunner.query(sql, new ColumnListHandler("question"), new Object[]{account});
            return (String)questions.get(0);
        } catch (SQLException var4) {
            return null;
        }
    }

    public int checkUserAnswer(String account, String question, String asw) {
        String sql = "select count(account) as num from action_users where account = ? and question = ? and asw = ?";

        try {
            List<Long> rs = (List)this.queryRunner.query(sql, new ColumnListHandler("num"), new Object[]{account, question, asw});
            return rs.size() > 0 ? ((Long)rs.get(0)).intValue() : 0;
        } catch (SQLException var6) {
            var6.printStackTrace();
            return 0;
        }
    }

    public int updatePasswordByAccount(String account, String password) {
        String sql = "update action_users set password=? where account = ?";

        try {
            return this.queryRunner.update(sql, new Object[]{password, account});
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public int checkPassword(String account, String password) {
        String sql = "select count(account) as num from action_users where account = ? and password=?";

        try {
            List<Long> rs = (List)this.queryRunner.query(sql, new ColumnListHandler("num"), new Object[]{account, password});
            return rs.size() > 0 ? ((Long)rs.get(0)).intValue() : 0;
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public List<User> findAllUsers() {
        String sql = "select * from action_users where del = 0";

        try {
            return (List)this.queryRunner.query(sql, new BeanListHandler(User.class));
        } catch (SQLException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public int deleteUser(Integer id) {
        String sql = "delete from action_users where id = ?";

        try {
            return this.queryRunner.update(sql, id);
        } catch (SQLException var4) {
            var4.printStackTrace();
            return 0;
        }
    }

    public User findUserById(Integer id) {
        String sql = "select * from action_users where id = ?";

        try {
            return (User)this.queryRunner.query(sql, new BeanHandler(User.class), new Object[]{id});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public int updateUserInfo(User user) {
        String sql = " update action_users set update_time = ?,password = ? ,email = ?,phone = ?,question = ?,asw = ?,name = ?,age = ?,sex = ?,create_time=?,account=?,role=?,del=? where id = ?";
        List<Object> params = new ArrayList();
        params.add(user.getUpdate_time());
        params.add(user.getPassword());
        params.add(user.getEmail());
        params.add(user.getPhone());
        params.add(user.getQuestion());
        params.add(user.getAsw());
        params.add(user.getName());
        params.add(user.getAge());
        params.add(user.getSex());
        params.add(user.getCreate_time());
        params.add(user.getAccount());
        params.add(user.getRole());
        params.add(user.getDel());
        params.add(user.getId());

        try {
            return this.queryRunner.update(sql, params.toArray());
        } catch (SQLException var5) {
            var5.printStackTrace();
            return 0;
        }
    }

    public User findUserByAccount(String account) {
        String sql = "select * from action_users where account = ?";

        try {
            return (User)this.queryRunner.query(sql, new BeanHandler(User.class), new Object[]{account});
        } catch (SQLException var4) {
            var4.printStackTrace();
            return null;
        }
    }
}
