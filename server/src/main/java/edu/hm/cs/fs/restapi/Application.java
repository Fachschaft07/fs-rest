package edu.hm.cs.fs.restapi;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.hm.cs.fs.restapi.parser.cache.CacheUpdater;
import edu.hm.cs.fs.restapi.parser.cache.CachedBlackboardParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedModuleParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedOccupiedParser;
import edu.hm.cs.fs.restapi.parser.cache.CachedPersonParser;

/**
 * This is the entry point class for the application.
 *
 * @author Fabio
 */
@SpringBootApplication
public class Application {
  
    private final static Logger logger = Logger.getLogger(Application.class);
  
    /**
     * The main class is called to start the application.
     *
     * @param args could be filled with parameters for spring.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
        
        CacheUpdater.execute(new Runnable() {
          
          @Override
          public void run() {
            try {
              logger.info("Start updating cache files");
              
              CachedPersonParser.getInstance().updateCache();
              CachedModuleParser.getInstance().updateCache();
              CachedBlackboardParser.getInstance().updateCache();
              CachedOccupiedParser.getInstance().updateCache();
              
              
              logger.info("Finished updating cache files");
            } catch(Exception e){
              logger.error(e.getMessage(), e);
            }
          }
        });
        
    }
}
