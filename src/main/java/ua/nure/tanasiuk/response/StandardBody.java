package ua.nure.tanasiuk.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
class StandardBody<T> {
    @JsonProperty("data")
    @JsonRawValue
    private T data;
    @JsonProperty("errors")
    private List<String> errors = new ArrayList<>();

    StandardBody(T data) {
        this.data = data;
    }
}
