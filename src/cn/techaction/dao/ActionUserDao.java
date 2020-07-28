//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.dao;

import cn.techaction.pojo.User;
import java.util.List;

public interface ActionUserDao {
    User findUserByAccountAndPwd(String var1, String var2);

    int checkUserByAccount(String var1);

    int checkUserByEmail(String var1);

    int checkUserByPhone(String var1);

    int insertUser(User var1);

    String findUserQuestion(String var1);

    int checkUserAnswer(String var1, String var2, String var3);

    int updatePasswordByAccount(String var1, String var2);

    int checkPassword(String var1, String var2);

    int updateUserInfo(User var1);

    List<User> findAllUsers();

    int deleteUser(Integer var1);

    User findUserById(Integer var1);

    User findUserByAccount(String var1);
}
