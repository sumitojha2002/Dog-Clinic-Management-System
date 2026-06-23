package com.example.backend.config;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class QueryTracer implements HibernatePropertiesCustomizer {
    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.statement_inspector",
            (StatementInspector) sql -> {
                if (sql.contains("pet_owner") || sql.contains("refresh_token")) {
                    System.out.println("\n>>> FULL TRACE for query: " + sql.substring(0, Math.min(60, sql.length())));
                    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
                    for (int i = 2; i < Math.min(trace.length, 27); i++) {
                        System.out.println("    at " + trace[i]);
                    }
                    System.out.println("<<< END FULL TRACE\n");
                }
                return sql;
            });
    }
}