package com.baidu.fsg.uid.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * UidProperties
 *
 * @Author IIIDelay
 * @Date 2024/1/3 21:37
 **/
@ConfigurationProperties(prefix = "uid-generator")
@Data
public class UidProperties {
    private Integer timeBits = 29;
    private Integer workerBits = 21;
    private Integer seqBits = 13;
    private String epochStr = parseDate();

    private String parseDate() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}