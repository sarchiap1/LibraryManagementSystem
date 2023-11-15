<template>
  <div class="wrapper">
    <TopNavbar />
  
    <main class="form-signin">
      <form @submit.prevent="submit">
        <h1 class="h3 mb-3 fw-normal">Please sign in</h1>
  
        <div class="form-floating">
          <input v-model="user.email" type="email" class="form-control" id="floatingInput" placeholder="name@example.com">
          <label for="floatingInput">Email address</label>
        </div>
        <div class="form-floating">
          <input v-model="user.password" type="password" class="form-control" id="floatingPassword" placeholder="Password">
          <label for="floatingPassword">Password</label>
        </div>
  
        <button class="w-100 btn btn-lg btn-primary" type="submit">Sign in</button>
        <p class="mt-5 mb-3 text-muted"></p>
      </form>
    </main>
  
    <Footer />
  </div>
</template>
<style scoped src="./sign-in.css"></style>

<script setup>
import authService from '../../../services/AuthService.js';

import TopNavbar from '../../../components/TopNavbar.vue';
import Footer from '../../../components/Footer.vue';

import { ref, toRaw } from "vue";
import { useRouter } from 'vue-router';

const router = useRouter();

const user = ref({
      email: "",
      password: "",
    });

function submit(){
  authService.login(toRaw(user.value));

  router.push("/");
}

</script>