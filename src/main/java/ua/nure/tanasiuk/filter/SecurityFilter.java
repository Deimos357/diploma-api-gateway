package ua.nure.tanasiuk.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import ua.nure.tanasiuk.commons.Constants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static ua.nure.tanasiuk.commons.Constants.Parameters.LOGGED_IN_USER;

import static ua.nure.tanasiuk.commons.Constants.Url.URL_WITHOUT_AUTH;
import static java.util.stream.Collectors.toMap;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class SecurityFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Constants.PreFiltersOrder.CORE_SECURITY_FILTER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return !PatternMatchUtils.simpleMatch(URL_WITHOUT_AUTH, context.getRequest().getRequestURI());
    }

    @Override
    public Object run() {
        String principalId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RequestContext context = RequestContext.getCurrentContext();
        Map<String, List<String>> newParameterMap = context.getRequest().getParameterMap().entrySet().stream()
            .collect(toMap(Map.Entry::getKey, entry -> Arrays.asList(entry.getValue())));

        newParameterMap.put(LOGGED_IN_USER, Collections.singletonList(principalId));
        context.setRequestQueryParams(newParameterMap);
        return null;
    }
}
