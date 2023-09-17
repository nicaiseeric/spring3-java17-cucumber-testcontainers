package fr.neads.data.common.queue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueueRepository extends JpaRepository<Queue, Integer> {
    public Queue findByFileId(Integer fileId);
    public List<Queue> findByIntegrationStatus(String integrationStatus);
    public List<Queue> findByStepName(String stepName);
    public List<Queue> findBySourceName(String sourceName);
    public List<Queue> findByFileName(String fileName);
    public List<Queue> findByFileNameAndSourceName(String fileName, String sourceName);
    public List<Queue> findByIntegrationStatusAndSourceName(String integrationStatus, String sourceName);
    public List<Queue> findByIntegrationStatusAndStepName(String integrationStatus, String stepName);
    public List<Queue> findByStepNameAndSourceName(String stepName, String sourceName);
    public List<Queue> findByIntegrationStatusAndStepNameAndSourceName(String integrationStatus, String stepName, String sourceName);
    public List<Queue> findByStepNameAndSourceNameAndFileName(String stepName, String sourceName, String fileName);
    public List<Queue> findByIntegrationStatusAndStepNameAndSourceNameAndFileName(
            String integrationStatus, String stepName, String sourceName, String fileName
    );

}
