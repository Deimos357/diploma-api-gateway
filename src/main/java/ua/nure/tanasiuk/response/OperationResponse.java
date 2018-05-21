package ua.nure.tanasiuk.response;

import lombok.EqualsAndHashCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@EqualsAndHashCode(callSuper = true)
public final class OperationResponse<T> extends ResponseEntity<StandardBody> {
    private StandardBody<T> standardBody;

    private OperationResponse(HttpStatus status, T data) {
        super(status);
        this.standardBody = new StandardBody<>(data);
    }

    private OperationResponse(HttpStatus status, StandardBody<T> standardBody) {
        super(status);
        this.standardBody = standardBody;
    }

    @Override
    public StandardBody getBody() {
        return standardBody;
    }

    @Override
    public boolean hasBody() {
        return standardBody != null;
    }

    public static <T> OperationResponse<T> success(T data) {
        return StringUtils.EMPTY.equals(data)
            ? new OperationResponse(OK, Collections.emptyList())
            : new OperationResponse(OK, data);
    }

    public static <T> OperationResponse<T> failure(List<String> errors) {
        StandardBody<T> response = new StandardBody(Collections.emptyList());
        response.setErrors(new ArrayList<>(errors));

        return new OperationResponse<>(BAD_REQUEST, response);
    }

    public static OperationResponse failure(String errorMessage) {
        return failure(singletonList(errorMessage));
    }
}
