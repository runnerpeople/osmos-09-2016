package ru.mail.park.json;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.park.implementationDAO.View;

public class IdResponse {
    @JsonView(View.Summary.class)
    private Integer userid;

    public IdResponse(Integer id) {
        this.userid = id;
    }

    public void setId(Integer id) {
        this.userid = id;
    }

    public Integer getId() {
        return userid;
    }
}
