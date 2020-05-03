package responses;

import lombok.Data;

@Data
public class Response {
    private String message = null;

    public boolean hasMessage() {
        return message != null;
    }

}
