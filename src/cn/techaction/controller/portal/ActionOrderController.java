
package cn.techaction.controller.portal;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/order"})
public class ActionOrderController {
    @Autowired
    private ActionOrderService aOrderService;

    public ActionOrderController() {
    }

    @RequestMapping(
        value = {"/createorder.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<ActionOrderVo> createOrder(HttpSession session, Integer addrId) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aOrderService.generateOrder(user.getId(), addrId);
    }

    @RequestMapping({"/getlist.do"})
    @ResponseBody
    public SverResponse<PageBean<ActionOrderVo>> list(HttpSession session, Integer status, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aOrderService.findOrders(user.getId(), status, pageNum, pageSize);
    }

    @RequestMapping({"/cancelorder.do"})
    @ResponseBody
    public SverResponse<String> cancelOrder(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aOrderService.cancelOrder(user.getId(), orderNo);
    }

    @RequestMapping({"/getdetail.do"})
    @ResponseBody
    public SverResponse<ActionOrderVo> getDetail(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aOrderService.findOrderDetail(user.getId(), orderNo);
    }

    @RequestMapping({"/confirmreceipt.do"})
    @ResponseBody
    public SverResponse<String> confirmReceipt(HttpSession session, Long orderNo) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后在进行操作！") : this.aOrderService.cancelOrder(user.getId(), orderNo);
    }
}
