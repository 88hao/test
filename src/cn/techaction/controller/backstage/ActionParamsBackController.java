//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.controller.backstage;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionParamsService;
import cn.techaction.service.UserService;
import cn.techaction.vo.ActionParamVo;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/mgr/param"})
public class ActionParamsBackController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActionParamsService aParamService;

    public ActionParamsBackController() {
    }

    @RequestMapping(
        value = {"/saveparam.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> saveParam(HttpSession session, ActionParam param) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aParamService.addParam(param) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping(
        value = {"/updateparam.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> updateCategory(HttpSession session, ActionParam param) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aParamService.updateParam(param) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/delparam.do"})
    @ResponseBody
    public SverResponse<String> delParam(@RequestParam(value = "id",defaultValue = "0") Integer id, HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aParamService.delParam(id) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/findchildren.do"})
    @ResponseBody
    public SverResponse<List<ActionParam>> getChildrenParam(HttpSession session, @RequestParam(value = "id",defaultValue = "0") Integer id) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aParamService.findParamChildren(id) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/findallchildren.do"})
    @ResponseBody
    public SverResponse<List<ActionParam>> getParamAndAllChildren(HttpSession session, @RequestParam(value = "id",defaultValue = "0") Integer id) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aParamService.findParamAndAllChildrenById(id) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/findptype.do"})
    @ResponseBody
    public SverResponse<List<ActionParam>> getParamByParentId(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aParamService.findProdutTypeParams() : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/findpartstype.do"})
    @ResponseBody
    public SverResponse<List<ActionParamVo>> getPartsParam(HttpSession session, Integer productTypeId) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aParamService.findPartsTypeParamsByProductTypeId(productTypeId) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/findpathparam.do"})
    @ResponseBody
    public SverResponse<List<ActionParam>> getPathParam(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aParamService.findAllPathParams() : SverResponse.createByErrorMessage("无操作权限！");
        }
    }
}
