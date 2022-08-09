package com.academia.andruhovich.library.util;

import com.academia.andruhovich.library.model.Message;

import static com.academia.andruhovich.library.util.Constants.ID;
import static com.academia.andruhovich.library.util.Constants.MESSAGE;
import static com.academia.andruhovich.library.util.DateHelper.currentDate;

public class MessageHelper {

    public static Message createNewMessage() {
        Message message = new Message();
        message.setMessage(MESSAGE);
        message.setCreatedAt(currentDate());
        message.setUpdatedAt(currentDate());
        return message;
    }

    public static Message createExistingMessage() {
        Message message = new Message();
        message.setId(ID);
        message.setMessage(MESSAGE);
        message.setCreatedAt(currentDate());
        message.setUpdatedAt(currentDate());
        return message;
    }
}
