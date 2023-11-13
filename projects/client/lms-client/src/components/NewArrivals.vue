<script setup>
import {useCatalog} from '../store/catalog.js'
</script>

<template>

</template>

<style scoped></style>
<script>
import { mapState, mapActions } from 'pinia'

export default {
  computed: {
    ...mapState(useCatalog, {newArrivals: 'results'})
  },

  methods: {
    ...mapActions(useCatalog, ['fetchNewArrivals']),

    addToCart() {
      // we'll populate this later
    }
  },

  created() {
    // when the template is created, we call this action
    this.fetchNewArrivals();
  }
};
</script>
