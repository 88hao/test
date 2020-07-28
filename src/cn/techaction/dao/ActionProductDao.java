

package cn.techaction.dao;

import cn.techaction.pojo.ActionProduct;
import java.util.List;

public interface ActionProductDao {
    ActionProduct findProductById(Integer var1);

    int insertProduct(ActionProduct var1);

    int updateProduct(ActionProduct var1);

    int deleteProductById(Integer var1);

    Integer getTotalCount(ActionProduct var1);

    List<ActionProduct> findProducts(ActionProduct var1, int var2, int var3);

    List<ActionProduct> findProductsNoPage(ActionProduct var1);

    List<ActionProduct> findHotProducts(Integer var1);

    List<ActionProduct> findProductsByProductCategory(Integer var1);

    List<ActionProduct> findProductsByPartsId(Integer var1);
}
