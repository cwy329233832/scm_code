package com.winway.scm.persistence.manager.impl;

import javax.annotation.Resource;

import com.winway.scm.model.ScmCwBanCommerce;
import com.winway.scm.model.ScmCwBanProduct;
import com.winway.scm.model.ScmCwDiscountRuleRecord;
import com.winway.scm.persistence.dao.ScmCwBanCommerceDao;
import com.winway.scm.persistence.dao.ScmCwBanProductDao;
import com.winway.scm.persistence.dao.ScmCwDiscountRuleRecordDao;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;
import com.hotent.base.dao.MyBatisDao;
import com.hotent.base.manager.impl.AbstractManagerImpl;
import com.winway.scm.persistence.dao.ScmCwDynamicDiscountDao;
import com.winway.scm.persistence.dao.ScmCwDynamicDiscountTypeDao;
import com.winway.scm.model.ScmCwDynamicDiscount;
import com.winway.scm.model.ScmCwDynamicDiscountType;
import com.winway.scm.persistence.manager.ScmCwDynamicDiscountManager;
import com.winway.scm.persistence.manager.ScmCwDynamicDiscountTypeManager;
import com.winway.scm.vo.DiscountVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 描述：动态票折规则 处理实现类
 * 构建组：x7
 * 作者:原浩
 * 邮箱:PRD-jun.he@winwayworld.com
 * 日期:2019-03-23 18:19:26
 * 版权：美达开发小组
 * </pre>
 */
@Service("scmCwDynamicDiscountManager")
public class ScmCwDynamicDiscountManagerImpl extends AbstractManagerImpl<String, ScmCwDynamicDiscount> implements ScmCwDynamicDiscountManager {
    @Resource
    ScmCwDynamicDiscountDao scmCwDynamicDiscountDao;

    @Resource
    ScmCwBanCommerceDao scmCwBanCommerceDao;

    @Resource
    ScmCwBanProductDao scmCwBanProductDao;
    
    @Resource
    ScmCwDynamicDiscountTypeManager scmCwDynamicDiscountTypeManager;
    
    @Resource
    ScmCwDynamicDiscountTypeDao scmCwDynamicDiscountTypeDao;

    @Resource
    ScmCwDiscountRuleRecordDao scmCwDiscountRuleRecordDao;

    @Override
    protected MyBatisDao<String, ScmCwDynamicDiscount> getDao() {
        return scmCwDynamicDiscountDao;
    }
    @Override
    public void create(ScmCwDynamicDiscount arg0) {
    	super.create(arg0);
    	String id = arg0.getId();
    	List<ScmCwDynamicDiscountType> scmCwDynamicDiscountTypeList = arg0.getScmCwDynamicDiscountTypeList();
    	for (ScmCwDynamicDiscountType scmCwDynamicDiscountType : scmCwDynamicDiscountTypeList) {
    		scmCwDynamicDiscountType.setDynamicDiscountId(id);
    		scmCwDynamicDiscountTypeManager.create(scmCwDynamicDiscountType);
		}
    }
    @Override
    public void update(ScmCwDynamicDiscount arg0) {
    	ScmCwDynamicDiscount scmCwDynamicDiscount = scmCwDynamicDiscountDao.get(arg0.getId());
    	if(scmCwDynamicDiscount == null) {
    		throw new RuntimeException("未查询到修改数据");
    	}
    	arg0.setSetPersion(arg0.getSetPersion());
    	arg0.setSetTime(arg0.getSetTime());
    	super.update(arg0);
    	scmCwDynamicDiscountTypeDao.delByMainId(arg0.getId());
    	List<ScmCwDynamicDiscountType> scmCwDynamicDiscountTypeList = arg0.getScmCwDynamicDiscountTypeList();
    	for (ScmCwDynamicDiscountType scmCwDynamicDiscountType : scmCwDynamicDiscountTypeList) {
    		scmCwDynamicDiscountType.setDynamicDiscountId(arg0.getId());
    		scmCwDynamicDiscountTypeManager.create(scmCwDynamicDiscountType);
		}
    }
    @Override
    public ScmCwDynamicDiscount get(String entityId) {
    	ScmCwDynamicDiscount scmCwDynamicDiscount = scmCwDynamicDiscountDao.get(entityId);
    	List<ScmCwDynamicDiscountType> scmCwDynamicDiscountTypes = scmCwDynamicDiscountTypeDao.getByMainId(scmCwDynamicDiscount.getId());
    	scmCwDynamicDiscount.setScmCwDynamicDiscountTypeList(scmCwDynamicDiscountTypes);
    	return scmCwDynamicDiscount;
    }
    
