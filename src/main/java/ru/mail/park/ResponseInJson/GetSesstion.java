package ru.mail.park.ResponseInJson;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.park.implementationDAO.View;

import java.util.Collection;

/**
 * Created by serqeycheremisin on 27/10/2016.
 */
public class GetSesstion {
    @JsonView(View.SummaryWithRecipients.class)
    private Collection session;

    public void setSession(Collection session) {
        this.session = session;
    }

    public Collection getSession() {
        return session;
    }

    public GetSesstion(Collection session) {

        this.session = session;
    }
}
