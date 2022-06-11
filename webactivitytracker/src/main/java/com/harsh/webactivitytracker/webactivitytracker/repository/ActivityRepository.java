package com.harsh.webactivitytracker.webactivitytracker.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.harsh.webactivitytracker.webactivitytracker.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
    List<Activity> findByName(String name); 
    // Activity save(Activity activity);
    // boolean saveAll(List<Activity> list);
}