    @Override
    public Map<String, Object> getDiscountDetail(Map<String, Object> purchaseDetails) {
        String ownerId = (String) purchaseDetails.get("ownerId");
        String commerceId = (String) purchaseDetails.get("commerceId");
        String[] ids = (String[]) purchaseDetails.get("productIds");
        if (ownerId == null || commerceId == null || ids == null) {
            throw new RuntimeException("参数错误");
        }
        Map<String, Object> map = new HashedMap();
        //判断该商业是否可票折
        ScmCwBanCommerce scmCwBanCommerce = scmCwBanCommerceDao.getByCommerceId(commerceId);
        if (scmCwBanCommerce != null) {
            //该商业不可票折
            map.put("comType", false);
            map.put("discountList", null);
            return map;
        }
        //否则该经销商可折扣
        map.put("comType", true);
        //判断该商业可票折的商品
        List<DiscountVo> discountVoList = new ArrayList<>();
        for (String productId : ids) {
            ScmCwBanProduct banProduct = null;
            ScmCwDynamicDiscount dynamicDiscount = scmCwDynamicDiscountDao.getByProductIdAndOwnerId(productId, ownerId);
            DiscountVo discountVo = new DiscountVo();
            //禁止票折商品不为空 该商品禁止票折
            if (banProduct != null) {
                discountVo.setType("1");
            } else if (dynamicDiscount != null) {
                //如果动态票折规则不为空，则可票折
                discountVo.setDiscountId(dynamicDiscount.getId());
                discountVo.setProductId(productId);
                //查询票折记录表票上次折数量
                List<ScmCwDiscountRuleRecord> ruleRecord = scmCwDiscountRuleRecordDao.getByIdInfo(commerceId, productId, dynamicDiscount.getId());
                if (ruleRecord != null && !ruleRecord.isEmpty()) {
                    int total = 0;
                    for (ScmCwDiscountRuleRecord record : ruleRecord) {
                        total += record.getDiscountNum();
                    }
                    discountVo.setDiscountNum(dynamicDiscount.getProductSum() - total);
                }
                discountVo.setDiscountNum(dynamicDiscount.getProductSum());
                //该商业 该商品剩余可票折数量
                discountVo.setRate(discountVo.getRate());
                discountVo.setState(dynamicDiscount.getState());
                discountVo.setType("3");
            } else {
                //都为空 则该商品正常
                discountVo.setType("2");
                discountVoList.add(discountVo);
            }
        }
        map.put("discountList", discountVoList);
        return map;
    }
    @Override
    public void remove(String entityId) {
    	super.remove(entityId);
    }
	@Override
	public ScmCwDynamicDiscount getByProductCode(String productCode, String ownerId) {
		ScmCwDynamicDiscount scmCwDynamicDiscount = scmCwDynamicDiscountDao.getByProductCode(productCode,ownerId);
		if(scmCwDynamicDiscount == null) {
			return scmCwDynamicDiscount;
		}
		List<ScmCwDynamicDiscountType> byMainId = scmCwDynamicDiscountTypeDao.getByMainId(scmCwDynamicDiscount.getId());
		scmCwDynamicDiscount.setScmCwDynamicDiscountTypeList(byMainId);
		return scmCwDynamicDiscount;
	}
	@Override
	public ScmCwDynamicDiscount getByProductCodeAndCommerceId(String productCode, String commerceId, String ownerId) {
		ScmCwDynamicDiscount scmCwDynamicDiscount = scmCwDynamicDiscountDao.getByProductCodeAndCommerceId(productCode,commerceId,ownerId);
		if(scmCwDynamicDiscount == null) {
			return scmCwDynamicDiscount;
		}
		List<ScmCwDynamicDiscountType> byMainId = scmCwDynamicDiscountTypeDao.getByMainId(scmCwDynamicDiscount.getId());
		scmCwDynamicDiscount.setScmCwDynamicDiscountTypeList(byMainId);
		return scmCwDynamicDiscount;
	}
}
