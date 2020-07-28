
package cn.techaction.controller.portal;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
//web请求为product
@Controller
@RequestMapping({"/product"})
public class ActionProductController {
    @Autowired
    private ActionProductService aProductService;

    public ActionProductController() {
    }
//请求路径映射
    @RequestMapping({"/getdetail.do"})
    @ResponseBody
    public SverResponse<ActionProduct> getProductDetail(Integer productId) {
        return this.aProductService.findProductDetailForPortal(productId);
    }

    @RequestMapping({"/findproducts.do"})
    @ResponseBody
    public SverResponse<PageBean<ActionProductListVo>> searchProducts(Integer productTypeId, Integer partsId, String name, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize) {
        return this.aProductService.findProductsForPortal(productTypeId, partsId, name, pageNum, pageSize);
    }

    @RequestMapping({"/findhotproducts.do"})
    @ResponseBody
    public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
        return this.aProductService.findHotProducts(num);
    }

    @RequestMapping({"/findfloors.do"})
    @ResponseBody
    public SverResponse<ActionProductFloorVo> findFloorProducts() {
        return this.aProductService.findFloorProducts();
    }
}
