package fr.neads.data.common.queue;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class QueueServiceSpringBootTestConfig {
    /**
     * Use this class if you need a mock and not a real instance of QueueService
     * @return
     */

    @Bean
    @Primary
    public QueueService queueService() {
       return Mockito.mock(QueueService.class);
    }
}
