package com.winway.scm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.winway.scm.persistence.manager.ScmXsBigContractProductManager;
import com.winway.scm.model.ScmXsAgreementSummary;
import com.winway.scm.model.ScmXsBigContract;
import com.winway.scm.model.ScmXsBigContractAllotProduct;
import com.winway.scm.model.ScmXsBigContractProduct;
import com.hotent.base.annotation.Workflow;
import com.hotent.base.controller.BaseController;
import com.hotent.base.model.CommonResult;
import com.hotent.base.model.WinwayResult;
import com.hotent.base.query.PageList;
import com.hotent.base.query.QueryFilter;
import com.hotent.base.util.StringUtil;

/**
 * 
 * <pre> 
 * 描述：大合同商品表 控制器类
 * 构建组：x7
 * 作者:原浩
 * 邮箱:PRD-jun.he@winwayworld.com
 * 日期:2019-01-09 09:35:44
 * 版权：美达开发小组
 * </pre>
 */
@RestController
@RequestMapping(value="/scm/scmXsBigContractProduct/v1",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(tags="scmXsBigContractProductController 大合同商品表")
public class ScmXsBigContractProductController extends BaseController{
	@Resource
	ScmXsBigContractProductManager scmXsBigContractProductManager;
	
	/**
	 * 大合同商品表列表(分页条件查询)数据
	 * @param request
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@PostMapping("/list")
	@ApiOperation(value="大合同商品表数据列表", httpMethod = "POST", notes = "获取大合同商品表列表")
	public PageList<ScmXsBigContractProduct> list(@ApiParam(name="queryFilter",value="查询对象")@RequestBody QueryFilter queryFilter) throws Exception{
		return scmXsBigContractProductManager.query(queryFilter);
	}
	/**
	 * 大合同分配商品表列表(分页条件查询)数据
	 * @param request
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@PostMapping("/groupByList")
	@ApiOperation(value="大合同商品列表(根据商业,供应商分组合并数据返回)", httpMethod = "POST", notes = "要保证同供应商,同经销商下多个大合同的商品数据全部展示")
	public PageList<ScmXsBigContractProduct> groupByList(@ApiParam(name="queryFilter",value="查询对象")@RequestBody QueryFilter queryFilter) throws Exception{
		return  scmXsBigContractProductManager.groupByList(queryFilter);
	}
	/**
	 * 大合同商品表明细页面
	 * @param id
	 * @return
	 * @throws Exception 
	 * ModelAndView
	 */
	@GetMapping(value="/get/{id}")
	@ApiOperation(value="大合同商品表数据详情",httpMethod = "GET",notes = "大合同商品表数据详情")
	public ScmXsBigContractProduct get(@ApiParam(name="id",value="业务对象主键", required = true)@PathVariable String id) throws Exception{
		return scmXsBigContractProductManager.get(id);
	}
    /**
	 * 新增大合同商品表
	 * @param scmXsBigContractProduct
	 * @throws Exception 
	 * @return
	 * @exception 
	 */
	@PostMapping(value="save")
	@ApiOperation(value = "新增,更新大合同商品表数据", httpMethod = "POST", notes = "新增,更新大合同商品表数据")
	public CommonResult<String> save(@ApiParam(name="scmXsBigContractProduct",value="大合同商品表业务对象", required = true)@RequestBody ScmXsBigContractProduct scmXsBigContractProduct) throws Exception{
		String msg = "添加大合同商品表成功";
		if(StringUtil.isEmpty(scmXsBigContractProduct.getId())){
			scmXsBigContractProductManager.create(scmXsBigContractProduct);
		}else{
			scmXsBigContractProductManager.update(scmXsBigContractProduct);
			 msg = "更新大合同商品表成功";
		}
		return new CommonResult<String>(msg);
	}
	
	/**
	 * 删除大合同商品表记录
	 * @param id
	 * @throws Exception 
	 * @return
	 * @exception 
	 */
	@DeleteMapping(value="remove/{id}")
	@ApiOperation(value = "删除大合同商品表记录", httpMethod = "DELETE", notes = "删除大合同商品表记录")
	public  CommonResult<String>  remove(@ApiParam(name="id",value="业务主键", required = true)@PathVariable String id) throws Exception{
		scmXsBigContractProductManager.remove(id);
		return new CommonResult<String>(true, "删除成功");
	}
	
	/**
	 * 批量删除大合同商品表记录
	 * @param ids
	 * @throws Exception 
	 * @return
	 * @exception 
	 */
	@DeleteMapping(value="/removes")
	@ApiOperation(value = "批量删除大合同商品表记录", httpMethod = "DELETE", notes = "批量删除大合同商品表记录")
	public CommonResult<String> removes(@ApiParam(name="ids",value="业务主键数组,多个业务主键之间用逗号分隔", required = true)@RequestParam String...ids) throws Exception{
		scmXsBigContractProductManager.removeByIds(ids);
		return new CommonResult<String>(true, "批量删除成功");
	}
	/**
	 * 大合同申请页获取药品信息
	 * @param request
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@PostMapping("/bigcontractProductList")
	@ApiOperation(value="大合同申请页获取药品信息", httpMethod = "POST", notes = "大合同申请页获取药品信息,query必须添加协议总表id（agreementSummaryId）进行筛选")
	public PageList<ScmXsBigContractProduct> bigcontractProductList(@ApiParam(name="queryFilter",value="查询对象")@RequestBody QueryFilter queryFilter) throws Exception{
		return scmXsBigContractProductManager.bigcontractProductList(queryFilter);
	}


	/**
	 * 日常合同同申请页获取药品信息
	 * @param
	 * @return
	 * @throws Exception
	 * PageJson
	 * @exception
	 */
	@PostMapping("/everyDayContractProductList")
	@ApiOperation(value="日常同申请页获取药品信息", httpMethod = "POST", notes = "日常同申请页获取药品信息,query必须添加协议总表id（agreementSummaryId，commerceFirstId）进行筛选")
	public PageList<ScmXsBigContractProduct> everyDayContractProductList(@ApiParam(name="queryFilter",value="查询对象")@RequestBody QueryFilter queryFilter) throws Exception{
		return scmXsBigContractProductManager.everyDayContractProductList(queryFilter);
	}


	/**
	 *  大合同分配 / 大合同商品表统计列表,提供给分配使用,按照供应商ID分组
	 * @param request
	 * @return
	 * @throws Exception 
	 * PageJson
	 * @exception 
	 */
	@PostMapping("/groupList")
	@ApiOperation(value="大合同分配 / 大合同商品表统计列表", httpMethod = "POST", notes = "大合同分配 / 大合同商品表统计列表,提供给分配使用,按照供应商ID分组")
	public PageList<ScmXsBigContractProduct> groupList (@ApiParam(name="queryFilter",value="查询对象")@RequestBody QueryFilter queryFilter) throws Exception{
		return scmXsBigContractProductManager.groupList(queryFilter);
	}
}
