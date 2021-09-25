package com.example.mywebquizengine.Model.Projection;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

public interface MessageForApiView {
    Integer getId();
    String getContent();
    UserForMessageView getSender();
    Calendar getTimestamp();
    DialogForApi getDialog();
    //Long getDialogId();
}

/*public interface MessageForApiView {
    Integer getId();
    String getContent();
    @Value("#{new com.example.mywebquizengine.Model.User(target.username, target.firstName, target.lastName, target.avatar)}")
    UserForMessageView getSender();

    Calendar getTimestamp();

    @Value("#{new com.example.mywebquizengine.Model.Chat.Dialog(target.dialogId, target.name, target.image)}")
    DialogForApi getDialog();
    //Long getDialogId();
}*/
