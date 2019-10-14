package com.fh.util;

import org.springframework.util.Assert;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Date;

/**
 * 类名称: FilePathUtils <br>
 * 类描述: <br>
 *
 * @author: dongbo.jiao
 * @since: 17/8/13 17:46
 * @version: 1.0.0
 */

public class FileUtils {

	
	
	
	
	/**
     * Check if a file exits.
     *
     * @param fileName The name of the file to check.
     * @return true if file exists.
     */
    public static boolean fileExists( String fileName )
    {
        File file = new File( fileName );
        return file.exists();
    }

    public static String fileRead( String file )
        throws IOException
    {
        return fileRead( new File( file ) );
    }

    public static String fileRead( File file )
        throws IOException
    {
        StringBuffer buf = new StringBuffer();

        FileInputStream in = new FileInputStream( file );

        int count;
        byte[] b = new byte[512];
        while ( ( count = in.read( b ) ) > 0 )  // blocking read
        {
            buf.append( new String( b, 0, count ) );
        }

        in.close();

        return buf.toString();
    }

	
	
	
	
	
	
	
	
	
    /**
     * 从文件名称中解析出后缀名,如果不包含,则返回空字符串
     * @param name
     * @return
     */
    public static String getFileSuffix(String name){
        String result = "";
        Assert.notNull(name,"name should not be null");

        int lastIndex = name.lastIndexOf(".");
        if(-1 == lastIndex){
            return "";
        }
        result = name.substring(lastIndex + 1);
        return result;
    }

    //写入文件，，
    public static int write(String filePath,String data){
        try{
            File file =new File(filePath);
            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }
            //true = append file
            FileWriter fileWritter = new FileWriter(file.getAbsolutePath(),true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.close();

        }catch(IOException e){
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    public static long copy(File f1, File f2) throws Exception{
        long time=new Date().getTime();
        int length=2097152;
        FileInputStream in=new FileInputStream(f1);
        FileOutputStream out=new FileOutputStream(f2);
        FileChannel inC=in.getChannel();
        FileChannel outC=out.getChannel();
        int i=0;
        while(true){
            if(inC.position()==inC.size()){
                inC.close();
                outC.close();
                return new Date().getTime()-time;
            }
            if((inC.size()-inC.position())<20971520)
                length=(int)(inC.size()-inC.position());
            else
                length=20971520;
            inC.transferTo(inC.position(),length,outC);
            inC.position(inC.position()+length);
            i++;
        }
    }
}
