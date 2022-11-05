package org.data.model.params;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("database")
public class SQLDatabaseParams {
    private String url;
    private String username;
    private String password;
    private String driver;
    private String testQuery;
    private String poolName;
    private Integer poolSize;
    private String dialect;
    private String modelPackage;
}
