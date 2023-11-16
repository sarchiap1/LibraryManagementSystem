import { createApp } from 'vue'
import App from './App.vue'
import store from './lib/store.js'
import router from './lib/router.js'


router.beforeEach((to, from, next) => {
    
    console.log("Before to:",to);
    console.log("Before from",from);
    console.log("Before next",next);
    next();
  });
  

const app = createApp(App)
app.use(router).use(store).mount('#app')