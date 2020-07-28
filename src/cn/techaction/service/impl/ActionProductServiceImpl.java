

package cn.techaction.service.impl;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.PageBean;
import cn.techaction.utils.ConstUtil.HotStatus;
import cn.techaction.utils.ConstUtil.ProductStatus;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ActionProductServiceImpl implements ActionProductService {
    @Autowired
    private ActionProductDao aProductDao;
    @Autowired
    private ActionParamsDao aParamsDao;

    public ActionProductServiceImpl() {
    }

    public SverResponse<List<ActionProductListVo>> findProduts(ActionProduct product) {
        if (product.getName() != null) {
            product.setName("%" + product.getName() + "%");
        }

        List<ActionProduct> products = this.aProductDao.findProductsNoPage(product);
        List<ActionProductListVo> voList = Lists.newArrayList();
        Iterator var5 = products.iterator();

        while(var5.hasNext()) {
            ActionProduct p = (ActionProduct)var5.next();
            voList.add(this.createProductListVo(p));
        }

        return SverResponse.createRespBySuccess(voList);
    }

    private ActionProductListVo createProductListVo(ActionProduct product) {
        ActionProductListVo vo = new ActionProductListVo();
        vo.setId(product.getId());
        vo.setName(product.getName());
        vo.setPrice(product.getPrice());
        vo.setStatus(product.getStatus());
        vo.setStatusDesc(ProductStatus.getStatusDesc(product.getStatus()));
        vo.setProductCategory(this.aParamsDao.findParamById(product.getProductId()).getName());
        vo.setPartsCategory(this.aParamsDao.findParamById(product.getPartsId()).getName());
        vo.setIconUrl(product.getIconUrl());
        vo.setHotStatus(HotStatus.getHotDesc(product.getHot()));
        vo.setHot(product.getHot());
        return vo;
    }

    public SverResponse<String> saveOrUpdateProduct(ActionProduct product) {
        if (product != null) {
            if (StringUtils.isNoneBlank(new CharSequence[]{product.getSubImages()})) {
                String[] array = product.getSubImages().split(",");
                product.setIconUrl(array[0]);
                String temp = product.getSubImages();
                int index = temp.indexOf(",");
                if (index != -1) {
                    if (temp.substring(temp.indexOf(",") + 1).equals("null")) {
                        product.setSubImages((String)null);
                    } else {
                        product.setSubImages(temp.substring(temp.indexOf(",") + 1));
                    }
                } else {
                    product.setSubImages((String)null);
                }
            }

            int rs;
            if (product.getId() != null) {
                product.setUpdated(new Date());
                rs = this.aProductDao.updateProduct(product);
                return rs > 0 ? SverResponse.createRespBySuccessMessage("产品更新成功！") : SverResponse.createByErrorMessage("产品更新失败！");
            } else {
                product.setStatus(1);
                product.setHot(2);
                product.setUpdated(new Date());
                product.setCreated(new Date());
                rs = this.aProductDao.insertProduct(product);
                return rs > 0 ? SverResponse.createRespBySuccessMessage("产品新增成功！") : SverResponse.createByErrorMessage("产品新增失败！");
            }
        } else {
            return SverResponse.createByErrorMessage("产品参数错误！");
        }
    }

    public SverResponse<String> updateStatus(Integer productId, Integer status, Integer hot) {
        if (productId != null && status != null && hot != null) {
            ActionProduct product = new ActionProduct();
            product.setId(productId);
            product.setUpdated(new Date());
            if (status == -1) {
                product.setHot(hot);
            } else if (hot == -1) {
                product.setStatus(status);
            }

            int rs = this.aProductDao.updateProduct(product);
            return rs > 0 ? SverResponse.createRespBySuccessMessage("修改产品状态成功！") : SverResponse.createByErrorMessage("修改产品状态失败！");
        } else {
            return SverResponse.createByErrorMessage("非法参数！");
        }
    }

    public SverResponse<ActionProduct> findProductDetailById(Integer productId) {
        if (productId == null) {
            return SverResponse.createByErrorMessage("产品编号不能为空！");
        } else {
            ActionProduct product = this.aProductDao.findProductById(productId);
            return product == null ? SverResponse.createByErrorMessage("产品已经下架！") : SverResponse.createRespBySuccess(product);
        }
    }

    public SverResponse<Map<String, String>> uploadFile(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);

        try {
            file.transferTo(targetFile);
        } catch (IOException var9) {
            return SverResponse.createByErrorMessage("文件上传错误！");
        }

        Map<String, String> fileMap = Maps.newHashMap();
        fileMap.put("url", "/upload/" + targetFile.getName());
        System.out.println((String)fileMap.get("url"));
        return SverResponse.createRespBySuccess(fileMap);
    }

    public SverResponse<PageBean<ActionProductListVo>> findProductsByCondition(ActionProduct product, int pageNum, int pageSize) {
        if (product.getName() != null) {
            product.setName("%" + product.getName() + "%");
        }

        int totalRecord = this.aProductDao.getTotalCount(product);
        PageBean<ActionProductListVo> pageBean = new PageBean(pageNum, pageSize, totalRecord);
        List<ActionProduct> products = this.aProductDao.findProducts(product, pageBean.getStartIndex(), pageSize);
        List<ActionProductListVo> voList = Lists.newArrayList();
        Iterator var9 = products.iterator();

        while(var9.hasNext()) {
            ActionProduct p = (ActionProduct)var9.next();
            voList.add(this.createProductListVo(p));
        }

        pageBean.setData(voList);
        return SverResponse.createRespBySuccess(pageBean);
    }

    public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
        if (productId == null) {
            return SverResponse.createByErrorMessage("产品编号不能为空！");
        } else {
            ActionProduct product = this.aProductDao.findProductById(productId);
            if (product == null) {
                return SverResponse.createByErrorMessage("产品已经下架！");
            } else {
                return product.getStatus() == 3 ? SverResponse.createByErrorMessage("产品已经下架！") : SverResponse.createRespBySuccess(product);
            }
        }
    }

    public SverResponse<PageBean<ActionProductListVo>> findProductsForPortal(Integer productTypeId, Integer partsId, String name, int pageNum, int pageSize) {
        ActionProduct product = new ActionProduct();
        boolean totalRecord = false;
        if (name != null && !name.equals("")) {
            product.setName(name);
        }

        if (productTypeId != 0) {
            product.setProductId(productTypeId);
        }

        if (partsId != 0) {
            product.setPartsId(partsId);
        }

        product.setStatus(2);
        int totalRecord1 = this.aProductDao.getTotalCount(product);
        PageBean<ActionProductListVo> pageBean = new PageBean(pageNum, pageSize, totalRecord1);
        List<ActionProduct> products = this.aProductDao.findProducts(product, pageBean.getStartIndex(), pageSize);
        List<ActionProductListVo> voList = Lists.newArrayList();
        Iterator var12 = products.iterator();

        while(var12.hasNext()) {
            ActionProduct p = (ActionProduct)var12.next();
            voList.add(this.createProductListVo(p));
        }

        pageBean.setData(voList);
        return SverResponse.createRespBySuccess(pageBean);
    }

    public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
        List<ActionProduct> products = this.aProductDao.findHotProducts(num);
        return SverResponse.createRespBySuccess(products);
    }

    public SverResponse<ActionProductFloorVo> findFloorProducts() {
        ActionProductFloorVo vo = new ActionProductFloorVo();
        List<ActionProduct> products1 = this.aProductDao.findProductsByProductCategory(10023);
        if (products1.size() >= 8) {
            products1 = products1.subList(0, 8);
        }

        vo.setOneFloor(products1);
        List<ActionProduct> products2 = this.aProductDao.findProductsByProductCategory(10024);
        if (products2.size() >= 8) {
            products2 = products2.subList(0, 8);
        }

        vo.setTwoFloor(products2);
        List<ActionProduct> products3 = this.aProductDao.findProductsByProductCategory(10025);
        if (products3.size() >= 8) {
            products3 = products3.subList(0, 8);
        }

        vo.setThreeFloor(products3);
        List<ActionProduct> products4 = this.aProductDao.findProductsByProductCategory(10026);
        if (products4.size() >= 8) {
            products4 = products4.subList(0, 8);
        }

        vo.setFourFloor(products4);
        return SverResponse.createRespBySuccess(vo);
    }
}
