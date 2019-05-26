package uca.plat.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by andy.lv
 * on: 2019/1/25 18:08
 */
@ConfigurationProperties(prefix = "uca.plat.file")
@Configuration
@Data
public class PlatFileConfiguration {

    private String url;

    private String realFilePath;

    private String exposedLocation;

}
