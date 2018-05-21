package ua.nure.tanasiuk.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import ua.nure.tanasiuk.commons.Constants;
import ua.nure.tanasiuk.response.OperationResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

@Slf4j
public class InternalServerErrorPostFilter extends ZuulFilter {
    private final ObjectMapper objectMapper;

    public InternalServerErrorPostFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return Constants.PostFiltersOrder.INTERNAL_SERVER_ERROR_FILTER;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        int status = context.getResponse().getStatus();
        return status == HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    @Override
    @SneakyThrows
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        OperationResponse failure = OperationResponse.failure(Constants.ErrorCodes.INTERNAL_SERVER_ERROR_CODE);
        context.setResponseBody(objectMapper.writeValueAsString(failure.getBody()));
        context.getResponse().setStatus(failure.getStatusCodeValue());
        return null;
    }
}
