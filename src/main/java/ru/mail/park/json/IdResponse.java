package ru.mail.park.ResponseInJson;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.park.implementationDAO.View;

/**
 * Created by serqeycheremisin on 27/10/2016.
 */
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
