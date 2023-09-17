package fr.neads.data.common.queue;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import fr.neads.data.common.beans.IntegrationStatus;
import fr.neads.data.common.beans.SourceName;
import fr.neads.data.common.beans.StepName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static fr.neads.data.common.beans.IntegrationStatus.*;

@Slf4j
@Service
@Configuration
public class QueueService {

    @Autowired
    public QueueRepository queueRepository;

    @Value("${cdr.source.name:unknown}")
    private String sourceNameStr;

    private SourceName sourceName;
    @PostConstruct
    private void initSourceName(){
        this.sourceName = SourceName.valueOf(sourceNameStr.toUpperCase());
    }

    public boolean exists(Queue queue){
        return queueRepository.exists(Example.of(queue));
    }

    public Queue getQueueEntriesByFileId(Integer fileId){
        return queueRepository.findByFileId(fileId);
    }
    public List<Queue> getQueueEntriesByFileName(String filename){
        return queueRepository.findByFileName(filename);
    }
    public List<Queue> getQueueEntriesByStepName(StepName stepName) { return queueRepository.findByStepName(stepName.name()); }
    public List<Queue> getQueueEntriesBySourceName(SourceName sourceName) { return queueRepository.findBySourceName(sourceName.name()); }
    public List<Queue> getQueueEntriesByIntegrationStatusAndStepName(IntegrationStatus status, StepName stepName){
        return queueRepository.findByIntegrationStatusAndStepName(status.name(), stepName.name());
    }
    public List<Queue> getQueueEntriesByIntegrationStatusAndSourceName(IntegrationStatus status, SourceName sourceName){
        return queueRepository.findByIntegrationStatusAndSourceName(status.name(), sourceName.name());
    }
    public List<Queue> getDefaultSourceNameAllQueueEntriesByStepName(StepName stepName){
        return queueRepository.findByStepNameAndSourceName(stepName.name(), sourceName.name());
    }
    public List<Queue> getDefaultSourceNameAllQueueEntriesByStepName(StepName stepName, SourceName sourceName){
        return queueRepository.findByStepNameAndSourceName(stepName.name(), sourceName.name());
    }
    public List<Queue> getQueueEntriesByIntegrationStatusAndStepNameAndSourceName(IntegrationStatus status, StepName stepName, SourceName sourceName){
        return queueRepository.findByIntegrationStatusAndStepNameAndSourceName(status.name(), stepName.name(), sourceName.name());
    }

    // Get pending
    public List<Queue> getDefaultSourceNameAllPendingIntegrationFiles() {
        return queueRepository.findByIntegrationStatusAndStepName(pending.name(), sourceName.name());
    }
    public List<Queue> getDefaultSourceNameAllPendingIntegrationFiles(StepName stepName) {
        return queueRepository.findByIntegrationStatusAndStepNameAndSourceName(pending.name(), stepName.name(), sourceName.name());
    }
    public List<Queue> getAllPendingIntegrationFiles() {
        return queueRepository.findByIntegrationStatus(pending.name());
    }
    public List<Queue> getStepNameAllPendingIntegrationFiles(StepName stepName) {
        return queueRepository.findByIntegrationStatusAndStepName(pending.name(), stepName.name());
    }
    public List<Queue> getSourceNameAllPendingIntegrationFiles(SourceName sourceName) {
        return queueRepository.findByIntegrationStatusAndSourceName(pending.name(), sourceName.name());
    }
    public List<Queue> getStepNameSourceNameAllPendingIntegrationFiles(StepName stepName, SourceName sourceName) {
        return queueRepository.findByIntegrationStatusAndStepNameAndSourceName(pending.name(), stepName.name(), sourceName.name());
    }


