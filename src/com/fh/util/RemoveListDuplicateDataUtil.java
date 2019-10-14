package com.fh.util;

import java.util.List;
/**
 * 去除List<String>集合中的重复数据
 * @author liuchi
 *
 */
public class RemoveListDuplicateDataUtil {
	
	
	public static List<String> removeDuplicateData(List<String> list) {    
		if(list == null || list.isEmpty()) {
			return null;
		}
		for(int i = 0; i < list.size() - 1;i++)  {       
		     for(int j = list.size() - 1 ; j > i;j--)  {       
		           if(list.get(j).equals(list.get(i)))  {       
		              list.remove(j);       
		           }        
		     }        
		}        
		return list;
	}   
}
