package com.example.mywebquizengine.Model.Projection;

import com.example.mywebquizengine.Model.User;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

public interface MeetingView {
    Long getId();

    @Value("#{new com.example.mywebquizengine.Model.User(target.first_username, target.first_firstName, target.first_lastName, target.first_avatar)}")
    UserCommonView getFirstUser();

    @Value("#{new com.example.mywebquizengine.Model.User(target.second_username, target.second_firstName, target.second_lastName, target.second_avatar)}")
    UserCommonView getSecondUser();

    Double getLng();
    Double getLat();

    Timestamp getTime();
}
