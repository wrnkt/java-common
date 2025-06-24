package org.tanchee.common.java.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ArtifactInfoTests {
    public static final String EXPECTED_VERSION = "1.0.0";
    @Test
    void initTest() {
        ArtifactInfo info = new ArtifactInfo(ClassA.class);
    }

    @Test
    void versionTest() {
        ArtifactInfo info = new ArtifactInfo(ClassA.class);
        String version = info.getArtifactVersion();
        assertEquals(EXPECTED_VERSION, version);
    }

    @Test
    void staticVersionTest() {
        String version = ArtifactInfo.getArtifactVersion(ClassA.class);
        assertEquals(EXPECTED_VERSION, version);
    }

    class ClassA {}

}
