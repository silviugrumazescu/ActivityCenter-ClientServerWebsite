<template>
    <div class="card shadow-0 border rounded-3">
        <div class="card-body">
            <div class="row">
                <div class="col-md-12 col-lg-3 col-xl-3 mb-4 mb-lg-0">
                    <div class="bg-image hover-zoom">
                        <img :src="imgLink"
                            class="w-100 hover-shadow" />
                    </div>
                </div>
                <div class="col-md-6 col-lg-6 col-xl-6">
                    <h4><b>{{title}}</b></h4>
                    <p class="text mb-4 mb-md-0">{{description}}</p>
                </div>
                <div class="col-md-6 col-lg-3 col-xl-3 border-sm-start-none border-start">
                    <div class="d-flex flex-row align-items-center mb-1">
                        <h4 class="mb-1 me-1">{{openHours}}</h4>
                    </div>
                    <h6 class="text-success">08:00-17:00</h6>
                    <div class="d-flex flex-column mt-4">
                        <button @click="$emit('galleryEvent', id)" class="btn btn-primary btn-sm" type="button">Galerie</button>
                        <button @click="$emit('addToCalendarEvent', id)" class="btn btn-outline-primary btn-sm mt-2" type="button">Adauga in calendar</button>
                        <button @click="$emit('showOnMapEvent', id)" class="btn btn-outline-primary btn-sm mt-2" type="button">Arata pe harta</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'ActivityCard',
    props: {
        id : Number,
        title: String,
        description: String,
        openHours: String,
        imgLink: String,

    }
}

</script>

<style scoped>

</style>