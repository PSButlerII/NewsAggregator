import { createRouter, createWebHistory } from 'vue-router';
import HelloWorld from '../components/HelloWorld.vue';
import NewsArticle from '../components/NewsArticle.vue';
import LatestNews from '../components/LatestNews.vue';
import NewsSearch from '../components/NewsSearch.vue';

const routes = [
  { path: '/', component: HelloWorld },
  { path: '/news-article', component: NewsArticle },
  { path: '/latest-news', component: LatestNews },
  { path: '/news-search', component: NewsSearch },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
