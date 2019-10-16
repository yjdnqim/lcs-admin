package com.fh.controller.template.buildcodetest;

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
import com.fh.service.template.buildcodetest.BuildCodeTestService;

/** 
 * 类名称：BuildCodeTestController
 * 创建人：Lcs-Admin
 * 创建时间：2019-10-16
 */
@Controller
@RequestMapping(value="/buildcodetest")
public class BuildCodeTestController extends BaseController {
	
	@Resource(name="buildcodetestService")
	private BuildCodeTestService buildcodetestService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	@ResponseBody
	public Object save(){
		logBefore(logger, "新增BuildCodeTest");
		PageData resultPageData = new PageData();
		PageData pd = this.getPageData();
		pd.put("BUILDCODETEST_ID", this.get32UUID());	//主键

		try {
			buildcodetestService.save(pd);
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
		logBefore(logger, "删除BuildCodeTest");
		PageData resultPageData = new PageData();
		PageData pd = this.getPageData();
		try{
			buildcodetestService.delete(pd);
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
		logBefore(logger, "修改BuildCodeTest");
		PageData resultPageData = new PageData();
		PageData pd = this.getPageData();
		try{
			buildcodetestService.edit(pd);
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
		logBefore(logger, "列表BuildCodeTest");
		ModelAndView mv = this.getModelAndView();
		try{
			PageData pd = this.getPageData();
			page.setPd(pd);

			List<PageData>	varList = buildcodetestService.list(page);	//列出BuildCodeTest列表
			mv.setViewName("template/buildcodetest/buildcodetest_list");
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
		logBefore(logger, "去新增BuildCodeTest页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("template/buildcodetest/buildcodetest_edit");
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
		logBefore(logger, "去修改BuildCodeTest页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = buildcodetestService.findById(pd);	//根据ID读取
			mv.setViewName("template/buildcodetest/buildcodetest_edit");
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
		logBefore(logger, "批量删除BuildCodeTest");
		PageData resultPageData = new PageData();
		try {
			PageData pd = this.getPageData();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				buildcodetestService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出BuildCodeTest到excel");
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("字段一");	//1
			titles.add("字段二");	//2
			titles.add("字段3");	//3
			dataMap.put("titles", titles);
			List<PageData> varOList = buildcodetestService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("F1"));	//1
				vpd.put("var2", varOList.get(i).get("F2").toString());	//2
				vpd.put("var3", varOList.get(i).getString("F3"));	//3
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
