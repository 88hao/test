
package cn.techaction.controller.portal;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionCartService;
import cn.techaction.vo.ActionCartVo;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({"/cart"})
public class ActionCartController {
    @Autowired
    private ActionCartService aCartService;

    public ActionCartController() {
    }

    @RequestMapping(
        value = {"/savecart.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> addProductCart(HttpSession session, Integer productId, Integer count) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后，在查看购物车！") : this.aCartService.saveOrUpdate(user.getId(), productId, count);
    }

    @RequestMapping({"/findallcarts.do"})
    @ResponseBody
    public SverResponse<ActionCartVo> findAllCarts(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后，在查看购物车！") : this.aCartService.findAllCarts(user.getId());
    }

    @RequestMapping({"/delcarts.do"})
    @ResponseBody
    public SverResponse<ActionCartVo> deleteCart(HttpSession session, Integer productId) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后，在查看购物车！") : this.aCartService.deleteCart(user.getId(), productId);
    }

    @RequestMapping({"/updatecarts.do"})
    @ResponseBody
    public SverResponse<String> updateCarts(HttpSession session, Integer productId, Integer count, Integer checked) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后，在查看购物车！") : this.aCartService.updateCart(user.getId(), productId, count, checked);
    }

    @RequestMapping({"/clearcarts.do"})
    @ResponseBody
    public SverResponse<String> clearCarts(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后，在查看购物车！") : this.aCartService.clearCart(user.getId());
    }

    @RequestMapping({"/getcartcount.do"})
    @ResponseBody
    public SverResponse<Integer> getCartsCount(HttpSession session) {
        User user = (User)session.getAttribute("curUser");
        return user == null ? SverResponse.createByErrorMessage("请登录后，在查看购物车！") : this.aCartService.getCartCount(user.getId());
    }
}
