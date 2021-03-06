package com.winway.scm.model;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.hotent.base.model.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


 /**
 * 商业授权销售品种
 * <pre> 
 * 描述：商业授权销售品种 实体对象
 * 构建组：x7
 * 作者:原浩
 * 邮箱:PRD-jun.he@winwayworld.com
 * 日期:2019-01-03 09:41:54
 * 版权：美达开发小组
 * </pre>
 */
 @ApiModel(value = "ScmZsjCommerceAccredit",description = "商业授权销售品种") 
public class ScmZsjCommerceAccredit extends BaseModel<String>{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="id")
	protected String id; 
	
	@ApiModelProperty(value="委托书ID",required = true)
	protected String entrustId; 
	
	@ApiModelProperty(value="产品编号",required = true)
	protected String productCode; 
	
	@ApiModelProperty(value="产品名称",required = true)
	protected String productName; 
	
	@ApiModelProperty(value="通用名称",required = true)
	protected String commonName; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 id
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	/**
	 * 返回 委托书ID
	 * @return
	 */
	public String getEntrustId() {
		return this.entrustId;
	}
	
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * 返回 产品编号
	 * @return
	 */
	public String getProductCode() {
		return this.productCode;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	/**
	 * 返回 产品名称
	 * @return
	 */
	public String getProductName() {
		return this.productName;
	}
	
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	
	/**
	 * 返回 通用名称
	 * @return
	 */
	public String getCommonName() {
		return this.commonName;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("entrustId", this.entrustId) 
		.append("productCode", this.productCode) 
		.append("productName", this.productName) 
		.append("commonName", this.commonName) 
		.toString();
	}
}