

package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;
import cn.techaction.vo.ActionParamVo;
import java.util.List;

public interface ActionParamsService {
    SverResponse<String> addParam(ActionParam var1);

    SverResponse<String> updateParam(ActionParam var1);

    SverResponse<List<ActionParam>> findParamChildren(Integer var1);

    SverResponse<List<ActionParam>> findParamAndAllChildrenById(Integer var1);

    SverResponse<List<ActionParam>> findProdutTypeParams();

    SverResponse<List<ActionParamVo>> findPartsTypeParamsByProductTypeId(Integer var1);

    SverResponse<List<ActionParam>> findAllPathParams();

    SverResponse<String> delParam(Integer var1);

    SverResponse<List<ActionParam>> findAllParams();
}
