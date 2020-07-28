

package cn.techaction.service.impl;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionParam;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionParamsService;
import cn.techaction.vo.ActionParamVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionParamsServiceImpl implements ActionParamsService {
    @Autowired
    private ActionParamsDao aParamDao;
    @Autowired
    private ActionProductDao actionProductDao;

    public ActionParamsServiceImpl() {
    }

    public SverResponse<String> addParam(ActionParam param) {
        if (StringUtils.isBlank(param.getName())) {
            return SverResponse.createByErrorMessage("产品参数名称不能为空！");
        } else if (this.aParamDao.findParamByParentIdandName(param.getParent_id(), param.getName()) != null) {
            return SverResponse.createByErrorMessage("产品参数名称已存在！");
        } else {
            param.setStatus(true);
            param.setCreated(new Date());
            param.setUpdated(new Date());
            param.setLevel(this.getParamLevel(param.getParent_id()));
            int rs = this.aParamDao.insertParam(param);
            return rs > 0 ? SverResponse.createRespBySuccessMessage("产品参数新增成功！") : SverResponse.createByErrorMessage("产品参数新增失败！");
        }
    }

    private int getParamLevel(int parentId) {
        ActionParam parentParam = this.aParamDao.findParamById(parentId);
        if (parentParam != null) {
            int count = parentParam.getLevel();
            return count + 1;
        } else {
            return 0;
        }
    }

    public SverResponse<String> updateParam(ActionParam param) {
        if (param.getId() == 0 || StringUtils.isBlank(param.getName())) {
            SverResponse.createByErrorMessage("参数错误！");
        }

        ActionParam pra = this.aParamDao.findParamById(param.getId());
        if (this.aParamDao.findParamByParentIdandName(param.getParent_id(), param.getName()) != null) {
            return SverResponse.createByErrorMessage("产品参数名称已存在！");
        } else {
            pra.setUpdated(new Date());
            pra.setName(param.getName());
            int rs = this.aParamDao.updateParam(pra);
            return rs > 0 ? SverResponse.createRespBySuccessMessage("产品参数修改成功！") : SverResponse.createByErrorMessage("产品参数修改失败！");
        }
    }

    public SverResponse<List<ActionParam>> findParamChildren(Integer id) {
        List<ActionParam> list = this.aParamDao.findParamsByParentId(id);
        return SverResponse.createRespBySuccess(list);
    }

    public SverResponse<List<ActionParam>> findParamAndAllChildrenById(Integer id) {
        Set<ActionParam> paramSet = Sets.newHashSet();
        this.findChildren(paramSet, id);
        List<ActionParam> paramsList = Lists.newArrayList();
        if (id != null) {
            Iterator var5 = paramSet.iterator();

            while(var5.hasNext()) {
                ActionParam param = (ActionParam)var5.next();
                paramsList.add(param);
            }
        }

        return SverResponse.createRespBySuccess(paramsList);
    }

    private Set<ActionParam> findChildren(Set<ActionParam> paramSet, Integer id) {
        ActionParam param = this.aParamDao.findParamById(id);
        if (param != null) {
            paramSet.add(param);
        }

        List<ActionParam> paramList = this.aParamDao.findParamsByParentId(id);
        Iterator var6 = paramList.iterator();

        while(var6.hasNext()) {
            ActionParam p = (ActionParam)var6.next();
            this.findChildren(paramSet, p.getId());
        }

        return paramSet;
    }

    public SverResponse<List<ActionParam>> findProdutTypeParams() {
        List<ActionParam> list = this.aParamDao.findParamsByParentId(0);
        return SverResponse.createRespBySuccess(list);
    }

    public SverResponse<List<ActionParamVo>> findPartsTypeParamsByProductTypeId(Integer productTypeId) {
        List<ActionParam> paramList = this.aParamDao.findParamsByParentId(productTypeId);
        Iterator var4 = paramList.iterator();

        while(var4.hasNext()) {
            ActionParam param = (ActionParam)var4.next();
            this.findDirectChildren(param);
        }

        List<ActionParamVo> volist = Lists.newArrayList();
        Iterator var5 = paramList.iterator();

        while(true) {
            while(var5.hasNext()) {
                ActionParam secondLevel = (ActionParam)var5.next();
                if (secondLevel.getChildren().size() > 0) {
                    Iterator var7 = secondLevel.getChildren().iterator();

                    while(var7.hasNext()) {
                        ActionParam thirdLevel = (ActionParam)var7.next();
                        ActionParamVo vo = new ActionParamVo();
                        vo.setId(thirdLevel.getId());
                        vo.setName(secondLevel.getName() + "/" + thirdLevel.getName());
                        volist.add(vo);
                    }
                } else {
                    ActionParamVo vo = new ActionParamVo();
                    vo.setId(secondLevel.getId());
                    vo.setName(secondLevel.getName());
                    volist.add(vo);
                }
            }

            return SverResponse.createRespBySuccess(volist);
        }
    }

    private void findDirectChildren(ActionParam parentParam) {
        List<ActionParam> paramList = this.aParamDao.findParamsByParentId(parentParam.getId());
        parentParam.setChildren(paramList);
        Iterator var4 = paramList.iterator();

        while(var4.hasNext()) {
            ActionParam p = (ActionParam)var4.next();
            this.findDirectChildren(p);
        }

    }

    public SverResponse<List<ActionParam>> findAllPathParams() {
        List<ActionParam> paramList = this.aParamDao.findParamsByParentId(0);
        Iterator var3 = paramList.iterator();

        while(var3.hasNext()) {
            ActionParam param = (ActionParam)var3.next();
            this.findDirectChildren(param);
        }

        List<ActionParam> list = Lists.newArrayList();
        Iterator var4 = paramList.iterator();

        while(var4.hasNext()) {
            ActionParam param = (ActionParam)var4.next();
            this.createDeepParam(list, param);
        }

        return SverResponse.createRespBySuccess(list);
    }

    private void createDeepParam(List<ActionParam> list, ActionParam parent) {
        list.add(parent);
        Iterator var4 = parent.getChildren().iterator();

        while(var4.hasNext()) {
            ActionParam p = (ActionParam)var4.next();
            p.setName(parent.getName() + "/" + p.getName());
            this.createDeepParam(list, p);
        }

    }

    public SverResponse<String> delParam(Integer id) {
        List<ActionParam> pars = this.aParamDao.findParamsByParentId(id);
        if (pars == null) {
            return SverResponse.createByErrorMessage("数据库操作异常！");
        } else if (pars.size() != 0) {
            return SverResponse.createByErrorMessage("请先删除子类型！");
        } else {
            List<ActionProduct> products = this.actionProductDao.findProductsByPartsId(id);
            if (products.size() != 0) {
                return SverResponse.createByErrorMessage("不能删除有商品的类型！");
            } else {
                int result = this.aParamDao.deleteParam(id);
                return result == 0 ? SverResponse.createByErrorMessage("数据库操作异常！") : SverResponse.createRespBySuccess();
            }
        }
    }

    public SverResponse<List<ActionParam>> findAllParams() {
        List<ActionParam> paramList = this.aParamDao.findParamsByParentId(0);
        Iterator var3 = paramList.iterator();

        while(var3.hasNext()) {
            ActionParam param = (ActionParam)var3.next();
            this.findDirectChildren(param);
        }

        return SverResponse.createRespBySuccess(paramList);
    }
}
