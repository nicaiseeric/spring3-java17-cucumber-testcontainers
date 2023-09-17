package fr.neads.data.common.beans;

import java.util.Arrays;
import java.util.List;

public enum SourceName {
    NET,
    RS,
    RSS;

    public static final List<String> SOURCE_NAME_NAMES = Arrays.stream(SourceName.values())
            .map(SourceName::name).toList();

    public static List<String> getSourceNameNames(){
        return SOURCE_NAME_NAMES;
    }

}
