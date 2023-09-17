package fr.neads.data.common.queue;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Entity
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "integration_queue")
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileId;
    private String fileName;
    private String absoluteFilePath;
    private Long fileSize;
    private Long fileNbRows;
    private Long nbCdrRows;
    private LocalDateTime fileModificationDatetime;
    private Long integrationStartTimestamp;
    private Long integrationEndTimestamp;
    private String integrationStatus;
    private String stepName;
    private String rootCause;
    private String sourceName;

    public Queue copy(){
        return Queue.builder()
                .absoluteFilePath(absoluteFilePath)
                .build();
    }

    public Queue copy(String stepName){
        return Queue.builder()
                .absoluteFilePath(absoluteFilePath)
                .stepName(stepName)
                .build();
    }
    public Queue copy(String integrationStatus, String stepName){
        return Queue.builder()
                .absoluteFilePath(absoluteFilePath)
                .integrationStatus(integrationStatus)
                .stepName(stepName)
                .build();
    }

}
