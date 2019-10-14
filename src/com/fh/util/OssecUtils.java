package com.fh.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * ossec工具类
 * @author wanliyunbai
 * @time 2017年5月14日
 * @email wanliyunbai@163.com
 */
public class OssecUtils {
	public final static String OSSEC_HOME = "/var/ossec/";//ossec主目录
	public final static String OSSEC_AGENT_DIR = "/var/ossec/queue/agent-info";
	/**
	 * 代理机器文件超过20分钟没有更新，说明机器已经断开
	 */
	public final static Long AGENT_TIME_OUT = 20 * 60 * 1000L;
	public final static String OSSEC_SYSCHECK_DIR = "/var/ossec/queue/syscheck";
	public final static String ADD_AGENT_COMMAND = "/var/ossec/bin/addagent.sh";
	public final static String GET_AGENT_KEY_COMMAND = "/var/ossec/bin/getAgentKey.sh";
	public final static String DEL_AGENT_COMMAND = "/var/ossec/bin/deleteAgent.sh";
	public final static String RESTART_COMMAND = "/var/ossec/bin/restartServer.sh";
	public final static String USER_CELUE_DIR = "/var/ossec/user-rule";
	public final static String CELUE_TEMPLATE_DIR = "/var/ossec/user-rule/template";
	
	/**
	 * 获取所有的监控计算机列表
	 * @author wanliyunbai
	 * @return
	 * @time 2017年5月18日
	 */
	public static List<Map<String,Object>> getAgents() {
		List<Map<String,Object>> res = new ArrayList<Map<String,Object>>();
		//先获取服务器信息
		Map<String,Object> serverAgent = new HashMap<String, Object>();
		serverAgent.put("NAME", "DUN-server");
		serverAgent.put("IP", "127.0.0.1");
		serverAgent.put("LAST_CONTACT", Tools.date2Str(new Date()));
//		serverAgent.put("OS", getServerOS());
		serverAgent.put("OS", "Linux");
		serverAgent.put("STATUS", "Active");
		res.add(serverAgent);
		//获取客户端代理
		File file = new File(OSSEC_AGENT_DIR);
		if (file.exists()) {
			File[] agentFiles = file.listFiles();
			if (agentFiles != null && agentFiles.length > 0) {
				for (File agent : agentFiles) {
					Map<String,Object> clientAgent = new HashMap<String, Object>();
					String fileName = agent.getName();
					String agentName = fileName.substring(0,fileName.lastIndexOf("-"));
					String ip = fileName.substring(fileName.lastIndexOf("-") + 1);
					if (StringUtils.isBlank(agentName) || StringUtils.isBlank(ip)) {
						continue;
					}
					String lastKeepAlive = Tools.date2Str(new Date(agent.lastModified()));
					String status = "";
					//如果文件修改时间大于20分钟，说明代理已经断开
					if (System.currentTimeMillis() - agent.lastModified() > AGENT_TIME_OUT) {
						status = "Inactive";
					} else {
						status = "Active";
					}
					//获取操作系统配置信息
					String os = getClientAgentOS(agent);
					os = os.replace("ossec", "DUN");
					clientAgent.put("NAME", agentName);
					clientAgent.put("IP", ip);
					clientAgent.put("LAST_CONTACT", lastKeepAlive);
					clientAgent.put("OS", os);
					clientAgent.put("STATUS", status);
					res.add(clientAgent);
				}
			}
		}
		return res;
	}
	
	public static Map<String,Object> getAgentByIp(String ip) {
		if ("127.0.0.1".equals(ip)) {
			Map<String,Object> serverAgent = new HashMap<String, Object>();
			serverAgent.put("NAME", "DUN-server");
			serverAgent.put("IP", "127.0.0.1");
			serverAgent.put("LAST_CONTACT", Tools.date2Str(new Date()));
//		serverAgent.put("OS", getServerOS());
			serverAgent.put("OS", "Linux");
			serverAgent.put("STATUS", "Active");
			return serverAgent;
		}
		//获取客户端代理
		File file = new File(OSSEC_AGENT_DIR);
		if (file.exists()) {
			File[] agentFiles = file.listFiles();
			if (agentFiles != null && agentFiles.length > 0) {
				for (File agent : agentFiles) {
					Map<String,Object> clientAgent = new HashMap<String, Object>();
					String fileName = agent.getName();
					String tmpAgentName = fileName.substring(0,fileName.lastIndexOf("-"));
					String tmpIp = fileName.substring(fileName.lastIndexOf("-") + 1);
					if (StringUtils.isBlank(tmpAgentName) || StringUtils.isBlank(tmpIp)) {
						continue;
					}
					if (!tmpIp.equals(ip)) {
						continue;
					}
					String lastKeepAlive = Tools.date2Str(new Date(agent.lastModified()));
					String status = "";
					//如果文件修改时间大于20分钟，说明代理已经断开
					if (System.currentTimeMillis() - agent.lastModified() > AGENT_TIME_OUT) {
						status = "Inactive";
					} else {
						status = "Active";
					}
					//获取操作系统配置信息
					String os = getClientAgentOS(agent);
					os = os.replace("ossec", "DUN");
					clientAgent.put("NAME", tmpAgentName);
					clientAgent.put("IP", tmpIp);
					clientAgent.put("LAST_CONTACT", lastKeepAlive);
					clientAgent.put("OS", os);
					clientAgent.put("STATUS", status);
					return clientAgent;
				}
			}
		}
		return new HashMap<String, Object>();
	}
	
