<template>
  <div class="grid grid-cols-4 gap-2 py-1">
    <Seat v-for="seat in seats" :seat="seat" :key="seat.floorSeatSeq" />
  </div>
</template>
<script setup>
import Seat from './Seat.vue'
defineProps(['seats'])
</script>
