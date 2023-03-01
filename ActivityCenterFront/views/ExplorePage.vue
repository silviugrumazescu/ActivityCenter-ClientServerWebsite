<template>
    <div class="container">
        <div class="row pad-bott">
            <div class="container d-inline-flex  justify-content-center">
                <div class="dropdown pad-right">
                    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                        aria-expanded="false">
                        {{ filterMethod }}
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" @click="changeFilter('Name')">Name</a></li>
                        <li><a class="dropdown-item" @click="changeFilter('Location')">Location</a></li>
                        <li><a class="dropdown-item" @click="changeFilter('Type')">Type</a></li>
                    </ul>
                </div>
                <input v-if="filterMethod == 'Name'" v-model="name" type="text"
                    class="form-control filter-input pad-right" id="activityName" placeholder="Enter name">
                <input v-if="filterMethod == 'Location'" v-model="distance" type="text"
                    class="form-control filter-input pad-right" id="activityDistance" placeholder="Enter distange (km)">
                <div v-if="filterMethod == 'Type'" class="dropdown pad-right">
                    <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                        aria-expanded="false">
                        {{ activityType }}
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" @click="changeActivityType('Food')">Food</a></li>
                        <li><a class="dropdown-item" @click="changeActivityType('Entertainment')">Entertainment</a></li>
                        <li><a class="dropdown-item" @click="changeActivityType('Hotel')">Hotel</a></li>
                    </ul>
                </div>
                <button type="button" class="btn btn-primary filter-button pad-right"
                    @click="getFilteredActivities">Filter</button>

            </div>

        </div>
        <div class="row pad-bott" v-show="!loadingMap">
            <div id="map">

            </div>
        </div>

        <Modal v-model:visible="dateModalVisible" :title="dateModalTitle"
            :okButton="{ text: 'Adauga', onclick: addEvent }" :cancelButton="{ text: 'Cancel', onclick: null }">
            <div>
                <label for="date" class="pad-right">Ziua: </label>
                <input v-model="dateModalDate" type="date" id="date" name="date-input" class="pad-right">
                <label for="time" class="pad-right">Ora:</label>
                <input v-model="dateModalTime" type="time" id="time" name="time-input" class="pad-right">
            </div>
        </Modal>

        <Modal v-model:visible="galleryModalVisible" title="Gallery" type="clean">
            <div id="galleryControls" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <div class="carousel-item active">
                        <img :src="activities[activityOnModalGalleryID].imageUrls[0].url" class="d-block" alt="...">
                    </div>
                    <div class="carousel-item" v-for="url in activities[activityOnModalGalleryID].imageUrls.slice(1)">
                        <img :src="url.url" />
                    </div>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#galleryControls"
                    data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#galleryControls"
                    data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>

        </Modal>

        <div class="row">
            <ul v-for="(activity, index) in activities" :key="activity.name">
                <ActivityCard :id="index" :title="activity.name" :description="activity.description"
                    :openHours="activity.type" :imgLink="activity.imageUrls[0].url" @galleryEvent="galleryButtonPressed"
                    @addToCalendarEvent="triggerModal" @showOnMapEvent="showOnMapPressed" />
            </ul>
        </div>
    </div>
</template>

<script>

import ActivityService from '../services/activity.service';
import ActivityCard from '../components/ActivityCard.vue';
import { Loader } from '@googlemaps/js-api-loader';
import { Modal } from 'usemodal-vue3';


export default {
    name: 'ExplorePage',
    components: {
        ActivityCard,
        Modal,
    },
    data() {
        return {
            activities: [],
            filterMethod: "Name",
            activityType: "Food",
            name: "",
            distance: 0.0,
            currentPosLat: 0.0,
            currentPosLong: 0.0,
            loadingMap: true,
            markers: [],
            dateModalVisible: false,
            dateModalTitle: "",
            dateModalDate: "",
            dateModalTime: "",
            activityOnDateModalID: 0,
            galleryModalVisible: false,
            activityOnModalGalleryID: 0,
        }
    },
    created() {
        ActivityService.getAllActivities().then(result => {
            this.activities = result;
            console.log(this.activities)
        });

    },
    mounted() {
        this.initMap();
    },
    methods: {
        changeFilter(type) {
            this.filterMethod = type;
        },
        changeActivityType(type) {
            this.activityType = type;
        },
        getFilteredActivities() {
            navigator.geolocation.getCurrentPosition((position) => {

                ActivityService.getFilteredActivities(
                    this.filterMethod,
                    this.name,
                    this.distance,
                    this.activityType,
                    position.coords.latitude,
                    position.coords.longitude
                ).then(result => {
                    this.activities = result;
                    this.clearMarkers();
                    this.populateMap();
                })
            })


        },
        initMap() {
            navigator.geolocation.getCurrentPosition((position) => {
                this.currentPosLat = position.coords.latitude;
                this.currentPosLong = position.coords.longitude;

                console.log(position)

                this.loadMap();

            })
        },
        loadMap() {

            const loader = new Loader({
                apiKey: '-',
                version: 'weekly',
            })

            loader.load()
                .then(() => {
                    this.populateMap()
                }).then(() => {
                    this.loadingMap = false;
                });

        },
        populateMap() {
            map = new google.maps.Map(document.getElementById("map"), {
                center: { lat: this.currentPosLat, lng: this.currentPosLong },
                zoom: 10,
            });
            var marker, i;

            for (i = 0; i < this.activities.length; i++) {
                this.markers[i] = new google.maps.Marker({
                    position: {
                        lat: this.activities[i].locationX,
                        lng: this.activities[i].locationY
                    },
                    map: map,
                });
            }
        },
        clearMarkers() {
            for (var i = 0; i < this.activities.length; i++) {
                this.markers[i].setMap(null);
                this.markers[i] = null;
            }
        },
        galleryButtonPressed(index) {
            this.activityOnModalGalleryID = index;
            this.galleryModalVisible = true;
            console.log("presssed")
        },
        showOnMapPressed(index) {

            map = new google.maps.Map(document.getElementById("map"), {
                center: { lat: this.activities[index].locationX, lng: this.activities[index].locationY },
                zoom: 15,
            });

            var marker, i;

            for (i = 0; i < this.activities.length; i++) {
                marker = new google.maps.Marker({
                    position: {
                        lat: this.activities[i].locationX,
                        lng: this.activities[i].locationY
                    },
                    map: map,
                });
            }
        },
        triggerModal(index) {
            this.modalTitle = this.activities[index].name;
            console.log(this.modalTitle);
            this.activityOnModalID = index;
            this.dateModalVisible = true;
        },
        addEvent() {
            ActivityService.addEvent(
                this.$store.state.auth.user.username,
                this.activityOnModalID,
                this.dateModalDate,
                this.dateModalTime)

            this.dateModalVisible = false;
        }
    }
}

</script>


<style scoped>
.filter-button {
    max-width: 200px;
    min-width: 100px;
}

.filter-input {
    max-width: 30em;
}

.pad-right {
    margin-right: 2em;
}

.pad-bott {
    margin-bottom: 3em;
}

#map {
    height: 400px;
    width: 100%;
}


</style>