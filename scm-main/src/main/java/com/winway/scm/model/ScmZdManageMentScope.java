package com.winway.scm.model;
import org.apache.commons.lang.builder.ToStringBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hotent.base.model.BaseModel;


 /**
 * 生产经营范围
 * <pre> 
 * 描述：生产经营范围 实体对象
 * 构建组：x7
 * 作者:原浩
 * 邮箱:PRD-jun.he@winwayworld.com
 * 日期:2018-12-29 10:31:24
 * 版权：美达项目组
 * </pre>
 */
 @ApiModel(value = "ScmZdManageMentScope",description = "生产经营范围") 
public class ScmZdManageMentScope extends BaseModel<String>{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="生产经营范围ID")
	protected String id; 
	
	@ApiModelProperty(value="生产经营范围")
	protected String managementScope; 
	
	@ApiModelProperty(value="编码")
	protected String code; 
	
	@ApiModelProperty(value="备注")
	protected String memo; 
	
	@ApiModelProperty(value="创建人")
	protected String createPersion; 
	
	@ApiModelProperty(value="创建时间")
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	protected java.util.Date createDate; 
	
	
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 生产经营范围ID
	 * @return
	 */
	public String getId() {
		return this.id;
	}
	
	public void setManagementScope(String managementScope) {
		this.managementScope = managementScope;
	}
	
	/**
	 * 返回 生产经营范围
	 * @return
	 */
	public String getManagementScope() {
		return this.managementScope;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 返回 编码
	 * @return
	 */
	public String getCode() {
		return this.code;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	/**
	 * 返回 备注
	 * @return
	 */
	public String getMemo() {
		return this.memo;
	}
	
	public void setCreatePersion(String createPersion) {
		this.createPersion = createPersion;
	}
	
	/**
	 * 返回 创建人
	 * @return
	 */
	public String getCreatePersion() {
		return this.createPersion;
	}
	
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("managementScope", this.managementScope) 
		.append("code", this.code) 
		.append("memo", this.memo) 
		.append("createPersion", this.createPersion) 
		.append("createDate", this.createDate) 
		.toString();
	}
}