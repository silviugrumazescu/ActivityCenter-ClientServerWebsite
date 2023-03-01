<template>
    <div class="row align-items-center">
        <div class="col">
            <h5 class="mb-1 me-1">{{this.time}}</h5>
        </div>
        <div class="col-5">
            <h5 class="mb-1 me-1">{{this.name}}</h5>
        </div>
        <div class="col d-flex justify-content-end">
            <button @click="$emit('deleteEvent', id, date)" class="btn btn-danger btn-sm ms-2" type="button">Delete</button>
        </div>
    </div>
</template>

<script>
export default {
    name: 'EventCard',
    props: {
        name: String,
        time: String,
        date: String,
        id : Number
    }
}

</script>

<style scoped>
.row {
    padding: 0.5em;

}
</style>