package org.testcontainers.doris;

import org.testcontainers.containers.wait.strategy.AbstractWaitStrategy;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import static org.rnorth.ducttape.unreliables.Unreliables.retryUntilSuccess;

public class DorisWaitStrategy extends AbstractWaitStrategy {

    @Override
    protected void waitUntilReady() {
        DorisContainer container = (DorisContainer) waitStrategyTarget;
        retryUntilSuccess(
            Math.toIntExact(startupTimeout.getSeconds()),
            TimeUnit.SECONDS,
            () -> {
                getRateLimiter()
                    .doWhenReady(() -> {
                        try (
                            Connection conn = container.createConnection("");
                            Statement statement = conn.createStatement()
                        ) {
                            ResultSet rs = statement.executeQuery("show backends");
                            if (rs.next()) {
                                String isAlive = rs.getString(9);
                                if (!"true".equalsIgnoreCase(isAlive)) {
                                    throw new SQLException("be is not alive");
                                }
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                return true;
            }
        );
    }
}
