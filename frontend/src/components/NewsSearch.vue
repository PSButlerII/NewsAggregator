<template>
  <div>
    <form @submit.prevent="fetchNews">
      <input type="text" v-model="searchParams.query" :class="{ 'is-invalid': !validation.queryValid }" placeholder="Search Term"/>
      <input type="text" v-model="searchParams.searchIn"  placeholder="Search In"/>
      <input type="text" v-model="searchParams.domains" placeholder="Domains"/>
      <input type="text" v-model="searchParams.excludeDomains" placeholder="Exclude Domains"/>
      <input type="date" v-model="searchParams.from" placeholder="From"/>
      <input type="date" v-model="searchParams.to" placeholder="To"/>
      <input type="text" v-model="searchParams.language"  placeholder="Language"/>
      <input type="text" v-model="searchParams.sortBy"  placeholder="Sort By"/>
      <input type="text" v-model="searchParams.pageSize"  placeholder="Page Size"/>
      <!-- Add other inputs for each field -->
      <div v-if="!validation.queryValid" class="error-message">Query is required</div>
      <button type="submit">Search</button>
      <button type="button" @click="clear">Clear</button>
    </form>

    <div class="container mt-4" v-if="articles.length > 0">
          <h1 class="mb-4">News Articles</h1>
          <div class="row">
            <div class="col-md-4" v-for="article in articles" :key="article.url">
              <div class="card mb-3">
                <img :src="article.urlToImage" class="card-img-top" alt="Article Image">
                <div class="card-body">
                  <h5 class="card-title">{{ article.title }}</h5>
                  <p class="card-text">{{ article.content }}</p>
                  <p>Published at: {{ article.publishedAt }}</p>
                  <p>Author: {{ article.author }}</p>
                  <p>Source: {{ article.source ? article.source.name : 'Unknown' }}</p>
                  <a :href="article.url" class="btn btn-primary" target="_blank">Read More</a>
                  <!-- Add the Save button if needed -->
                </div>
              </div>
            </div>
          </div>
        </div>
<!-- Pagination controls -->
    <div class="pagination-controls">
      <button class="btn btn-secondary" @click="previousPage" :disabled="isFirstPage">Previous</button>
      <span>Page {{ searchParams.page }} of {{ totalPages }}</span>
      <button class="btn btn-secondary" @click="nextPage" :disabled="isLastPage">Next</button>
    </div>
      </div>
</template>


<script>
import axios from 'axios'; // Make sure to import axios

export default {
  data() {
    return {
      // Define searchParams with all necessary properties
      searchParams: {
        query: '',
        searchIn: '',
        domains: '',
        excludeDomains: '',
        from: null,
        to: null,
        language: '',
        sortBy: '',
        pageSize: 20, // Default value
        page: 1, // Default value
      },

      articles: [],
      totalPages: 0,
            loading: false, // New state for loading
      validation: {
       queryValid: true,
       // If you want to validate other fields, add flags here
     },

    };
    },
    computed: {
        isFirstPage() {
          return this.searchParams.page === 1;
        },
        isLastPage() {
          return this.searchParams.page === this.totalPages;
        },
  },
  methods: {

    formatDates() {
      if (this.searchParams.from) {
        // Assuming from is in a format that can be parsed to a Date
        this.searchParams.from = this.formatDate(this.searchParams.from);
      }
      if (this.searchParams.to) {
        // Assuming to is in a format that can be parsed to a Date
        this.searchParams.to = this.formatDate(this.searchParams.to);
      }
    },
  validateQuery() {
      this.validation.queryValid = this.searchParams.query.trim().length > 0;
    },
formatDate(dateStr) {
    const date = new Date(dateStr);
    if (!isNaN(date.getTime())) {
      // Format date to YYYY-MM-DD
      return date.toISOString().split('T')[0];
    }
    return null;
  },
    fetchNews() {
      this.validateQuery();

      if (!this.validation.queryValid) {
        console.error("Invalid input");
        return;
      }
    let params = { ...this.searchParams };
      if (!params.from) {
        delete params.from;
      }
      if (!params.to) {
        delete params.to;
      }
       const queryString = new URLSearchParams(params).toString();

        axios.get(`http://localhost:8080/api/news/fetch?${queryString}`)
        .then(response => {
          this.articles = response.data;
        })
        .catch(error => {
          console.error('Error fetching news', error);
        });
    },

    clear() {
      this.searchParams = {
        query: '',
        searchIn: '',
        domains: '',
        excludeDomains: '',
        from: null,
        to: null,
        language: '',
        sortBy: '',
        pageSize: 20, // Default value
        page: 1, // Default value
      };
      this.articles = [];
    },

    getMoreArticles() {
      this.searchParams.page++;
      this.fetchNews();
    },

  }
};
</script>

<style>
.is-invalid {
  border-color: red;
}

.error-message {
  color: red;
  font-size: 0.8em;
}
</style>
