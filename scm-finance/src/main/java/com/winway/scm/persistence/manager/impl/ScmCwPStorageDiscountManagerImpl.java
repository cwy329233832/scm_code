package com.winway.scm.persistence.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.hotent.base.dao.MyBatisDao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.winway.scm.persistence.dao.ScmCwPStorageDiscountDao;
import com.winway.scm.model.ScmCwPStorageDiscount;
import com.winway.scm.persistence.manager.ScmCwPStorageDiscountManager;

/**
 * 
 * <pre> 
 * 描述：经销商RB支付储运折扣 处理实现类
 * 构建组：x7
 * 作者:原浩
 * 邮箱:PRD-jun.he@winwayworld.com
 * 日期:2019-05-09 18:18:05
 * 版权：美达开发小组
 * </pre>
 */
@Service("scmCwPStorageDiscountManager")
public class ScmCwPStorageDiscountManagerImpl extends AbstractManagerImpl<String, ScmCwPStorageDiscount> implements ScmCwPStorageDiscountManager{
	@Resource
	ScmCwPStorageDiscountDao scmCwPStorageDiscountDao;
	@Override
	protected MyBatisDao<String, ScmCwPStorageDiscount> getDao() {
		return scmCwPStorageDiscountDao;
	}
}
