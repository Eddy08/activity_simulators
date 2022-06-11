package com.harsh.webactivitytracker.webactivitytracker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.harsh.webactivitytracker.webactivitytracker.model.Activity;
import com.harsh.webactivitytracker.webactivitytracker.model.FileContentJson;
import com.harsh.webactivitytracker.webactivitytracker.repository.ActivityRepository;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;
    public Activity saveActivity(Activity activity){
        return activityRepository.save(activity);
    }
    public List<Activity> saveActivity(List<Activity> list){
        return activityRepository.saveAll(list);
    }
    public List<Activity> getActivitiesFromDir(String dir_name)throws IOException{
        List<Activity> list=new ArrayList<>();
        var lists=   Files.walk(Paths.get(dir_name))
                                .filter(Files::isRegularFile)
                                .map(Path::toFile)
                                .collect(Collectors.toList());
        System.out.println(lists);
        ObjectMapper objectMapper=new ObjectMapper();

        for(var obj:lists){
            FileContentJson json=objectMapper.readValue(obj, FileContentJson.class);
            Activity act=new Activity();
            
        }
        System.out.println(lists.getClass().getTypeName());
        return list;
    }
}