	/**
	 * 获取系统文件修改记录表
	 * @author wanliyunbai
	 * @param agentName
	 * @return
	 * @time 2017年5月18日
	 */
	public static Map<String,List<Map<String,String>>> getSyscheckInfo(String ip) {
		Map<String,List<Map<String,String>>> result = new HashMap<String,List<Map<String,String>>>();
		File file = new File(OSSEC_SYSCHECK_DIR);
		if (file.exists()) {
			File[] syscheckFiles = file.listFiles();
			if (syscheckFiles != null && syscheckFiles.length > 0) {
				for (File syscheckFile : syscheckFiles) {
					String fileName = syscheckFile.getName();
					if (fileName.startsWith(".")) {
						continue;
					}
					String tmpAgentName = "";
					String tmpIp = "";
					if ("syscheck".equals(fileName)){//说明是ossec服务器
						tmpAgentName = "DUN-server";
						tmpIp = "127.0.0.1";
					} else {//ossec的客户端
						tmpAgentName = fileName.substring(fileName.indexOf("(") + 1,fileName.indexOf(")")).trim();
						tmpIp = fileName.substring(fileName.indexOf(")") + 1,fileName.indexOf("->")).trim();
					}
					if (StringUtils.isNotBlank(ip) && !ip.equals(tmpIp)) {
						//查询指定的计算机代理，其他代理的不用查询
						continue;
					}
					List<Map<String, String>> changeList = parseSyscheckFile(syscheckFile, tmpAgentName, tmpIp);
					if (result.get(tmpIp) != null) {//一个代理多个日志文件的情况
						result.get(tmpIp).addAll(changeList);
					} else {
						result.put(tmpIp, changeList);
					}
				}
			}
			
		}
		return result;
	}
	
