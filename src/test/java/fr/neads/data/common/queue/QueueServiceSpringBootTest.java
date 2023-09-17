package fr.neads.data.common.queue;

import lombok.extern.slf4j.Slf4j;
import fr.neads.data.common.QueueDemoApplication;
import fr.neads.data.common.integration.IntegrationEnvTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Testcontainers
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(classes = QueueDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class QueueServiceSpringBootTest extends IntegrationEnvTest {
    /**
     * This class implement a simple springboot test using postgresql db from testcontainers
     */
    @Autowired
    public QueueService queueService;


    @BeforeEach
    void setUp() {
        log.info("cleaning up the queue");
        queueService.queueRepository.deleteAll();
    }


    @Test
    @Order(1)
    void should_be_able_to_save_one_element_in_the_queue() {
        // Given
        final var queue = Queue.builder()
                .fileId(1)
                .absoluteFilePath("/fake/path/info.gz")
                .fileName("info.gz")
                .sourceName("NET")
                .stepName("storage")
                .integrationStatus("pending")
                .integrationStartTimestamp(System.currentTimeMillis())
                .build();
        log.info("saving queue={}", queue);

        queueService.persistIntoStorage(List.of(queue));

        // When & Then
        assertThat(queueService.getAllPendingIntegrationFiles())
                .hasSize(1)
                .containsExactly(queue)
        ;

    }

}
