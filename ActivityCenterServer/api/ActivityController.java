package com.ActivityCenterServer.api;

import com.ActivityCenterServer.database.repository.ActivityRepository;
import com.ActivityCenterServer.model.Activity;
import com.ActivityCenterServer.web.payload.FilteredActivitiesRequest;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("api/getAllActivities")
    public ResponseEntity<?> getAllActivities() {
        ArrayList<Activity> activities = new ArrayList<Activity>();
        Iterable<Activity> activitiesIter = activityRepository.findAll();
        for(Activity act : activitiesIter) {
            activities.add(act);
        }
        return ResponseEntity.ok(activities);
    }

    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("api/getFilteredActivities")
    public ResponseEntity<?> getFilteredActivities(@RequestBody FilteredActivitiesRequest filteredActivitiesRequest) {
        ArrayList<Activity> filteredActivities = new ArrayList<Activity>();
        Iterable<Activity> activitiesIter = activityRepository.findAll();

        for(Activity act : activitiesIter) {
            switch(filteredActivitiesRequest.getFilterType()) {
                case "Name":
                    if (act.getName().contains(filteredActivitiesRequest.getName())) {
                        filteredActivities.add(act);
                    }
                    break;
                case "Type":
                    if (act.getType().equals(filteredActivitiesRequest.getCategory())) {
                        filteredActivities.add(act);
                    }
                    break;
                case "Location":
                    if (distance(filteredActivitiesRequest.getLocationX(),
                            act.getLocationX(),
                            filteredActivitiesRequest.getLocationY(),
                            act.getLocationY()) < filteredActivitiesRequest.getDistance()) {
                        filteredActivities.add(act);
                    }
                    System.out.println(distance(filteredActivitiesRequest.getLocationX(),
                            act.getLocationX(),
                            filteredActivitiesRequest.getLocationY(),
                            act.getLocationY()) < filteredActivitiesRequest.getDistance());
                    break;
            }
        }
        return ResponseEntity.ok(filteredActivities);
    }

    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);
        double c = 2 * Math.asin(Math.sqrt(a));

        double r = 6371;

        return(c * r);
    }

}
