package com.winway.scm.persistence.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hotent.base.dao.MyBatisDao;
import com.winway.scm.model.ScmZsjSupplierLicence;

/**
 * 
 * <pre> 
 * 描述：供应商许可证信息 DAO接口
 * 构建组：x7
 * 作者:原浩
 * 邮箱:PRD-jun.he@winwayworld.com
 * 日期:2019-01-03 09:41:54
 * 版权：美达开发小组
 * </pre>
 */
public interface ScmZsjSupplierLicenceDao extends MyBatisDao<String, ScmZsjSupplierLicence> {
	/**
	 * 根据外键获取子表明细列表
	 * @param supplierFirstId
	 * @return
	 */
	public List<ScmZsjSupplierLicence> getByMainId(String supplierFirstId);
	
	/**
	 * 根据外键删除子表记录
	 * @param supplierFirstId
	 * @return
	 */
	public void delByMainId(String supplierFirstId);
	
}
