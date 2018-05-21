package ua.nure.tanasiuk.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import ua.nure.tanasiuk.response.OperationResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ua.nure.tanasiuk.commons.Constants;

import java.io.InputStreamReader;
import java.util.List;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Component
public class BadRequestResponseFilter extends ZuulFilter {
    private final ObjectMapper objectMapper;

    public BadRequestResponseFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return Constants.PostFiltersOrder.BAD_REQUEST_RESPONSE_FILTER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        int status = context.getResponse().getStatus();
        return status == HttpStatus.BAD_REQUEST.value() && context.sendZuulResponse();
    }

    @SneakyThrows
    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        final String responseData = CharStreams
            .toString(new InputStreamReader(context.getResponseDataStream(), "UTF-8"));
        OperationResponse failure = OperationResponse.failure(objectMapper.readValue(responseData, List.class));
        context.setResponseBody(objectMapper.writeValueAsString(failure.getBody()));
        context.getResponse().setStatus(failure.getStatusCodeValue());
        return null;
    }
}
