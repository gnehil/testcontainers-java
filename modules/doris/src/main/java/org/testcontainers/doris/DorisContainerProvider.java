package org.testcontainers.doris;

import org.apache.commons.lang3.StringUtils;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.JdbcDatabaseContainerProvider;
import org.testcontainers.utility.DockerImageName;

public class DorisContainerProvider extends JdbcDatabaseContainerProvider {

    public static final String DEFAULT_TAG = "2.0.3";

    @Override
    public boolean supports(String databaseType) {
        return DorisContainer.NAME.equals(databaseType);
    }

    @Override
    public JdbcDatabaseContainer<DorisContainer> newInstance() {
        return newInstance(DEFAULT_TAG);
    }

    @Override
    public JdbcDatabaseContainer<DorisContainer> newInstance(String tag) {
        return StringUtils.isBlank(tag)
            ? newInstance()
            : new DorisContainer(DockerImageName.parse(DorisContainer.DOCKER_IMAGE_NAME).withTag(tag));
    }
}
