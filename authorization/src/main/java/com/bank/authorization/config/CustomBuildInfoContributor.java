package com.bank.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Component
public class CustomBuildInfoContributor implements InfoContributor {
    @Autowired(required = false)
    private BuildProperties buildProperties;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    private final LocalDateTime startupTime;

    public CustomBuildInfoContributor() {
        startupTime = LocalDateTime.now();
    }

    @Override
    public void contribute(Info.Builder builder) {
        final LinkedHashMap<String, Object> buildInfo = new LinkedHashMap<>();
        if (buildProperties != null) {
            buildInfo.put("version", buildProperties.getVersion());
        }
        if (buildProperties != null) {
            buildInfo.put("artifact", buildProperties.getArtifact());
        }
        buildInfo.put("startupDateTime", startupTime);
        buildInfo.put("context-path", contextPath);
        if (buildProperties != null) {
            buildInfo.put("group", buildProperties.getGroup());
        }
        builder.withDetail("build", buildInfo);
    }
}
