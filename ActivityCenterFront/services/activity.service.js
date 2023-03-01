import axios from 'axios'
import authHeader from './auth-header'

const API_URL = 'http://localhost:8080/api/';

class ActivityService {

    getAllActivities() {
        return axios
            .get(API_URL + 'getAllActivities', {
                headers: authHeader(),
            }).then(response => {
                console.log(response.data)
                return response.data
            })
    }

    getFilteredActivities(type, name, distance, category, locationX, locationY) {
        return axios
            .post(API_URL + 'getFilteredActivities', {
                headers: authHeader(),
                filterType: type,
                name: name,
                category: category,
                distance: distance,
                locationX: locationX,
                locationY: locationY
            }).catch(function (error) {
                if (error.response) {
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else if (error.request) {
                    console.log(error.request);
                } else {
                    console.log('Error', error.message);
                }
                console.log(error.config);
            }).then(response => {
                return response.data
            })
    }
    
    addEvent(username, idActivity, date, time) {
        return axios
            .post(API_URL + 'insertEvent', {
                headers: authHeader(),
                username: username,
                date: date + " " + time,
                idActivity: idActivity
            }).then(response => {
                console.log(response.data)
            })
    }

    getEventsByUsername(username) {
        return axios
            .post(API_URL + 'getAllEvents', {
                headers:
                    authHeader()
                ,
                username: username
            })
    }

    deleteEvent(idEvent) {
        return axios
            .delete(API_URL + "deleteEvent/" + idEvent,{
                headers: authHeader(),
            }).then (response => {
                console.log(response.data);
            })
    }

}

export default new ActivityService();