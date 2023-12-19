package org.testcontainers;

import org.testcontainers.utility.DockerImageName;

public interface DorisTestImages {
    DockerImageName DORIS_IMAGE = DockerImageName.parse("adamlee489/doris:2.0.3");
}
