package fr.neads.data.common.beans;

import java.util.Arrays;
import java.util.List;

public enum StepName {
    streaming,
    storage,
    quality,
    notif;

    public static final List<String> STEP_NAME_NAMES = Arrays.stream(StepName.values())
            .map(StepName::name).toList();

    public static List<String> getStepNameNames(){
        return STEP_NAME_NAMES;
    }
}
