package com.project;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class OrderPropertyHolder {

    @Value("${order.reminder.cut.off.time}")
    private Integer orderReminderCutOffTime;
}
