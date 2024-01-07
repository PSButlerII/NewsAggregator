<template>
  <nav class="app-navbar" style="background-color: red;">
    <ul>
      <li><router-link to="/">Home</router-link></li>
      <li><router-link to="/news-article">News Article</router-link></li>
      <li><router-link to="/latest-news">Latest News</router-link></li>
      <li><router-link to="/news-search">News Search</router-link></li>
    </ul>
  </nav>
</template>

<style>
.app-navbar ul {
  list-style-type: none;
  padding: 0;
  display: flex; /* Align items in a row */
  justify-content: space-around; /* Evenly space out links */
}
.app-navbar li {
  margin: 0 10px;
}

.app-navbar a.router-link-exact-active {
  color: #43b983; /* Highlight for the active link */
}
</style>

<script>
export default {
  name: 'AppNavbar'
}
</script>
