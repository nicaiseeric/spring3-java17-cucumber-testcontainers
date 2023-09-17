package fr.neads.data.common.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties (prefix = "file.path")
public class AppProperties {
    private String raw;
    private String store;
}
