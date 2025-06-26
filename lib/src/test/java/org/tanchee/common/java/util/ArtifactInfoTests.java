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
        assertEquals(ArtifactInfo.ARTIFACT_VERSION_NOT_FOUND, version);
    }

    @Test
    void staticVersionTest() {
        String version = ArtifactInfo.getArtifactVersion(ClassA.class);
        assertEquals(ArtifactInfo.ARTIFACT_VERSION_NOT_FOUND, version);
    }

    class ClassA {}

}
