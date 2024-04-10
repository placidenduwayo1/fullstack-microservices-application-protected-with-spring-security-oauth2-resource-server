package com.placide.k8skafkaavroaepccleanarchibsmicrosaddress.infra.adatpters.input.feignclients.proxy;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SvcAddressFeignRequestAuthInterceptor implements RequestInterceptor{
    static String regex ="^Bearer ([a-zA-Z0-9-._~+/]+=*)$";
    private static final Pattern BEARER_TOKEN_HEADER_PATTERN =
            Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    private static final String AUTHORIZATION = HttpHeaders.AUTHORIZATION;
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(Objects.nonNull(requestAttributes)){
            String authorizationHeader = requestAttributes.getRequest().getHeader(AUTHORIZATION);
            Matcher matcher = BEARER_TOKEN_HEADER_PATTERN.matcher(authorizationHeader);
            if(matcher.matches()){
                requestTemplate.header(AUTHORIZATION,authorizationHeader);
            }
            else{
                log.error("[ADDRESS-BS-MICROSERVICE] authorization not match");
            }
        }
        else
            log.error("[ADDRESS-BS-MICROSERVICE] Request Attributes null");
    }
}
