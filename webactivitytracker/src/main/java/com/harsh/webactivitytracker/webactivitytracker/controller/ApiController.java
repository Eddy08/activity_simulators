package com.harsh.webactivitytracker.webactivitytracker.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.harsh.webactivitytracker.webactivitytracker.service.ActivityService;

@RestController
@RestControllerAdvice
public class ApiController{
    @Autowired
    private ActivityService activityService;
    @Autowired Environment env;
    @GetMapping ("/files")
    public ResponseEntity getFilesData() throws IOException {
        String dir_name=env.getProperty("directory-name-with-jsons");
        String response =activityService.getActivitiesFromDir(dir_name);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }

    @GetMapping("/analytics")
    public ResponseEntity getAllJobsInLastDays(@RequestParam int day){
        // TreeMap<Enum, Integer> map = activityService.getAllActivitiesForLastNDays(day);
        // Will reverse the tree map and then will check 

        
        String response =activityService.getAllActivities().toString();
        return new ResponseEntity<String>(response,HttpStatus.OK);

    }
}