package com.harsh.webactivitytracker.webactivitytracker.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harsh.webactivitytracker.webactivitytracker.service.ActivityService;

@RestController
@RestControllerAdvice
public class ApiController{
    @Autowired
    private ActivityService activityService;

    @GetMapping ("/files")
    public ResponseEntity getFilesData() throws IOException {
        activityService.getActivitiesFromDir("/home/others/python/user-experior/ActivitiesToProcesses");
        return new ResponseEntity<String>("hello",HttpStatus.OK);
    }
}