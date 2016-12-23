package ru.mail.park.websocket;

import org.jetbrains.annotations.NotNull;


public interface MessageHandlerContainer {

    void handle(@NotNull Message message, @NotNull Long forUser) throws HandleException;

    <T> void registerHandler(@NotNull Class<T> clazz, MessageHandler<T> handler);
}
