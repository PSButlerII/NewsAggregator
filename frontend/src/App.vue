<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <HelloWorld msg="Welcome to Your Vue.js App"/>
  <NewsArticle/>
  <LatestNews/>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import NewsArticle from './components/NewsArticle.vue'
import LatestNews from './components/LatestNews.vue'
export default {
  name: 'App',
  components: {
    HelloWorld,
    NewsArticle, // Add the imported NewsArticle component here
    LatestNews
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
