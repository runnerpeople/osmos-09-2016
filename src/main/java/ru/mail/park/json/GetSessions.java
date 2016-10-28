package ru.mail.park.json;

import com.fasterxml.jackson.annotation.JsonView;
import ru.mail.park.implementationDAO.View;

import java.util.Collection;

public class GetSessions {
    @JsonView(View.SummaryWithRecipients.class)
    private Collection session;

    public void setSession(Collection session) {
        this.session = session;
    }

    public Collection getSession() {
        return session;
    }

    private GetSessions(Collection session) {

        this.session = session;
    }
}