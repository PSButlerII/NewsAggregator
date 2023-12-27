<template>
  <div>
    <h1>Latest News</h1>
    <ul v-if="articles.length">
      <li v-for="article in articles" :key="article.id">
        <h3>{{ article.title }}</h3>
        <p>{{ article.content }}</p>
      </li>
    </ul>
    <p v-else>No news articles available.</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      articles: []
    };
  },
  created() {
    this.fetchNews();
  },
  methods: {
    async fetchNews() {
      try {
        let response = await this.$axios.get('/api/news/latest');
        this.articles = response.data;
      } catch (error) {
        console.error('Error fetching news:', error);
      }
    }
  }
};
</script>
