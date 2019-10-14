package com.fh.configuration;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
public class ConfigurationManager {
    
    private static final Logger s_log = Logger.getLogger(ConfigurationManager.class);

    
    /**
     * NOTE: Should not wire bean into static fields unless absolutely necessary because of performance issue.
     * http://www.connorgarvey.com/blog/?p=105
     * @param configuration
     */
	private Configuration configuration = new Configuration("config.properties") ;

	public Configuration getConfiguration() {
		return configuration;
	}
    

}
