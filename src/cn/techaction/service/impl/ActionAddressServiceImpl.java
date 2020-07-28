//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.techaction.service.impl;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.service.ActionAddrService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionAddressServiceImpl implements ActionAddrService {
    @Autowired
    private ActionAddressDao aAddressDao;

    public ActionAddressServiceImpl() {
    }

    public SverResponse<List<ActionAddress>> findAddrsByUserId(Integer userId) {
        if (userId == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            List<ActionAddress> list = this.aAddressDao.findAddrsByUserId(userId);
            return SverResponse.createRespBySuccess(list);
        }
    }

    public SverResponse<String> addAddress(ActionAddress addr) {
        if (addr == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            int count = this.aAddressDao.findDefaultAddrByUserId(addr.getUid());
            if (count == 0) {
                addr.setDefault_addr(1);
            } else {
                addr.setDefault_addr(0);
            }

            addr.setCreated(new Date());
            addr.setUpdated(new Date());
            int rs = this.aAddressDao.insertAddress(addr);
            return rs > 0 ? SverResponse.createRespBySuccessMessage("地址新增成功！") : SverResponse.createRespBySuccessMessage("地址新增失败！");
        }
    }

    public SverResponse<String> delAddress(Integer userId, Integer id) {
        if (id == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            ActionAddress address = new ActionAddress();
            address.setId(id);
            address.setDel_state(1);
            address.setUpdated(new Date());
            int rs = this.aAddressDao.updateAddress(address);
            return rs > 0 ? SverResponse.createRespBySuccessMessage("地址删除成功！") : SverResponse.createRespBySuccessMessage("地址删除失败！");
        }
    }

    public SverResponse<String> updateAddress(ActionAddress addr) {
        if (addr == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            addr.setUpdated(new Date());
            int rs = this.aAddressDao.updateAddress(addr);
            return rs > 0 ? SverResponse.createRespBySuccessMessage("地址更新成功！") : SverResponse.createRespBySuccessMessage("地址更新失败！");
        }
    }

    public SverResponse<String> updateAddrDefaultStatus(Integer userId, Integer id) {
        if (id != null && userId != null) {
            ActionAddress oldAddr = this.aAddressDao.findDefaultAddr(userId);
            if (oldAddr != null) {
                oldAddr.setDefault_addr(0);
                oldAddr.setUpdated(new Date());
                if (this.aAddressDao.updateAddress(oldAddr) <= 0) {
                    return SverResponse.createRespBySuccessMessage("默认地址设置失败！");
                }
            }

            ActionAddress newAddr = new ActionAddress();
            newAddr.setDefault_addr(1);
            newAddr.setId(id);
            newAddr.setUpdated(new Date());
            return this.aAddressDao.updateAddress(newAddr) <= 0 ? SverResponse.createRespBySuccessMessage("默认地址设置失败！") : SverResponse.createRespBySuccessMessage("默认地址设置成功！");
        } else {
            return SverResponse.createByErrorMessage("参数错误！");
        }
    }

    public SverResponse<ActionAddress> getAddressById(Integer id) {
        if (id == null) {
            return SverResponse.createByErrorMessage("参数错误！");
        } else {
            ActionAddress address = this.aAddressDao.findAddrsById(id);
            return SverResponse.createRespBySuccess(address);
        }
    }
}
