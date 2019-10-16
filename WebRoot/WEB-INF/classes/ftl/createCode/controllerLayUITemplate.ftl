package com.fh.controller.${packageName}.${objectNameLower};

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.service.${packageName}.${objectNameLower}.${objectName}Service;

/** 
 * 类名称：${objectName}Controller
 * 创建人：Lcs-Admin
 * 创建时间：${nowDate?string("yyyy-MM-dd")}
 */
@Controller
@RequestMapping(value="/${objectNameLower}")
public class ${objectName}Controller extends BaseController {
	
	@Resource(name="${objectNameLower}Service")
	private ${objectName}Service ${objectNameLower}Service;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(){
		logBefore(logger, "新增${objectName}");
		PageData resultPageData = new PageData();
		PageData pd = this.getPageData();
		pd.put("${objectNameUpper}_ID", this.get32UUID());	//主键
<#list fieldList as var>
	<#if var[3] == "否">
		<#if var[1] == 'Date'>
		pd.put("${var[0]}", Tools.date2Str(new Date()));	//${var[2]}
		<#else>
		pd.put("${var[0]}", "${var[4]?replace("无","")}");	//${var[2]}
		</#if>
	</#if>
</#list>

		try {
			${objectNameLower}Service.save(pd);
			resultPageData.put("code", 0);
			resultPageData.put("message", "OK");
		} catch (Exception e) {
			resultPageData.put("code", -1);
			resultPageData.put("message", e.getLocalizedMessage());
		}
		return AppUtil.returnObject(resultPageData, resultPageData);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(){
		logBefore(logger, "删除${objectName}");
		PageData resultPageData = new PageData();
		PageData pd = this.getPageData();
		try{
			${objectNameLower}Service.delete(pd);
			resultPageData.put("code", 0);
			resultPageData.put("message", "OK");
		} catch(Exception e){
			resultPageData.put("code", -1);
			resultPageData.put("message", e.getLocalizedMessage());
		}
		return AppUtil.returnObject(resultPageData, resultPageData);
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	@ResponseBody
	public Object edit(){
		logBefore(logger, "修改${objectName}");
		PageData resultPageData = new PageData();
		PageData pd = this.getPageData();
		try{
			${objectNameLower}Service.edit(pd);
			resultPageData.put("code", 0);
			resultPageData.put("message", "OK");
		} catch(Exception e){
			resultPageData.put("code", -1);
			resultPageData.put("message", e.getLocalizedMessage());
		}
		return AppUtil.returnObject(resultPageData, resultPageData);
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表${objectName}");
		ModelAndView mv = this.getModelAndView();
		try{
			PageData pd = this.getPageData();
			page.setPd(pd);

			List<PageData>	varList = ${objectNameLower}Service.list(page);	//列出${objectName}列表
			mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_list");
			mv.addObject("varList", varList);

			pd.put("totalCount", page.getTotalResult());
			pd.put("pageCount", page.getShowCount());
			pd.put("currentPage", page.getCurrentPage());
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增${objectName}页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改${objectName}页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = ${objectNameLower}Service.findById(pd);	//根据ID读取
			mv.setViewName("${packageName}/${objectNameLower}/${objectNameLower}_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除${objectName}");
		PageData resultPageData = new PageData();
		try {
			PageData pd = this.getPageData();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				${objectNameLower}Service.deleteAll(ArrayDATA_IDS);
				resultPageData.put("code", 0);
				resultPageData.put("message", "OK");
			}else{
				resultPageData.put("code", -2);
				resultPageData.put("message", "缺少参数");
			}
		} catch (Exception e) {
			resultPageData.put("code", -1);
			resultPageData.put("message", e.getLocalizedMessage());
		}
		return AppUtil.returnObject(resultPageData, resultPageData);
	}
	
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出${objectName}到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
	<#list fieldList as var>
			titles.add("${var[2]}");	//${var_index+1}
	</#list>
			dataMap.put("titles", titles);
			List<PageData> varOList = ${objectNameLower}Service.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
	<#list fieldList as var>
			<#if var[1] == 'Integer'>
				vpd.put("var${var_index+1}", varOList.get(i).get("${var[0]}").toString());	//${var_index+1}
			<#else>
				vpd.put("var${var_index+1}", varOList.get(i).getString("${var[0]}"));	//${var_index+1}
			</#if>
	</#list>
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
