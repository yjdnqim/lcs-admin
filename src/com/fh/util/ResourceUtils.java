package com.fh.util;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class ResourceUtils {

    private static ClassLoader classLoader = null;
    private static Logger s_log = Logger.getLogger(ResourceUtils.class);
    static
    {
        classLoader = ResourceUtils.class.getClassLoader();
        if (classLoader == null)
            classLoader = ClassLoader.getSystemClassLoader();
    }
    
	public static String findResource(String rscName)
	{
	    s_log.debug("Trying to find resource [" + rscName + "]");
	    URL rscPath = classLoader.getResource(rscName);
	    if (rscPath == null)
	        throw new RuntimeException("Resource [" + rscName + "] cannot be found.");
	    
	    String absolutePath = null;

        try {
            absolutePath = (new File(rscPath.toURI().normalize())).getAbsolutePath();
        } catch (URISyntaxException e) {
            s_log.error("Failed to locate the file", e);
        }
	    return absolutePath;
	}
	
	public static URL findResourceURL(String rscName)
	{
	    URL rscPath = classLoader.getResource(rscName);
	    return rscPath;
	}
	
	public static InputStream findResourceAsStream(String rscName)
	{
	    InputStream is = classLoader.getResourceAsStream(rscName);
	    return is;
	}
	
	/**
	 * debug method to see what the classpath is 
	 * @return
	 */
	public static URL[] getClashpath()
	{

        URL[] urls = ((URLClassLoader)classLoader).getURLs();
        
        for(URL url: urls){
            s_log.debug(url.getFile());
        }
        return urls;
	}
    
	public static String getFileSuffix(String fileName)
	{
	    int suffixPos = fileName.lastIndexOf('.');
	    if (suffixPos != -1 && !fileName.endsWith("."))
	    {
	        return fileName.substring(suffixPos+1);
	    }
	    else
	    {
	        s_log.warn("File name [" + fileName + "] doesn't have suffix.");
	        return "";
	    }
	}
	
	 public static void scale(InputStream is, String result) 
	 {
			  try {
				   BufferedImage src = ImageIO.read(is); //read file
				  
				   Image image = src.getScaledInstance(320, 230,Image.SCALE_DEFAULT);
				   BufferedImage tag = new BufferedImage(320, 230,BufferedImage.TYPE_INT_RGB);
				   Graphics g = tag.getGraphics();
				   g.drawImage(image, 0, 0, null); //draw the pic of 320*230
				   g.dispose();
				   ImageIO.write(tag, "JPEG", new File(result));//put to file stream
			  }
			  catch (IOException e) {
				  e.printStackTrace();
			  }
	}

}
