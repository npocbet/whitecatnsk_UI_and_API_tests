package cloud.autotests.api_example_tests.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewUserResponse {

    private String name;

    private String job;

    private String id;

    private String createdAt;

    private String updatedAt;

    public NewUserResponse() {
    }

}