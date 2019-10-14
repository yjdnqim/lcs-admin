package com.fh.service.screen.serialportdatalog;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("serialportdatalogService")
public class SerialPortDataLogService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("SerialPortDataLogMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("SerialPortDataLogMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("SerialPortDataLogMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SerialPortDataLogMapper.datalistPage", page);
	}
	
	/*
	*收发数据日志列表
	*/
	public List<PageData> getLogByDataType(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SerialPortDataLogMapper.getLogByDataType", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SerialPortDataLogMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SerialPortDataLogMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("SerialPortDataLogMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findSensor(PageData pd)throws Exception{
		return (PageData)dao.findForObject("SensorMapper.findSensor", pd);
	}
	
}

