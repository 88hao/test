//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.vo.ActionUserVo;
import java.util.List;

public interface UserService {
    User login(String var1, String var2);

    SverResponse<String> isAdmin(User var1);

    SverResponse<User> doLogin(String var1, String var2);

    SverResponse<String> doRegister(User var1);

    SverResponse<String> checkValidation(String var1, String var2);

    SverResponse<String> findUserQuestion(String var1);

    SverResponse<String> checkUserAnswer(String var1, String var2, String var3);

    SverResponse<String> resetPassword(Integer var1, String var2);

    SverResponse<String> updatePassword(User var1, String var2, String var3);

    SverResponse<User> updateUserInfo(ActionUserVo var1);

    SverResponse<List<ActionUserVo>> findUserList();

    SverResponse<String> delUser(Integer var1);

    SverResponse<ActionUserVo> findUser(Integer var1);

    SverResponse<User> findUserByAccount(String var1);
}
