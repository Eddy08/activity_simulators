package com.harsh.webactivitytracker.webactivitytracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Activity {
    @Id
    @GeneratedValue
    private int id;
    private long unique_id;
    private String name;
    private LocalDateTime time;
    private int duration;
}
