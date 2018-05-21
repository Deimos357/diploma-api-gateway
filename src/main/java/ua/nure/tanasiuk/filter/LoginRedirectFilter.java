package ua.nure.tanasiuk.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ua.nure.tanasiuk.commons.Constants;

import java.net.URL;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.apache.commons.lang.StringUtils.EMPTY;

@Component
public class LoginRedirectFilter extends ZuulFilter {
    @Value("${zuul.routes.auth.url}")
    private String authServerHost;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Constants.PreFiltersOrder.LOGIN_REDIRECT_FILTER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        return context.getRequest().getRequestURI().startsWith(Constants.Url.CORE_LOGIN_URL_PREFIX);
    }

    @Override
    @SneakyThrows
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String redirectURL = context.getRequest().getRequestURI().replace("core", "auth");
        String urlWithoutStartingSlash = redirectURL.substring(1);
        context.setRouteHost(new URL(authServerHost + urlWithoutStartingSlash));
        context.set("requestURI", EMPTY);
        return null;
    }
}
