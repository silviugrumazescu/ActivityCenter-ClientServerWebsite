<template>
  <div class="container">
    <header>
      <Header />
    </header>
    
    <body>
      <router-view></router-view>
    </body>

  </div>
</template>

<script>
  import Header from './components/Header'

  export default {
    name: 'App',
    components: {
      Header,
    }
  }
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

body { padding-top: 50px; }

</style>
