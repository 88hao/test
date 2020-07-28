//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.controller.backstage;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.vo.ActionUserVo;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/mgr/user"})
public class ActionUserBackController {
    @Autowired
    private UserService userService;

    public ActionUserBackController() {
    }

    @RequestMapping(
        value = {"/login.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<User> doLogin(String account, String password, HttpSession session) {
        SverResponse<User> response = this.userService.doLogin(account, password);
        if (response.isSuccess()) {
            User user = (User)response.getData();
            if (user.getRole() == 2) {
                session.setAttribute("curUser", user);
                return response;
            } else {
                return SverResponse.createByErrorMessage("不是管理员,无法登录");
            }
        } else {
            return response;
        }
    }

    @RequestMapping({"/finduserlist.do"})
    @ResponseBody
    public SverResponse<List<ActionUserVo>> getUserDetail(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.userService.findUserList() : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/isAdmin.do"})
    @ResponseBody
    public SverResponse<String> isAdmin(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? SverResponse.createRespBySuccess() : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/finduser.do"})
    @ResponseBody
    public SverResponse<ActionUserVo> findUser(HttpSession session, Integer id) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.userService.findUser(id) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/updateuser.do"})
    @ResponseBody
    public SverResponse<User> updateUser(HttpSession session, ActionUserVo userVo) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.userService.updateUserInfo(userVo) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/deleteusers.do"})
    @ResponseBody
    public SverResponse<String> delUsers(HttpSession session, Integer id) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.userService.delUser(id) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }
}
