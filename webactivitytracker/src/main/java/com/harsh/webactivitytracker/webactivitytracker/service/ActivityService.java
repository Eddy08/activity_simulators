package com.harsh.webactivitytracker.webactivitytracker.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.harsh.webactivitytracker.webactivitytracker.model.Activity;
import com.harsh.webactivitytracker.webactivitytracker.model.ActivityName;
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
    public String getActivitiesFromDir(String dir_name)throws IOException{
        List<Activity> list=new ArrayList<>();
        Set<Activity> set = new HashSet<>();
        var lists=   Files.walk(Paths.get(dir_name))
                                .filter(Files::isRegularFile)
                                .map(Path::toFile)
                                .collect(Collectors.toList());
        // System.out.println(lists);
        ObjectMapper objectMapper=JsonMapper.builder() // or different mapper for other format
        .addModule(new ParameterNamesModule())
        .addModule(new Jdk8Module())
        .addModule(new JavaTimeModule())
        // and possibly other configuration, modules, then:
        .build();

        int count=0;
        int totalActivities=0;
        for(var obj:lists){
            FileContentJson json=objectMapper.readValue(obj, FileContentJson.class);
            // Filtering of invalid activities
            totalActivities+=json.getActivities().size();
            for(var list_of_activities:json.getActivities()){
                Activity act=new Activity();
                act.setUnique_id(json.getUnique_id());
                if(ActivityName.get(list_of_activities.getName())!=null) act.setName(ActivityName.valueOf(list_of_activities.getName()));
                else continue;
                if(list_of_activities.getDuration()>0) act.setDuration(list_of_activities.getDuration());
                else continue;
                act.setTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(list_of_activities.getTime()),
                TimeZone.getDefault().toZoneId()));
                set.add(act);
                count++;
            }
            
        }
        String str="Total Activities Present in all the Files : "+totalActivities+"\n"+"Invalid Activities : "+count;
        System.out.println("Total Activities Present in all the Files : "+totalActivities);
        System.out.println("Valid Activities : "+count);
        // System.out.println(lists.getClass().getTypeName());
        list=new ArrayList<>(set);
        saveActivity(list);
        return str;
    }
    public TreeMap<Enum,Integer> getAllActivitiesForLastNDays(int days){
        TreeMap<Enum,Integer> hmap=new TreeMap<>();
        LocalDateTime dateTime=LocalDateTime.now().minusDays(days);
       List<Activity> list= activityRepository.findAllByTimeGreaterThan(dateTime);
        for(var act:list){
            hmap.put(act.getName(),hmap.getOrDefault(act.getName(), 0)+1);
        }

        return hmap;
    }
    public LinkedHashMap<String,List<HashMap<String,Object>>> getAllActivities(){
        LinkedHashMap<String,List<HashMap<String,Object>>> hmap=new LinkedHashMap<>();
        LocalDateTime dateTime=LocalDateTime.now().minusDays(30);

        LocalDateTime todayDateTime=LocalDate.now().atStartOfDay();
        LocalDateTime yesterdayDateTime=todayDateTime.minusDays(1);
        System.out.println("today "+todayDateTime+"\n yesterday "+yesterdayDateTime);
        System.out.println("\ntoday+1 "+todayDateTime+"\n yesterday+1 "+yesterdayDateTime);


        HashMap<String,Object> activity_analytics_30days=new HashMap<>();
        HashMap<String,Integer> activity_analytics_today=new HashMap<>();
        HashMap<String,Integer> activity_analytics_yesterday=new HashMap<>();


       List<Activity> list= activityRepository.findAllByTimeGreaterThan(dateTime);
        for(var act:list){
            activity_analytics_30days.put(act.getName().toString(),
            Integer.valueOf((Integer)activity_analytics_30days.getOrDefault(act.getName().toString(), Integer.valueOf(0)))+1
            );
        }
        
        // compartor for sorting the entries and the updating in the linked hashMap

        Comparator<Entry<String,Object> >valueCompartor=new Comparator<>() {
            @Override
            public int compare(Entry<String,Object>e1,Entry<String,Object> e2){
                Integer v1=(Integer)e1.getValue();
                Integer v2=(Integer)e2.getValue();
                return v1.compareTo(v2);
            }
        };
        Set<Entry<String,Object>> entries = activity_analytics_30days.entrySet();
        List<Entry<String,Object>> listOfEntries =  new ArrayList<>(entries);
        
        Collections.sort(listOfEntries,valueCompartor);
        Collections.reverse(listOfEntries);
        LinkedHashMap<String,Object> sorted_activity_analytics_30days=new LinkedHashMap<>();
        for(Entry<String,Object> ent:listOfEntries){
            sorted_activity_analytics_30days.put(ent.getKey(),ent.getValue());
        }


        List<HashMap<String,Object>> activity_analytics_30days_list=new ArrayList<>();
        activity_analytics_30days_list.add(sorted_activity_analytics_30days);
        
        List<Activity> list2=activityRepository.findAllByTimeGreaterThanAndTimeLessThan(todayDateTime,todayDateTime.plusDays(1));
        List<Activity> list3=activityRepository.findAllByTimeGreaterThanAndTimeLessThan(yesterdayDateTime,todayDateTime);
        for(var act:list2){
            activity_analytics_today.put(act.getName().toString(),activity_analytics_today.getOrDefault(act.getName().toString(), Integer.valueOf(0))+1);
        }
        for(var act:list3){
            activity_analytics_yesterday.put(act.getName().toString(),activity_analytics_yesterday.getOrDefault(act.getName().toString(), Integer.valueOf(0))+1);
        }
        System.out.println("Yesterday Map"+activity_analytics_yesterday);
        System.out.println("Today Map"+activity_analytics_today);

       ActivityName[] acts= ActivityName.values();
       List<HashMap<String,Object>> activity_analytics_compare_list=new ArrayList<>();
       for(var a:acts){
            HashMap<String,Object> activity_analytics_comparision=new HashMap<>();
            int yesterday_count=0;
            if(activity_analytics_yesterday.get(a.toString())!=null) yesterday_count=activity_analytics_yesterday.get(a.toString());
            
            int today_count=0;
            if(activity_analytics_today.get(a.toString())!=null) today_count=activity_analytics_today.get(a.toString());
            

            activity_analytics_comparision.put("activity_name",a.toString());
            activity_analytics_comparision.put("yesterday_occurence",yesterday_count);
            activity_analytics_comparision.put("today_occurence",today_count);
            
            String status=yesterday_count - today_count ==0 ?"unaltered" : yesterday_count - today_count >0 ? "negative": "positive";
            
            activity_analytics_comparision.put("status",status);
            activity_analytics_compare_list.add(activity_analytics_comparision);
        }

        hmap.put("activity_statistics_for_month",activity_analytics_30days_list);
        hmap.put("activities_statistics_yesterday_vs_today",activity_analytics_compare_list);
        return hmap;
    }

}
