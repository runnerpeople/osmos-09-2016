package ru.mail.park.ResponseInJson;

/**
 * Created by serqeycheremisin on 27/10/2016.
 */
public class SuccessResponse {
    private String login;

    public SuccessResponse(String login) {
        this.login = login;
    }

    //Функция необходима для преобразования см  https://en.wikipedia.org/wiki/Plain_Old_Java_Object
    @SuppressWarnings("unused")
    public String getLogin() {
        return login;
    }
}
