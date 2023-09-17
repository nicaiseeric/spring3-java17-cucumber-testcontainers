package fr.neads.data.common.queue.features.scanning;

import fr.neads.data.common.QueueDemoApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import fr.neads.data.common.integration.IntegrationEnvTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Testcontainers
@ActiveProfiles("test")
@RunWith(Cucumber.class)
@CucumberContextConfiguration
@CucumberOptions(
        glue = "fr.neads.data.common.queue.features.scanning",
        features = "classpath:features/scanning",
        plugin = {
                "pretty",
                "html:target/build/reports/tests/cucumber/cucumber-report.html"
        }
)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(
        classes = QueueDemoApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
public class QueueServiceScanningCucumberTests extends IntegrationEnvTest {
        /**
         * This class implement a springboot+cucumber test using postgresql db from testcontainers
         */
}
