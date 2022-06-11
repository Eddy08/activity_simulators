package com.harsh.webactivitytracker.webactivitytracker.model;

import java.util.HashMap;

public enum ActivityName {
    doubleTap,
    singleTap,
    crash,
    anr ;  
    private static HashMap<String, ActivityName> activities;
        static {
            activities = new HashMap<>();
            for (ActivityName r : ActivityName.values()) {
                activities.put(r.toString(), r);
            }
        }
        public static ActivityName get (String s){
            return activities.get(s);
        }
}
