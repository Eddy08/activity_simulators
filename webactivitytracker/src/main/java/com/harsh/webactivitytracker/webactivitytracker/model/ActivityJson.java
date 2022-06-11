package com.harsh.webactivitytracker.webactivitytracker.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityJson {
    private String name;
    private LocalDateTime time;
    private int duration;
}
