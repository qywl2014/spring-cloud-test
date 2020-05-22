package com.wulang.spring.spel;

import org.junit.Test;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.StringUtils.tokenizeToStringArray;

public class SpelTest {
    @Test
    public void gatewayDiscovery() {
        final ServiceInstance instanceForEval = new ServiceInstance() {
            @Override
            public String getServiceId() {
                return "apitivity";
            }

            @Override
            public String getHost() {
                return null;
            }

            @Override
            public int getPort() {
                return 0;
            }

            @Override
            public boolean isSecure() {
                return false;
            }

            @Override
            public URI getUri() {
                return null;
            }

            @Override
            public Map<String, String> getMetadata() {
                return null;
            }
        };

        SimpleEvaluationContext evalCtx = SimpleEvaluationContext.forReadOnlyDataBinding().withInstanceMethods().build();

        Expression valueExpr = new SpelExpressionParser().parseExpression("'/${remaining}'");
        String value = valueExpr.getValue(evalCtx, instanceForEval, String.class);
        System.out.println(value);
    }

    @Test
    public void t1() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'");
        String message = (String) exp.getValue();
        System.out.println(message);
    }

    @Test
    public void t2() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("'Hello World'.concat('!')");
        String message = (String) exp.getValue();
        System.out.println(message);
    }

    @Test
    public void t3() {
        String[] args = StringUtils.tokenizeToStringArray("nihao,hehe,lala", ",");
        for (String arg : args) {
            System.out.println(arg);
        }
    }
}
