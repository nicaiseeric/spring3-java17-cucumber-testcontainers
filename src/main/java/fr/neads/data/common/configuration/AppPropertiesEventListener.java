package fr.neads.data.common.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AppPropertiesEventListener {

    public static final String HIDDEN_PATTERN = "password|pwd|jaas.config|auth.user.info";

    public static final String HIDDEN_CONFIGURATION_VALUE = "[hidden]";

    public static final List<String> CONFIGURATION_FILES = Arrays.asList("application.yml","application.yaml");

    @EventListener
    public void handleContextRefreshed(ContextRefreshedEvent event) {
        log.info("Application properties: \n{}",
                getProperties((ConfigurableEnvironment) event.getApplicationContext().getEnvironment()));
    }

    private String getProperties(ConfigurableEnvironment env) {

        List<MapPropertySource> propertySources = new ArrayList<>();

        env.getPropertySources().forEach(
                it -> {
                    boolean isConfigFile = CONFIGURATION_FILES.stream()
                            .anyMatch(CONFIGURATION_FILE -> it.getName().contains(CONFIGURATION_FILE));
                if (it instanceof MapPropertySource && isConfigFile) {
                    log.info("found configuration file={}", it.getName());
                    propertySources.add((MapPropertySource) it);
                }
        });

        return propertySources.stream()
                .map(propertySource -> propertySource.getSource().keySet())
                .flatMap(Collection::stream)
                .distinct()
                .sorted()
                .map(key -> {
                    try {
                        // Obfuscate unwanted values
                        ExpressionParser parser = new SpelExpressionParser();
                        String value = HIDDEN_CONFIGURATION_VALUE;
                        Pattern p = Pattern.compile(HIDDEN_PATTERN);
                        Matcher m = p.matcher(key);
                        if (!m.find()) {
                            // Parse dynamic values (like #{"PARTNER".toLowerCase})
                            value = parser.parseExpression(Objects.requireNonNull(env.getProperty(key)),
                                    new TemplateParserContext()).getValue(String.class);
                        }
                        return "\t" + key + " = " + value;
                    } catch (Exception e) {
                        log.warn("{} -> {}", key, e.getMessage());
                        return "\t" + key + " = N/A";
                    }
                })
                .collect(Collectors.joining("\n"));
    }
}

