<script setup>
import { ref } from 'vue'
import TopNavbar from '../../components/TopNavbar.vue';

defineProps({
  msg: String,
})

const count = ref(0)
</script>

<template>
    <TopNavbar />
    <h1>Admin</h1>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
