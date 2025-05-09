<template>
  <div
    class="flex justify-center items-center aspect-5/1 rounded-md font-bold"
    :class="
      getEmployee(seat)
        ? `bg-red-600 text-white hover:cursor-not-allowed ${getEmployee(seat).empId === selectedEmployee ? 'border-3 border-dashed border-black' : ''}`
        : seat.floorSeatSeq === selectedSeat.floorSeatSeq
          ? 'bg-green-300 hover:cursor-pointer'
          : 'bg-gray-200 hover:cursor-pointer'
    "
    @click="setSelected(seat)"
  >
    {{ seat.floorNo }}樓: 座位{{ seat.seatNo }}
    {{ getEmployee(seat) ? `[員編:${getEmployee(seat).empId}]` : '' }}
  </div>
</template>
<script setup>
import { inject } from 'vue'
defineProps(['seat'])

const employees = inject('employees')
const selectedSeat = inject('selectedSeat')
const selectedEmployee = inject('selectedEmployee')

const getEmployee = (seat) => {
  return employees.find((emp) => {
    return emp.floorNo === seat.floorNo && emp.seatNo === seat.seatNo
  })
}

const setSelected = (seat) => {
  if (!getEmployee(seat)) {
    // clear
    if (selectedSeat) {
      if (selectedSeat.floorSeatSeq === seat.floorSeatSeq) {
        selectedSeat.floorSeatSeq = 0
      } else {
        Object.assign(selectedSeat, seat)
      }
    } else {
      Object.assign(selectedSeat, seat)
    }
  }
}
</script>
