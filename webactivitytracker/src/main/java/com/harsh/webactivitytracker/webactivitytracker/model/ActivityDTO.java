package com.harsh.webactivitytracker.webactivitytracker.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ActivityDTO {
    private int id;
    private long unique_id;
    private String name;
    private LocalDate time;
    private int duration;

}
