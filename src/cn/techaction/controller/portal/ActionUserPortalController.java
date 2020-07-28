

package cn.techaction.controller.portal;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.UserService;
import cn.techaction.vo.ActionUserVo;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/user"})
public class ActionUserPortalController {
    @Autowired
    private UserService userService;

    public ActionUserPortalController() {
    }

    @RequestMapping(
        value = {"/login.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public User login(String account, String password, HttpSession session) {
        User user = this.userService.login(account, password);
        return user;
    }

    @RequestMapping(
        value = {"/do_login.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<User> doLogin(String account, String password, HttpSession session) {
        SverResponse<User> response = this.userService.doLogin(account, password);
        if (response.isSuccess()) {
            session.setAttribute("curUser", response.getData());
        }

        return response;
    }

    @RequestMapping({"/do_logout.do"})
    @ResponseBody
    public SverResponse<String> loginOut(HttpSession session) {
        session.removeAttribute("curUser");
        return SverResponse.createRespBySuccess();
    }

    @RequestMapping(
        value = {"/do_register.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> registerUser(User user) {
        return this.userService.doRegister(user);
    }

    @RequestMapping(
        value = {"/do_check_info.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> checkValidUserInfo(String info, String type) {
        return this.userService.checkValidation(info, type);
    }

    @RequestMapping(
        value = {"/getuserinfo.do"},
        method = {RequestMethod.GET}
    )
    @ResponseBody
    public SverResponse<User> getUserInfo(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        return user != null ? SverResponse.createRespBySuccess(user) : SverResponse.createByErrorMessage("无法获取用户信息！");
    }

    @RequestMapping(
        value = {"/getuserquestion.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> getUserQuestion(String account) {
        return this.userService.findUserQuestion(account);
    }

    @RequestMapping(
        value = {"/checkuserasw.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> checkUserAnswer(String account, String question, String asw) {
        return this.userService.checkUserAnswer(account, question, asw);
    }

    @RequestMapping(
        value = {"/resetpassword.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> resetPassword(Integer userId, String newpwd) {
        return this.userService.resetPassword(userId, newpwd);
    }

    @RequestMapping(
        value = {"/updatepassword.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> updatePassword(HttpSession session, String newpwd, String oldpwd) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorMessage("请登录后再修改密码！");
        } else {
            SverResponse<String> result = this.userService.updatePassword(user, newpwd, oldpwd);
            if (result.isSuccess()) {
                session.removeAttribute("curUser");
            }

            return result;
        }
    }

    @RequestMapping(
        value = {"/updateuserinfo.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<User> updateUserInfo(HttpSession session, ActionUserVo userVo) {
        User curUser = (User)session.getAttribute("curUser");
        if (curUser == null) {
            return SverResponse.createByErrorMessage("用户尚未登录");
        } else {
            userVo.setId(curUser.getId());
            userVo.setAccount(curUser.getAccount());
            SverResponse<User> resp = this.userService.updateUserInfo(userVo);
            if (resp.isSuccess()) {
                session.setAttribute("curUser", resp.getData());
            }

            return resp;
        }
    }

    @RequestMapping(
        value = {"/getUserByAccount.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<User> getUserByAccount(String account) {
        return this.userService.findUserByAccount(account);
    }
}
