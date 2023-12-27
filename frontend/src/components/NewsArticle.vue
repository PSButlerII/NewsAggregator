<template>
  <div class="container mt-4">
    <h1 class="mb-4">News Articles</h1>
    <div class="row">
      <div class="col-md-4" v-for="article in articles" :key="article.id">
        <div class="card mb-3">
          <div class="card-body">
            <h5 class="card-title">{{ article.title }}</h5>
            <p class="card-text">{{ article.content }}</p>
            <button class="btn btn-primary" @click="openArticle(article.url)">Read More</button>
            <button class="btn btn-success" @click="saveArticle(article)">Save</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<script>
import axios from 'axios';

export default {
  data() {
    return {
      articles: []
    };
  },
  mounted() {
    this.fetchArticles();
  },
  methods: {
    fetchArticles() {
      axios.get('http://localhost:8080/api/news/external')
        .then(response => {
          this.articles = response.data;
        })
        .catch(error => {
          console.error('Axios fetch failed, trying Fetch API', error);
          this.fetchArticlesWithFetchAPI();
        });
    },
    saveArticle(article) {
      axios.post('http://localhost:8080/api/news/addArticle', article)
        .then(response => {
          console.log('Article saved', response.data);
          // Optionally, perform additional actions like notifying the user
        })
        .catch(error => {
          console.error('Error saving article', error);
          // Handle errors, such as displaying a message to the user
        });
    },
    openArticle(url) {
      window.open(url, '_blank');
    },
    fetchArticlesWithFetchAPI() {
      fetch('http://localhost:8080/api/news/external')
        .then(response => response.json())
        .then(data => {
          this.articles = data;
        })
        .catch(error => {
          console.error('Fetch API failed', error);
        });
    }
  }
};
</script>
