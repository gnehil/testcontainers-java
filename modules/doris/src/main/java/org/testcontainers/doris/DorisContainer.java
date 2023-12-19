package org.testcontainers.doris;

import lombok.NonNull;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

public class DorisContainer extends JdbcDatabaseContainer<DorisContainer> {

    public static final String NAME = "doris";

    public static final String DOCKER_IMAGE_NAME = "adamlee489/doris";

    private static final DockerImageName DEFAULT_DOCKER_IMAGE = DockerImageName.parse(DOCKER_IMAGE_NAME);

    private static final int QUERY_PORT = 9030;

    private static final int FE_HTTP_PORT = 8030;

    private static final int BE_HTTP_PORT = 8040;

    private static final int BE_RPC_PORT = 8060;

    private static final String DATABASE_NAME = "mysql";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "";

    public DorisContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DEFAULT_DOCKER_IMAGE);
        addExposedPorts(QUERY_PORT, FE_HTTP_PORT, BE_HTTP_PORT, BE_RPC_PORT);
        this.setWaitStrategy(new DorisWaitStrategy().withStartupTimeout(Duration.ofSeconds(60L)));
    }

    public DorisContainer(@NonNull String dockerImageName) {
        this(DockerImageName.parse(dockerImageName));
    }

    @Override
    public String getDriverClassName() {
        String driverClassName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            driverClassName = "com.mysql.jdbc.Driver";
        }
        return driverClassName;
    }

    @Override
    public String getJdbcUrl() {
        String param = constructUrlParameters("?", "&");
        return String.format("jdbc:mysql://%s:%d/%s%s", getHost(), getMappedPort(QUERY_PORT), DATABASE_NAME, param);
    }

    @Override
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    protected String getTestQueryString() {
        return "SELECT 1";
    }
}
