

package cn.techaction.controller.portal;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionAddrService;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/addr"})
public class ActionAddressController {
    @Autowired
    private ActionAddrService aAddrService;

    public ActionAddressController() {
    }

    @RequestMapping(
        value = {"/saveaddr.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<List<ActionAddress>> saveAddress(HttpSession session, ActionAddress addr) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorMessage("请登录后在进行操作！");
        } else {
            addr.setUid(user.getId());
            SverResponse<String> result = null;
            if (addr.getId() == null) {
                result = this.aAddrService.addAddress(addr);
            } else {
                result = this.aAddrService.updateAddress(addr);
            }

            return result.isSuccess() ? this.aAddrService.findAddrsByUserId(user.getId()) : SverResponse.createByErrorMessage(result.getMsg());
        }
    }

    @RequestMapping({"/deladdr.do"})
    @ResponseBody
    public SverResponse<List<ActionAddress>> deleteAddress(HttpSession session, Integer id) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorMessage("请登录后在进行操作！");
        } else {
            SverResponse<String> result = this.aAddrService.delAddress(user.getId(), id);
            return result.isSuccess() ? this.aAddrService.findAddrsByUserId(user.getId()) : SverResponse.createByErrorMessage(result.getMsg());
        }
    }

    @RequestMapping(
        value = {"/updateaddr.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> updateAddress(HttpSession session, ActionAddress addr) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aAddrService.updateAddress(addr);
    }

    @RequestMapping({"/findaddrs.do"})
    @ResponseBody
    public SverResponse<List<ActionAddress>> findaddrs(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aAddrService.findAddrsByUserId(user.getId());
    }

    @RequestMapping({"/setdefault.do"})
    @ResponseBody
    public SverResponse<List<ActionAddress>> setDefault(HttpSession session, Integer id) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorMessage("请登录后在进行操作！");
        } else {
            SverResponse<String> result = this.aAddrService.updateAddrDefaultStatus(user.getId(), id);
            return result.isSuccess() ? this.aAddrService.findAddrsByUserId(user.getId()) : SverResponse.createByErrorMessage(result.getMsg());
        }
    }

    @RequestMapping({"/findAddressById.do"})
    @ResponseBody
    public SverResponse<ActionAddress> findAddressById(HttpSession session, Integer id) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aAddrService.getAddressById(id);
    }
}
