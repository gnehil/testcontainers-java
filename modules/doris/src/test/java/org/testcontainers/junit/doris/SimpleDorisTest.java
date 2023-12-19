package org.testcontainers.junit.doris;

import org.junit.Test;
import org.testcontainers.DorisTestImages;
import org.testcontainers.db.AbstractContainerDatabaseTest;
import org.testcontainers.doris.DorisContainer;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleDorisTest extends AbstractContainerDatabaseTest {

    @Test
    public void simpleTest() throws SQLException {
        try (DorisContainer container = new DorisContainer(DorisTestImages.DORIS_IMAGE)) {
            container.start();
            ResultSet resultSet = performQuery(container, "SELECT 1");
            String s = resultSet.getString(1);
            assertThat(s).isEqualTo("1");
        }
    }
}
