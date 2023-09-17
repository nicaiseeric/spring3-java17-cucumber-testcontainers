package fr.neads.data.common;

import fr.neads.data.common.configuration.AppPropertiesEventListener;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Locale;

// see springboot multi-module sample here -> https://github.com/spring-guides/gs-multi-module/tree/main/complete
@SpringBootApplication(scanBasePackages = "fr.neads.data")
@Import(AppPropertiesEventListener.class)
@EnableJpaRepositories(
        basePackages = {
                "fr.neads.data.common.queue"
        }
)
@EntityScan(
        basePackages = {
                "fr.neads.data.common.queue"
        }
)
public class QueueDemoApplication {
    public static void main( String[] args ) {
        Locale.setDefault(new Locale("EN", "MG"));
        new SpringApplicationBuilder(QueueDemoApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
