//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.controller.backstage;

import cn.techaction.common.ResponseCode;
import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.pojo.User;
import cn.techaction.service.ActionProductService;
import cn.techaction.service.UserService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductListVo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/mgr/product"})
public class ActionProductBackController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActionProductService aProductService;

    public ActionProductBackController() {
    }

    @RequestMapping({"/productlist.do"})
    @ResponseBody
    public SverResponse<List<ActionProductListVo>> findProducts(HttpSession session, ActionProduct product) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aProductService.findProduts(product) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping(
        value = {"/saveproduct.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<String> saveProduct(HttpSession session, ActionProduct product) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aProductService.saveOrUpdateProduct(product) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/getdetail.do"})
    @ResponseBody
    public SverResponse<ActionProduct> getProductDetail(HttpSession session, Integer productId) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aProductService.findProductDetailById(productId) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping({"/setstatus.do"})
    @ResponseBody
    public SverResponse<String> modifyStatus(HttpSession session, Integer productId, Integer status, Integer hot) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            if (response.isSuccess()) {
                try {
                    return this.aProductService.updateStatus(productId, status, hot);
                } catch (Exception var8) {
                    var8.printStackTrace();
                    return SverResponse.createByErrorMessage("商品更新失败！");
                }
            } else {
                return SverResponse.createByErrorMessage("无操作权限！");
            }
        }
    }

    @RequestMapping({"/searchproducts.do"})
    @ResponseBody
    public SverResponse<PageBean<ActionProductListVo>> searchProducts(HttpSession session, ActionProduct product, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            return response.isSuccess() ? this.aProductService.findProductsByCondition(product, pageNum, pageSize) : SverResponse.createByErrorMessage("无操作权限！");
        }
    }

    @RequestMapping(
        value = {"/upload.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public SverResponse<Map<String, String>> uploadFiles(HttpSession session, @RequestParam(value = "file",required = false) MultipartFile file, HttpServletRequest request) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return SverResponse.createByErrorCodeMessage(ResponseCode.UNLOGIN.getCode(), "请登录后在进行操作！");
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            if (response.isSuccess()) {
                String path = request.getSession().getServletContext().getRealPath("/upload/");
                return this.aProductService.uploadFile(file, path);
            } else {
                return SverResponse.createByErrorMessage("无操作权限！");
            }
        }
    }

    @RequestMapping(
        value = {"/pic_upload.do"},
        method = {RequestMethod.POST}
    )
    @ResponseBody
    public Map<String, String> editorUploadFiles(HttpSession session, @RequestParam(value = "files",required = false) MultipartFile file, HttpServletRequest request) {
        User user = (User)session.getAttribute("curUser");
        if (user == null) {
            return null;
        } else {
            SverResponse<String> response = this.userService.isAdmin(user);
            Map<String, String> result = new HashMap();
            result.put("success", "false");
            if (response.isSuccess()) {
                String path = request.getSession().getServletContext().getRealPath("/upload/");
                SverResponse<Map<String, String>> resp = this.aProductService.uploadFile(file, path);
                if (resp.isSuccess()) {
                    result.put("success", "true");
                    result.put("file_path", request.getContextPath() + (String)((Map)resp.getData()).get("url"));
                }
            }

            return result;
        }
    }
}
