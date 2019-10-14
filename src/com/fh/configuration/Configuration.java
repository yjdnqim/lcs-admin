package com.fh.configuration;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.fh.util.ResourceUtils;

public class Configuration {
	
	//生成的表名的前缀（默认为TB）此数据不能删除
	public static final String TABLE_PREFIX_NAME = "TABLE_PREFIX_NAME";
	
	
	private static final Logger s_log = Logger.getLogger(Configuration.class);

    // immutable, config properties should be read-only after initialization
    private final Map<Object, Object> configProps; 


	public Configuration(String configFileName)
    {
        File configFile = new File(ResourceUtils.findResource(configFileName));
        FileInputStream is=null;
        Properties properties = new Properties();
        if (!configFile.exists())
        {
            s_log.error("Fatal error, configuration file [" + configFile
                    + "] does not exist.");
            System.exit(-1);
        }

        try
        {
        	is=new FileInputStream(configFile);
            properties.load(is);
            is.close();
        }
        catch (IOException e)
        {
            s_log.error("Failed to initialize the configuration properties: "
                    + e.getMessage());
        }
        
        if (properties.getProperty(TABLE_PREFIX_NAME) == null)
        {
            s_log.fatal("Fatal error, TABLE_PREFIX_NAME is not configured on config.properties");
            System.exit(-1);
        }
        
        // making a immutable map out of the config properties
        configProps = Collections.unmodifiableMap(properties);
    }
    
	public String getTablePrefixName() {
    	String retVal = (String) configProps.get(TABLE_PREFIX_NAME);
        s_log.debug("TABLE_PREFIX_NAME is: " + retVal);
		return retVal;
	}
	
    /**
     * return the property value for the given property name
     * 
     * @param name
     *            The name of the property to retrieve
     * @return Value of the property
     */
    public String getProperty(String name)
    {
        String retVal = (String) configProps.get(name);
        s_log.debug("Property value for [" + name + "] is: " + retVal);
        return retVal;
    }

}
