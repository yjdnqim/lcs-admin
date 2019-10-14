package com.fh.service.system.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("userLoginLogService")
public class UserLoginLogService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("UserLoginLogMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("UserLoginLogMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("UserLoginLogMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("UserLoginLogMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserLoginLogMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserLoginLogMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("UserLoginLogMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 查询最新5条记录
	 * @author wanliyunbai
	 * @param pd
	 * @return
	 * @throws Exception
	 * @time 2017年5月14日
	 */
	public List<PageData> listLatest5(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("UserLoginLogMapper.listLatest5", pd);
	}
	
	public PageData findCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserLoginLogMapper.findCount", pd);
	}
}

