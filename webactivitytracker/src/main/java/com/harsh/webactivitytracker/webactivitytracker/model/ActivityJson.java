package com.harsh.webactivitytracker.webactivitytracker.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

// import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityJson {
    private String name;
    // @JsonFormat("")
    private long time;
    private int duration;
}
