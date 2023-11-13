<script setup>
import TopNavbar from '../../../components/TopNavbar.vue';
import Footer from '../../../components/Footer.vue';

</script>

<template src="./sign-in.html"></template>
<style scoped src="./sign-in.css"></style>

<script>
import authService from '../../../services/AuthService.js';
import {useRouter} from 'vue-router'
export default {
  name: "Login",

  components: {
    TopNavbar,
    Footer
  },
  setup(){

    const router = useRouter();

    const data = reactive({
      email:'',
      password:''
    });

    const submit = async () => {
      var response = await authService.login(data);

      await router.push("/");
    }

    return {
      data,
      submit
    }
  }
};
</script>