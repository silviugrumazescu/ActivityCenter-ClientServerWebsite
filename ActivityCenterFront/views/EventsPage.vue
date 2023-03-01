<template>
    <div class="container">
        <div class="row">
            <div class="container d-inline-flex  justify-content-center">
                <!-- <label for="date" class="select-label">Selecteaza ziua:</label>
                <input type="date" id="date" name="select-input" class="select-input" required="required">
                <button type="button" class="btn btn-outline-primary select-button"
                    @click="getFilteredActivities">Show</button> -->
                    <h1>My Events</h1>
            </div>
        </div>

        <div class="row" v-if="loaded">
            <ul v-for="date in Object.keys(eventsByDate)">
                <p>{{date}}</p>
                <hr />
                <div class="ul-events">
                    <ul v-for="el in eventsByDate[date]" class="border">
                        <EventCard 
                            :name="el.activity.name" 
                            :time="el.time" 
                            :id="el.idEvent" 
                            :date="date"
                            @deleteEvent="deleteEvent"/>
                    </ul>
                </div>
                

            </ul>
        </div>

    </div>
</template>

<script>

import EventCard from '../components/EventCard.vue'
import ActivityService from '@/services/activity.service';

export default {
    name: 'EventsPage',
    components: {
        EventCard,
    },
    data() {
        return {
            events: null,
            eventsByDate: null,
            loaded: false
        }
    },
    created() {
        ActivityService.getEventsByUsername(this.$store.state.auth.user.username).then(result => {
            var array = result.data;
            this.events = result.data;
            console.log(this.events)
            this.reduceEvents();
        })
    },

    methods: {
        deleteEvent(id, date) {
            ActivityService.deleteEvent(id).then (() => {
                for(let i = 0; i < this.events.length; i++) {
                    if(this.events[i].idEvent == id) {
                        this.events.splice(i, 1);
                        i = this.events.length;
                    }
                }

                this.reduceEvents();

            })
        },
        reduceEvents() {
            this.eventsByDate = this.events.reduce(function (obj, value) {
                var key = value.date;
                if (obj[key] == null) obj[key] = [];

                obj[key].push(value);
                return obj;
            }, {});
            this.loaded = true;
            console.log(this.eventsByDate);
        }
    }
}

</script>

<style scoped>
.select-label {
    max-width: 20em;
    min-width: 10em;
}

.select-input {
    max-width: 30em;
    min-width: 30em;
    margin-right: 2em;
}

.select-button {
    max-width: 20em;
    min-width: 10em;
}

.ul-events {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);
}
</style>