package fr.neads.data.common.beans;

import java.util.Arrays;
import java.util.List;

public enum IntegrationStatus {
    pending,
    running,
    completed,
    failed,
    notfound;

    public static final List<String> INTEGRATION_STATUS_NAMES = Arrays.stream(IntegrationStatus.values())
            .map(IntegrationStatus::name).toList();

    public static List<String> getIntegrationStatusNames(){
        return INTEGRATION_STATUS_NAMES;
    }
}
