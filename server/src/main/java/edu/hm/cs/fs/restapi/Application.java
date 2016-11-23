package edu.hm.cs.fs.restapi;

import edu.hm.cs.fs.restapi.parser.cache.*;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This is the entry point class for the application.
 *
 * @author Fabio
 */
@EnableAutoConfiguration
@SpringBootApplication
@EnableSwagger2
public class Application extends SpringBootServletInitializer {

    private final static Logger LOG = Logger.getLogger(Application.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     * The main class is called to start the application.
     *
     * @param args could be filled with parameters for spring.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);

        /*CacheUpdater.execute(() -> {
            try {
                LOG.info("Start updating cache files");

                CachedPersonParser.getInstance().updateCache();
                CachedModuleParser.getInstance().updateCache();
                CachedBlackboardParser.getInstance().updateCache();
                CachedOccupiedParser.getInstance().updateCache();
                CachedExamParser.getInstance().updateCache();


                LOG.info("Finished updating cache files");
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        });*/
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex("/rest/api/1.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API of fs.cs.hm.edu")
                .description("The official REST API from the Student Council 07 of the University of applied Science Munich")
                        //.termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
                .contact("Student Council 07")
                        //.license("Apache License Version 2.0")
                        //.licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("0.9.5")
                .build();
    }
}
