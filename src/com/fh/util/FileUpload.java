package com.fh.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件
 * 创建人：FH 创建时间：2014年12月23日
 * @version
 */
public class FileUpload {

	/**
	 * @param file 			//文件对象
	 * @param filePath		//上传路径
	 * @param fileName		//文件名
	 * @return  文件名
	 */
	public static String fileUp(MultipartFile file, String filePath, String fileName){
		String extName = ""; // 扩展名格式：
		try {
			if (file.getOriginalFilename().lastIndexOf(".") >= 0){
				extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
			}
			//copyFile(file.getInputStream(), filePath, fileName+extName).replaceAll("-", "");
			copyFile(file, filePath, fileName+extName).replaceAll("-", "");
		} catch (IOException e) {
			System.out.println(e);
		}
		return filePath+fileName+extName;
	}
	
	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private static String copyFile(MultipartFile myfile, String dir, String realName)
	//private static String copyFile(InputStream in, String dir, String realName)
			throws IOException {
		File file = new File(dir, realName);
		System.out.println("file:"+file);
		System.out.println("file.getParentFile():"+file.getParentFile());
//		if (!file.exists()) {
//			System.out.println("file.getParentFile():"+file.getParentFile());
//			if (!file.getParentFile().exists()) {
//				file.getParentFile().mkdirs();
//			}
//			file.createNewFile();
//		}
		if(!file.exists()){
			file.mkdirs();
		}
//		FileUtils.copyInputStreamToFile(in, file);
		//利用文件读写把内容写到已经创建好的文件里面
		myfile.transferTo(file);
		return realName;
	}
}