	private static List<Map<String,String>> parseSyscheckFile(File syscheckFile,String agentName,String ip) {
		List<Map<String,String>> result = new ArrayList<Map<String,String>>();
		FileReader fr = null;
		BufferedReader reader = null;
		try {
			fr = new FileReader(syscheckFile);
			reader = new BufferedReader(fr);
			String line = "";
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.startsWith("+") || line.startsWith("#")) {
					continue;
				}
				line = line.substring(line.lastIndexOf(" !"));
				String reg = "^\\s\\!(\\d+) (.+)$";
				Pattern pattern = Pattern.compile(reg);
				Matcher matcher = pattern.matcher(line);
				if(matcher.find()) {
					Long lastModTime = Long.parseLong(matcher.group(1));
					String lastModTimeStr = Tools.date2Str(new Date(lastModTime * 1000));
					String tmpFileName = matcher.group(2);
					Map<String,String> modifyFile = new HashMap<String, String>();
					modifyFile.put("agentName", agentName);
					modifyFile.put("ip", ip);
					modifyFile.put("timestamp", lastModTimeStr);
					modifyFile.put("filename", tmpFileName);
					result.add(modifyFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 获取ossec服务端操作系统信息
	 * @author wanliyunbai
	 * @return
	 * @time 2017年6月20日
	 */
	public static String getServerOS() {
		return executeShell("/bin/uname -a");
	}
	
	/**
	 * 添加计算机
	 * @author wanliyunbai
	 * @param ip
	 * @param agentName
	 * @time 2017年5月29日
	 */
	public static String addAgent(String ip,String agentName) {
		//给添加代理脚本授权
		String command = "chmod 777 " + ADD_AGENT_COMMAND;
		executeShell(command);
		//执行添加脚本命令
		command = ADD_AGENT_COMMAND + " " + ip + " " + agentName;
		String resp = executeShell(command);
		if (StringUtils.isNotBlank(resp)) {
			resp = resp.substring(resp.indexOf("ID:") + 3);
			resp = resp.substring(0, resp.indexOf("Name:"));
			resp = resp.trim();
		}
		return resp;
	}
	
	/**
	 * 删除代理
	 * @author wanliyunbai
	 * @param agentId
	 * @time 2017年5月29日
	 */
	public static void delAgent(String agentId) {
		String command = "chmod 777 " + DEL_AGENT_COMMAND;
		executeShell(command);
		command = DEL_AGENT_COMMAND + " " + agentId;
		executeShell(command);
	}
	
	/**
	 * 获取客户端key
	 * @author wanliyunbai
	 * @param agentId
	 * @return
	 * @time 2017年5月29日
	 */
	public static String getAgentKey(String agentId) {
		String command = "chmod 777 " + GET_AGENT_KEY_COMMAND;
		executeShell(command);
		command = GET_AGENT_KEY_COMMAND + " " + agentId;
		String resp = executeShell(command);
		String result = "";
		if (StringUtils.isNotBlank(resp) && resp.contains("Agent key information for")) {
			resp = resp.substring(resp.indexOf("is:") + 3);
			resp = resp.substring(0,resp.indexOf("** Press ENTER to return to the main menu."));
			resp = resp.replace("\n", "");
			resp = resp.trim();
			result = resp;
		}
		return result;
	}
	
	/**
	 * 重启Server
	 * @author wanliyunbai
	 * @time 2017年5月29日
	 */
	public static void restartServer() {
		String command = "chmod 777 " + RESTART_COMMAND;
		executeShell(command);
		command = RESTART_COMMAND;
		executeShell(command);
	}
	
	public static String executeShell(String command) {
		StringBuffer sb = new StringBuffer();  
	    try {  
            Process ps = Runtime.getRuntime().exec(command);  
            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));  
            String line;  
            while ((line = br.readLine()) != null) {  
                sb.append(line).append("\n");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
	    return sb.toString();
	}
	
	private static String getClientAgentOS(File file) {
		String name = "";
		FileReader fr = null;
		BufferedReader reader = null;
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			String line = "";
			while ((line = reader.readLine()) != null) {
				name += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return name;
	}
	
	public static void replaceShowName(List<PageData> pdList) {
		if (pdList != null && pdList.size() > 0) {
			for (PageData pd : pdList) {
				String description = pd.getString("description");
				String full_log = pd.getString("full_log");
				if (StringUtils.isNotBlank(description)) {
					description = description.replace("ossec", "DUN");
					description = description.replace("Ossec", "DUN");
					pd.put("description", description);
				}
				if (StringUtils.isNotBlank(full_log)) {
					full_log = full_log.replace("ossec", "DUN");
					full_log = full_log.replace("Ossec", "DUN");
					pd.put("full_log", full_log);
				}
			}
		}
	}
	
	/**
	 * 获取策略类别
	 * @author wanliyunbai
	 * @param id 策略ID
	 * @return
	 * @throws IOException 
	 * @time 2017年6月3日
	 */
	public static List<PageData> getCelueCat(String id) throws IOException {
		List<PageData> resultList = new ArrayList<PageData>();
		//在自定义策略目录(user-rule)中template目录下放置的是模板，已策略id为目录的为该策略下的文件
		//根据策略ID找到对应目录，如果目录下有策略文件，遍历目录，返回策略分类，如果没有该目录或者文件，从模板中拷贝一份过来
		String userCeluePath = USER_CELUE_DIR + File.separator + id;
		File userCelueDir = new File(userCeluePath);
		//如果规则文件不存在，从模板里面拷贝规则到策略id目录下
		if (!userCelueDir.exists()) {
			intCelueFile(id);
		} else if (userCelueDir.list() == null || userCelueDir.list().length == 0) {
			intCelueFile(id);
		}
		File[] listFiles = userCelueDir.listFiles();
		if (listFiles != null && listFiles.length > 0) {
			for (File ruleFile : listFiles) {
				PageData pd = new PageData();
				pd.put("FILE_NAME", ruleFile.getName());
				pd.put("FILE_PATH", ruleFile.getAbsolutePath());
				resultList.add(pd);
			}
		}
		return resultList;
	}
	
	/**
	 * 初始化策略
	 * @author wanliyunbai
	 * @param id 策略ID
	 * @throws IOException 
	 * @time 2017年6月3日
	 */
	public static void intCelueFile(String id) throws IOException {
		String templatePath = USER_CELUE_DIR + File.separator + "template";
		File templateFile = new File(templatePath);
		String userCeluePath = USER_CELUE_DIR + File.separator + id;
		File userCelueDir = new File(userCeluePath);
		File[] listFiles = templateFile.listFiles();
		for (File templateRule : listFiles) {
			FileUtils.copyFileToDirectory(templateRule, userCelueDir);
		}
	}
	
	/**
	 * 通过路径获取规则信息,包含规则是否被打开
	 * @author wanliyunbai
	 * @param filePath
	 * @return
	 * @throws Exception 
	 * @time 2017年6月3日
	 */
	public static List<PageData> getRuleConfByPath (String filePath) throws Exception {
		File userRuleFile = new File(filePath);
		File templateFile = new File(CELUE_TEMPLATE_DIR + File.separator + userRuleFile.getName());
		List<PageData> templateRule = getRuleByPath(templateFile);//模板列表
		List<PageData> userRule = getRuleByPath(userRuleFile);//自定义规则
		Map<String,PageData> userRuleMap = new HashMap<String, PageData>();
		for (PageData pd : userRule) {
			userRuleMap.put(pd.getString("rule_id"), pd);
		}
		for (PageData pd : templateRule) {
			if (userRuleMap.get(pd.get("rule_id")) != null) {
				pd.put("status", "1");
			} else {
				pd.put("status", "0");
			}
		}
		return templateRule;
	}
	
	/**
	 * 更新规则文件，如果是打开规则，需要从模板中获取规则，并更新到当前文件中
	 * 如果是关闭规则，直接从该模板文件中删除规则即可并保存即可
	 * @author wanliyunbai
	 * @param filePath
	 * @param ruleId
	 * @param openFlag true表示打开，false表示关闭
	 * @time 2017年6月3日
	 */
	public static void updateRuleFile(String filePath,String groupName,String ruleId,boolean openFlag) throws Exception {
		File userRuleFile = new File(filePath);
		if (openFlag) {//需要打开规则，也就是本文件中规则之前被删除了，需要从模板文件中获取到该规则
			File templateFile = new File(CELUE_TEMPLATE_DIR + File.separator + userRuleFile.getName());
			Document templateDoc = getDocument(templateFile);
			Element templateRule = (Element)templateDoc.selectSingleNode("/root/group/rule[@id='" + ruleId + "']");
			Document userDoc = getDocument(userRuleFile);
			Element userGroup = (Element)userDoc.selectSingleNode("/root/group[@name='" + groupName + "']");
			userGroup.add(templateRule.createCopy());
			saveDocument(userRuleFile,userDoc);
		} else {//需要关闭规则，直接删除当前文件中的规则即可
			Document userDoc = getDocument(userRuleFile);
			Element userRule = (Element)userDoc.selectSingleNode("/root/group/rule[@id='" + ruleId + "']");
			userRule.getParent().remove(userRule);
			saveDocument(userRuleFile,userDoc);
		}
	}
	
	public static void saveDocument(File file,Document doc) throws Exception{
		FileWriter fw = null;
		try {
			fw = new FileWriter(file,false);
			String value = doc.asXML();
			value = value.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n", "");
			value = value.replace("<root>", "");
			value = value.replace("</root>", "");
			fw.write(value);
			fw.close();
		} catch(Exception e) {
			throw e;
		} finally {
			if (fw!=null) {
				fw.close();
			}
		}
	}
	
	public static Document getDocument(File filePath) throws Exception {
		SequenceInputStream seqStream = null;
		Document document = null;
		try {
			InputStream is = new FileInputStream(filePath);
			Enumeration<InputStream> streams = Collections
					.enumeration(Arrays.asList(new InputStream[] { new ByteArrayInputStream("<root>".getBytes()), is,
							new ByteArrayInputStream("</root>".getBytes()), }));
			seqStream = new SequenceInputStream(streams);
			// 使用xml解析器进行解析
			SAXReader reader = new SAXReader();
			document = reader.read(seqStream);
		} catch (Exception e) {
			throw e;
		} finally {
			if (seqStream != null) {
				seqStream.close();
			}
		}
		return document;
	}
	
	/**
	 * 解析xml获取规则信息
	 * @author wanliyunbai
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException 
	 * @throws DocumentException 
	 * @time 2017年6月3日
	 */
	public static List<PageData> getRuleByPath(File filePath) throws Exception {
		List<PageData> resultList = new ArrayList<PageData>();
		SequenceInputStream seqStream = null;
		try {
			InputStream is = new FileInputStream(filePath);
			Enumeration<InputStream> streams = Collections
					.enumeration(Arrays.asList(new InputStream[] { new ByteArrayInputStream("<root>".getBytes()), is,
							new ByteArrayInputStream("</root>".getBytes()), }));
			seqStream = new SequenceInputStream(streams);
			// 使用xml解析器进行解析
			SAXReader reader = new SAXReader();
			Document document = reader.read(seqStream);
			Element rootElement = document.getRootElement();
			//获取所有的group组
			List<Element> groupList = rootElement.elements("group");
			if (groupList != null) {
				for (Element group : groupList) {
					//获取该组下的所有规则
					List<Element> ruleList = group.elements("rule");
					if (ruleList != null) {
						for (Element rule : ruleList) {
							PageData pd = new PageData();
							pd.put("rule_id", rule.attributeValue("id"));
							pd.put("level", rule.attributeValue("level"));
							pd.put("description", rule.element("description").getText());
							pd.put("group", group.attributeValue("name"));
							resultList.add(pd);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (seqStream != null) {
				seqStream.close();
			}
		}
		return resultList;
	}
}
