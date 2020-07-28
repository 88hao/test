
package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface ActionProductService {
    SverResponse<String> saveOrUpdateProduct(ActionProduct var1);

    SverResponse<String> updateStatus(Integer var1, Integer var2, Integer var3);

    SverResponse<ActionProduct> findProductDetailById(Integer var1);

    SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct var1, int var2, int var3);

    SverResponse<List<ActionProductListVo>> findProduts(ActionProduct var1);

    SverResponse<Map<String, String>> uploadFile(MultipartFile var1, String var2);

    SverResponse<ActionProduct> findProductDetailForPortal(Integer var1);

    SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer var1, Integer var2, String var3, int var4, int var5);

    SverResponse<List<ActionProduct>> findHotProducts(Integer var1);

    SverResponse<ActionProductFloorVo> findFloorProducts();
}
