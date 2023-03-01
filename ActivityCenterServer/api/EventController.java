package com.ActivityCenterServer.api;


import com.ActivityCenterServer.database.repository.ActivityRepository;
import com.ActivityCenterServer.database.repository.EventRepository;
import com.ActivityCenterServer.model.Activity;
import com.ActivityCenterServer.model.Event;
import com.ActivityCenterServer.web.payload.ActivityWithDateResponse;
import com.ActivityCenterServer.web.payload.GetAllEventRequest;
import com.ActivityCenterServer.web.payload.InsertEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@RestController
public class EventController {

    @Autowired
    EventRepository eventRepository;
    @Autowired
    ActivityRepository activityRepository;

    //PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("api/insertEvent")
    public ResponseEntity<?> insertEvent(@RequestBody InsertEventRequest insertEventRequest) {
        Event event = new Event();
        event.setIdActivity(insertEventRequest.getIdActivity());
        event.setUsername(insertEventRequest.getUsername());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        try {
            System.out.println(insertEventRequest.getDate());
            Date date = formatter.parse(insertEventRequest.getDate());
            event.setDatetime(date);
            eventRepository.save(event);
            return ResponseEntity.ok("Event registered succesfully");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok("Format failed");
    }

    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("api/getAllEvents")
    public ResponseEntity<?> getAllEvents(@RequestBody GetAllEventRequest request) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        ArrayList<Event> events = (ArrayList<Event>) eventRepository.findAllByUsername(request.getUsername());
        System.out.println("Received username: " + request.getUsername());

        ArrayList<ActivityWithDateResponse> list = new ArrayList<ActivityWithDateResponse>();
        for(Event event : events) {
            Optional<Activity> act = activityRepository.findById(event.getIdActivity());
            String[] datetime = formatter.format(event.getDatetime()).split(" ");
            list.add(new ActivityWithDateResponse(datetime[0], datetime[1], event.getIdEvent(), act.get()));
        }

        for(ActivityWithDateResponse act : list) {
            System.out.println(act.getTime());
        }

        return ResponseEntity.ok(list);
    }

    //@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("api/deleteEvent/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") int id) {
        eventRepository.deleteById(id);

        return ResponseEntity.ok("Event deleted succesfully");
    }



}
