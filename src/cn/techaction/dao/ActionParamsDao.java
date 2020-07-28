

package cn.techaction.dao;

import cn.techaction.pojo.ActionParam;
import java.util.List;

public interface ActionParamsDao {
    int insertParam(ActionParam var1);

    int updateParam(ActionParam var1);

    List<ActionParam> findParamsByParentId(Integer var1);

    ActionParam findParamById(Integer var1);

    int deleteParam(Integer var1);

    ActionParam findParamByParentIdandName(Integer var1, String var2);
}
