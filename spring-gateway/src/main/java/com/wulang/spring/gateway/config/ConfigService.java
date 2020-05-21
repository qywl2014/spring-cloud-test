package com.wulang.spring.gateway.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Title: ConfigService
 * <p>
 * Description:
 * </p>
 *
 * @author <a href="mailto:baolin.zhu@melot.cn">朱宝林</a>
 * @version V1.0.0
 * @since 2020/5/6 10:32
 */
@Component
@Getter
@RefreshScope
public class ConfigService {

    @Value(value = "${swaggerEnabled:true}")
    private boolean swaggerEnabled;

    private List<String> apiPrefixList = Arrays.asList("api","openplatform","admin");

    @Value("${signTimeOff:60000}")
    private long signTimeOff;

}
