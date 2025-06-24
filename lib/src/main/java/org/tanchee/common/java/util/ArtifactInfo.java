package org.tanchee.common.java.util;

import java.io.InputStream;
import java.lang.Package;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class ArtifactInfo {

    private Class<?> clazz;

    public static final String MANIFEST_PATH = "/META-INF/MANIFEST.MF";

    public static final String IMPLEMENTATION_VERSION = "Implementation-Version";

    public static final String ARTIFACT_VERSION_NOT_FOUND = "VERSION_NOT_FOUND";

    public ArtifactInfo(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getArtifactVersion() {
        if (this.clazz == null) {
            System.out.println("NO CLASS PROVIDED");
            return ARTIFACT_VERSION_NOT_FOUND;
        }
        return getArtifactVersion(this.clazz);
    }

    public static String getArtifactVersion(Class<?> clazz) {
        Package pkg = clazz.getPackage();
        String version = pkg.getImplementationVersion();
        if (version != null) return version;

        try (InputStream is = clazz.getResourceAsStream(MANIFEST_PATH)) {
            if (is != null) {
                Manifest manifest = new Manifest(is);
                Attributes attributes = manifest.getMainAttributes();
                return (String) attributes.getOrDefault(IMPLEMENTATION_VERSION, ARTIFACT_VERSION_NOT_FOUND);
            }
        } catch (Exception e) {
        }
        return ARTIFACT_VERSION_NOT_FOUND;
    }

}
