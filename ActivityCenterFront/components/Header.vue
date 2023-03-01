<template>
  <nav class="navbar fixed-top navbar-expand-lg navbar-light">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Activity Center</a>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <a class="nav-link" aria-current="page" href="#">Home</a>
          </li>
          <li class="nav-item">
            <!-- <a class="nav-link active" aria-current="page" href="#">Login</a> -->
            <router-link class="nav-link" to="/login" v-if="!getLoginStatus">Login</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/register" v-if="!getLoginStatus">Register</router-link>
          </li>
          <li class="nav-item" v-if="getLoginStatus">
            <router-link class="nav-link" to="/explore">Explore</router-link>
          </li>
          <li class="nav-item" v-if="getLoginStatus">
            <router-link class="nav-link" to="/events">My Events</router-link>
          </li>
        </ul>
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
          <li class="nav-item" v-if="getLoginStatus">
            <router-link @click="logout" class="nav-link" to="/login">Log out</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>

    export default {
        name: 'Header',
        computed: {
          getLoginStatus() {
            return this.$store.state.auth.status.loggedIn;
          }
        },
        data() {
          return {
          }
        },
        methods: {
          logout() {
            this.$store.dispatch('auth/logout')
          }
        }
    }
</script>

<style scoped>


.navbar {
  background-color: rgb(255, 143, 143);
  padding: 0.0em;
}

.navbar .navbar-nav .nav-link{
  background-color: rgb(255, 143, 143);
  color: #fff;
  font-size: 1.1em;
  padding-top: 0.8em;
  padding-bottom: 0.8em;
  padding-left: 1.4em;
  padding-right: 1.4em;;
}

.navbar .navbar-nav .nav-item .nav-link:hover {
  background-color: #ffffff;
  color: rgb(255, 143, 143);
}

.navbar .navbar-brand {
  padding: 0 0.6em;
}
</style>