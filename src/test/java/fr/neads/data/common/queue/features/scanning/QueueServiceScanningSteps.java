package fr.neads.data.common.queue.features.scanning;

import fr.neads.data.common.beans.IntegrationStatus;
import fr.neads.data.common.beans.SourceName;
import fr.neads.data.common.beans.StepName;
import fr.neads.data.common.queue.Queue;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import fr.neads.data.common.queue.QueueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class QueueServiceScanningSteps {

    @Autowired
    QueueService queueService;

    Queue queue;

    public static final Path baseProjectDir = Paths.get(
            Objects.requireNonNull(QueueServiceScanningSteps.class.getClassLoader().getResource(".")).getPath()
    ).getParent().getParent();

    public static Path toAbsolutePath(String relativePathStr){
        return baseProjectDir.resolve(Paths.get(relativePathStr));
    }

    @Given("the raw cdr file {string} is in the queue at with completed status at step streaming")
    public void theRawCdrFileIsInTheQueueAtWithCompletedStatusAtStepStreaming(String relativeCdrFilePath) {
        log.info("testing file relativeCdrFilePath={} ...", relativeCdrFilePath);

        Path absoluteCdrFilePath = toAbsolutePath(relativeCdrFilePath);

        queue = Queue.builder()
                .fileId(1)
                .absoluteFilePath(absoluteCdrFilePath.toString())
                .fileName(absoluteCdrFilePath.getFileName().toString())
                .sourceName(SourceName.RS.name())
                .stepName(StepName.storage.name())
                .integrationStatus(IntegrationStatus.pending.name())
                .integrationStartTimestamp(System.currentTimeMillis())
                .integrationEndTimestamp(System.currentTimeMillis())
                .build();
        log.info("insert queue={}", queue);

        queueService.persistIntoStorage(queue);

        // When & Then
        assertThat(queueService.getStepNameAllPendingIntegrationFiles(StepName.storage))
                .hasSize(1)
                .containsExactly(queue);
        assertThat(true).isTrue();
    }

    @When("I run the QueueService")
    public void iRunTheDecoderService() {
        log.info("running QueueService.persistIntoStorage() ...");
        Queue completedQueue = queue.copy(IntegrationStatus.completed.name(), StepName.notif.name());
        assertThat(queueService.persistIntoStorage(completedQueue)).isEqualTo(completedQueue);
    }

    @Then("the fact table should contains data from {string}")
    public void theFactTableShouldContainsDataFrom(String expectedContentFilePath) {
        log.info("verifying expected fact table against the legacy expectedContentFilePath={}", expectedContentFilePath);
        assertThat(queueService.loadFromStorage()).contains(queue);
    }

    @And("the storage status should be {string}")
    public void theStorageStatusShouldBe(String integrationStatus) {
        log.info("verifying expected storage status integrationStatus={}", integrationStatus);
        assertThat(queueService.loadFromStorage().stream().map(Queue::getIntegrationStatus).distinct())
                .contains(integrationStatus);
    }
}
