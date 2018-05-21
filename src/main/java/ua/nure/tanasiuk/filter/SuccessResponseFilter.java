package ua.nure.tanasiuk.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import ua.nure.tanasiuk.response.OperationResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;

import static ua.nure.tanasiuk.commons.Constants.PostFiltersOrder.SUCCESS_RESPONSE_FILTER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Component
public class SuccessResponseFilter extends ZuulFilter {
    private final ObjectMapper objectMapper;

    public SuccessResponseFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return SUCCESS_RESPONSE_FILTER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        int status = context.getResponse().getStatus();
        return status == HttpStatus.OK.value();
    }

    @SneakyThrows
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        final String responseData = CharStreams
            .toString(new InputStreamReader(context.getResponseDataStream(), "UTF-8"));
        // set body here
        OperationResponse success = OperationResponse.success(responseData);
        context.setResponseBody(objectMapper.writeValueAsString(success.getBody()));
        context.getResponse().setStatus(success.getStatusCodeValue());
        return null;
    }
}