    // Get running
    public List<Queue> getDefaultSourceNameAllRunningIntegrationFiles() {
        return queueRepository.findByIntegrationStatusAndStepName(running.name(), sourceName.name());
    }
    public List<Queue> getDefaultSourceNameAllRunningIntegrationFiles(StepName stepName) {
        return queueRepository.findByIntegrationStatusAndStepNameAndSourceName(running.name(), stepName.name(), sourceName.name());
    }
    public List<Queue> getAllRunningIntegrationFiles() {
        return queueRepository.findByIntegrationStatus(running.name());
    }
    public List<Queue> getStepNameAllRunningIntegrationFiles(StepName stepName) {
        return queueRepository.findByIntegrationStatusAndStepName(running.name(), stepName.name());
    }
    public List<Queue> getSourceNameAllRunningIntegrationFiles(SourceName sourceName) {
        return queueRepository.findByIntegrationStatusAndSourceName(running.name(), sourceName.name());
    }
    public List<Queue> getStepNameSourceNameAllRunningIntegrationFiles(StepName stepName, SourceName sourceName) {
        return queueRepository.findByIntegrationStatusAndStepNameAndSourceName(running.name(), stepName.name(), sourceName.name());
    }


    // Get completed
    public List<Queue> getDefaultSourceNameAllCompletedIntegrationFiles() {
        return queueRepository.findByIntegrationStatusAndStepName(completed.name(), sourceName.name());
    }
    public List<Queue> getDefaultSourceNameAllCompletedIntegrationFiles(StepName stepName) {
        return queueRepository.findByIntegrationStatusAndStepNameAndSourceName(completed.name(), stepName.name(), sourceName.name());
    }
    public List<Queue> getAllCompletedIntegrationFiles() {
        return queueRepository.findByIntegrationStatus(completed.name());
    }
    public List<Queue> getStepNameAllCompletedIntegrationFiles(StepName stepName) {
        return queueRepository.findByIntegrationStatusAndStepName(completed.name(), stepName.name());
    }
    public List<Queue> getSourceNameAllCompletedIntegrationFiles(SourceName sourceName) {
        return queueRepository.findByIntegrationStatusAndSourceName(completed.name(), sourceName.name());
    }
    public List<Queue> getStepNameSourceNameAllCompletedIntegrationFiles(StepName stepName, SourceName sourceName) {
        return queueRepository.findByIntegrationStatusAndStepNameAndSourceName(completed.name(), stepName.name(), sourceName.name());
    }

    public List<Queue> loadFromStorage() {
        return queueRepository.findAll();
    }

    public Queue persistIntoStorage(Queue queue) {
        queue.setSourceName(sourceName.name());
        return queueRepository.save(queue);
    }

    public List<Queue> persistIntoStorage(List<Queue> queues) {
        List<Queue> sourceQueues = queues.stream()
                .map(
                        q -> {
                            if(Objects.isNull(q.getSourceName())){
                                q.setSourceName(sourceName.name());
                                return q;
                            }
                            else
                                return q;
                        }
                )
                .collect(Collectors.toList());
        return queueRepository.saveAll(sourceQueues);
    }
    public boolean updateQueue(Queue queue, IntegrationStatus status) {
        if(exists(queue)){
            queue.setIntegrationStatus(status.name());
            persistIntoStorage(queue);
            return true;

        }
        return false;
    }

    public boolean checkMatchStatus(Queue queue, IntegrationStatus status){
        boolean isNotPresent = queueRepository
                .findByIntegrationStatusAndStepNameAndSourceNameAndFileName(
                        status.name(),
                        queue.getStepName(),
                        queue.getSourceName(),
                        queue.getFileName()
                )
                .isEmpty();
        return !isNotPresent;
    }
    public boolean checkMatchNullStatus(Queue queue){
        List<Queue> candidates = queueRepository
                .findByStepNameAndSourceNameAndFileName(
                        queue.getStepName(),
                        queue.getSourceName(),
                        queue.getFileName()
                );
        return candidates.isEmpty() ||
                candidates.stream().anyMatch(q -> Objects.isNull(q.getIntegrationStatus()));
    }

    public Queue checkAndPutQueue(Queue queue, IntegrationStatus currentStatus, IntegrationStatus nextStatus) {
        boolean matchesNullStatus = checkMatchNullStatus(queue);
        boolean matchesCurrentStatus = checkMatchStatus(queue, currentStatus);

        log.debug(
                "matchesNullStatus={}, matchesCurrentStatus={}, queue.fileName={}",
                matchesNullStatus, matchesCurrentStatus, queue.getFileName()
        );
        if(matchesCurrentStatus || matchesNullStatus){
            queue.setIntegrationStatus(nextStatus.name());
            log.debug("queue={}", queue);
            return persistIntoStorage(queue);
        }
        return queue;
    }
}
