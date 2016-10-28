package ru.mail.park.json;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.park.implementationDAO.View;

public class ErrorResponse {
    @JsonView(View.Summary.class)
    private String cause;

    public ErrorResponse(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
