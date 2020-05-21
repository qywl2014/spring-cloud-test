package com.wulang.spring.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

/**
 * Title: ApiGlobalFilter
 * <p>
 * Description:
 * </p>
 * @author <a href="mailto:baolin.zhu@melot.cn">朱宝林</a>
 * @version V1.0.0
 * @since 2020/5/6 11:33
 */
@Component
public class ApiGlobalFilter implements GlobalFilter, Ordered {

    public static int count = 1;

    public static int apiCount = 1;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
        String routeId = route.getId();
        System.err.println("ApiGlobalFilter: " + routeId);
        URI uri = exchange.getRequest().getURI();
        if (routeId.startsWith("api-")) {
            String path = exchange.getRequest().getPath().value();
            char[] chars = path.toCharArray();
            int newPathStart = 1;
            while (newPathStart < chars.length && chars[newPathStart] != '/') {
                newPathStart++;
            }
            chars[newPathStart] = '-';
            while (newPathStart < chars.length && chars[newPathStart] != '/') {
                newPathStart++;
            }
            String newPath = new String(chars);
            // /api-service/xx -> /xx
            ServerHttpRequest newRequest = exchange.getRequest().mutate().path(newPath.substring(newPathStart)).build();
            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

            String name = newPath.substring(5, newPathStart);
            // lb://api-service/xx
            URI newUri = UriComponentsBuilder.fromUriString("lb:/" + newPath).build().toUri();
            Route newRoute = Route.async()
                    .asyncPredicate(route.getPredicate())
                    .filters(route.getFilters())
                    .id(name)
                    .uri(newUri)
                    .order(route.getOrder())
                    .build();
            exchange.getAttributes().put(GATEWAY_ROUTE_ATTR, newRoute);
            return chain.filter(newExchange).then(Mono.just(newExchange)).map(serverWebExchange ->{
                System.out.println(apiCount++ + "after api filter: "+serverWebExchange.getResponse().toString());
                return serverWebExchange;
            }).then();
        }
        return chain.filter(exchange).then(Mono.just(exchange)).map(serverWebExchange ->{
            System.out.println(count++ + "after un api filter: "+serverWebExchange.getResponse().getStatusCode());
            return serverWebExchange;
        }).then();
    }

    @Override
    public int getOrder() {
        return 9999;
    }
}
