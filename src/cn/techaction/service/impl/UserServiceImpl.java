//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.service.impl;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionUserDao;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.utils.MD5Util;
import cn.techaction.utils.TokenCache;
import cn.techaction.vo.ActionUserVo;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ActionUserDao userDao;
    @Autowired
    private ActionOrderDao orderDao;

    public UserServiceImpl() {
    }

    public User login(String account, String password) {
        return this.userDao.findUserByAccountAndPwd(account, password);
    }

    public SverResponse<User> doLogin(String account, String password) {
        int rs = this.userDao.checkUserByAccount(account);
        if (rs == 0) {
            return SverResponse.createByErrorMessage("用户不存在！");
        } else {
            String md5Pwd = MD5Util.MD5Encode(password, "utf-8", false);
            User user = this.userDao.findUserByAccountAndPwd(account, md5Pwd);
            if (user == null) {
                return SverResponse.createByErrorMessage("密码错误！");
            } else {
                user.setPassword("");
                return SverResponse.createRespBySuccess("登陆成功", user);
            }
        }
    }

    public SverResponse<String> isAdmin(User user) {
        return user.getRole() == 2 ? SverResponse.createRespBySuccess() : SverResponse.createRespByError();
    }

    public SverResponse<List<ActionUserVo>> findUserList() {
        List<User> findAllUsers = this.userDao.findAllUsers();
        List<ActionUserVo> voList = Lists.newArrayList();
        Iterator var4 = findAllUsers.iterator();

        while(var4.hasNext()) {
            User allusers = (User)var4.next();
            voList.add(this.setNormalProperty(allusers));
        }

        return SverResponse.createRespBySuccess(voList);
    }

    private ActionUserVo setNormalProperty(User user) {
        ActionUserVo uservo = new ActionUserVo();
        uservo.setId(user.getId());
        uservo.setName(user.getName());
        uservo.setAccount(user.getAccount());
        uservo.setAge(user.getAge());
        uservo.setEmail(user.getEmail());
        uservo.setPhone(user.getPhone());
        if (user.getSex() == 1) {
            uservo.setSex("男");
        } else {
            uservo.setSex("女");
        }

        return uservo;
    }

    public SverResponse<ActionUserVo> findUser(Integer id) {
        User user = this.userDao.findUserById(id);
        if (user == null) {
            return SverResponse.createByErrorMessage("获取用户数据失败");
        } else {
            ActionUserVo vo = this.setNormalProperty(user);
            return SverResponse.createRespBySuccess(vo);
        }
    }

    public SverResponse<User> updateUserInfo(ActionUserVo userVo) {
        User updateUser = this.userDao.findUserById(userVo.getId());
        updateUser.setAccount(userVo.getAccount());
        updateUser.setEmail(userVo.getEmail());
        updateUser.setPhone(userVo.getPhone());
        updateUser.setUpdate_time(new Date());
        updateUser.setAge(userVo.getAge());
        if (userVo.getSex().equals("男")) {
            updateUser.setSex(1);
        } else {
            updateUser.setSex(0);
        }

        updateUser.setName(userVo.getName());
        int rs = this.userDao.updateUserInfo(updateUser);
        return rs > 0 ? SverResponse.createRespBySuccess("用户信息修改成功！", updateUser) : SverResponse.createByErrorMessage("用户信息修改失败！");
    }

    public SverResponse<String> delUser(Integer id) {
        if (this.orderDao.findOrderByUid(id).size() > 0) {
            return SverResponse.createByErrorMessage("用户存在订单无法删除！");
        } else {
            User user = this.userDao.findUserById(id);
            user.setDel(1);
            user.setUpdate_time(new Date());
            int result = this.userDao.updateUserInfo(user);
            return result == 0 ? SverResponse.createByErrorMessage("用户删除失败！") : SverResponse.createRespBySuccess();
        }
    }

    public SverResponse<String> doRegister(User user) {
        SverResponse<String> resp = this.checkValidation(user.getAccount(), "account");
        if (!resp.isSuccess()) {
            return resp;
        } else {
            resp = this.checkValidation(user.getEmail(), "email");
            if (!resp.isSuccess()) {
                return resp;
            } else {
                user.setRole(1);
                user.setPassword(MD5Util.MD5Encode(user.getPassword(), "UTF-8", false));
                Date curDate = new Date();
                user.setCreate_time(curDate);
                user.setUpdate_time(curDate);
                int rs = this.userDao.insertUser(user);
                return rs == 0 ? SverResponse.createByErrorMessage("注册失败！") : SverResponse.createRespBySuccessMessage("注册成功！");
            }
        }
    }

    public SverResponse<String> checkValidation(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            int rs;
            if ("account".equals(type)) {
                rs = this.userDao.checkUserByAccount(str);
                if (rs > 0) {
                    return SverResponse.createByErrorMessage("用户名已经存在");
                }
            }

            if ("email".equals(type)) {
                rs = this.userDao.checkUserByEmail(str);
                if (rs > 0) {
                    return SverResponse.createByErrorMessage("Email已经存在");
                }
            }

            if ("phone".equals(type)) {
                rs = this.userDao.checkUserByPhone(str);
                if (rs > 0) {
                    return SverResponse.createByErrorMessage("电话号码已经存在！");
                }
            }

            return SverResponse.createRespBySuccessMessage("信息验证成功！");
        } else {
            return SverResponse.createByErrorMessage("信息验证类别错误！");
        }
    }

    public SverResponse<String> findUserQuestion(String account) {
        String question = this.userDao.findUserQuestion(account);
        return question == null ? SverResponse.createByErrorMessage("未设置密码提示问题！") : SverResponse.createRespBySuccess(question);
    }

    public SverResponse<String> checkUserAnswer(String account, String question, String asw) {
        int rs = this.userDao.checkUserAnswer(account, question, asw);
        if (rs > 0) {
            String token = UUID.randomUUID().toString();
            TokenCache.setCacheData("action_" + account, token);
            return SverResponse.createRespBySuccessMessage(token);
        } else {
            return SverResponse.createByErrorMessage("问题答案错误！");
        }
    }

    public SverResponse<String> resetPassword(Integer userId, String password) {
        String pwd = MD5Util.MD5Encode(password, "UTF-8", false);
        User user = this.userDao.findUserById(userId);
        user.setPassword(pwd);
        user.setUpdate_time(new Date());
        int rs = this.userDao.updateUserInfo(user);
        return rs > 0 ? SverResponse.createRespBySuccessMessage("密码修改成功！") : SverResponse.createByErrorMessage("密码修改失败！");
    }

    public SverResponse<String> updatePassword(User user, String newPassword, String oldPassword) {
        oldPassword = MD5Util.MD5Encode(oldPassword, "UTF-8", false);
        int rs = this.userDao.checkPassword(user.getAccount(), oldPassword);
        if (rs == 0) {
            return SverResponse.createByErrorMessage("原始密码错误！");
        } else {
            newPassword = MD5Util.MD5Encode(newPassword, "UTF-8", false);
            user.setPassword(newPassword);
            user.setUpdate_time(new Date());
            rs = this.userDao.updateUserInfo(user);
            return rs > 0 ? SverResponse.createRespBySuccessMessage("密码修改成功！") : SverResponse.createByErrorMessage("密码修改失败！");
        }
    }

    public SverResponse<User> findUserByAccount(String account) {
        User user = this.userDao.findUserByAccount(account);
        if (user == null) {
            return SverResponse.createByErrorMessage("用户名错误！");
        } else {
            user.setPassword("");
            user.setAsw("");
            return SverResponse.createRespBySuccess(user);
        }
    }
}
