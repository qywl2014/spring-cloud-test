/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.wulang.spring.gateway.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/***
 *
 * @since:swagger-bootstrap-ui 1.0
 * @author fsl
 * 2019/05/04 12:38
 */
@Slf4j
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;
    private ConfigService configService;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<Route> routeList = new ArrayList<>();
        if (configService.isSwaggerEnabled()) {
            // example: CompositeDiscoveryClient_api-service-user-demo
            Set<String> allApiPrefix = new HashSet<>(configService.getApiPrefixList());
            routeLocator.getRoutes().subscribe(route -> {
                routeList.add(route);
                String id = route.getId();
                if (isApiPrefix(id, allApiPrefix)) {
                    // api-user -> api/user
                    String name = id.substring(25);
                    resources.add(swaggerResource(name, name.replace('-', '/') + "/v2/api-docs"));
                }
            });
        }

        for (Route route : routeList) {
            System.out.println(route);
        }
        return resources;
    }

    private static boolean isApiPrefix(String routeId, Set<String> allApiPrefix) {
        int n = routeId.length();
        if (n <= 25) {
            return false;
        }
        int i = 25;
        boolean hasDelimiter = false;
        while (i < n) {
            if (routeId.charAt(i) == '-') {
                hasDelimiter = true;
                break;
            }
            i++;
        }
        return hasDelimiter && allApiPrefix.contains(routeId.substring(25, i)) && n > i + 1;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        log.info("name:{}, location:{}", name, location);
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
