<template>
  <div class="flex gap-3 w-full">
    <!-- seat display -->
    <div class="w-2/3">
      <p class="text-3xl font-bold mb-2">員工座位表</p>
      <div>
        <Floor
          v-for="floorNo in seats
            .map((s) => s.floorNo)
            .filter((value, index, array) => {
              return array.indexOf(value) === index
            })"
          :key="floorNo"
          :seats="
            seats.filter((seat) => {
              return seat.floorNo === floorNo
            })
          "
        />
        <div class="flex flex-col pt-2 gap-1">
          <div class="flex gap-1">
            <div class="w-7 aspect-5/4 rounded-md bg-gray-200"></div>
            空位
          </div>
          <div class="flex gap-1">
            <div class="w-7 aspect-5/4 rounded-md bg-red-600"></div>
            已占用
          </div>
          <div class="flex gap-1">
            <div class="w-7 aspect-5/4 rounded-md bg-green-300"></div>
            請選擇
          </div>
        </div>
        <div class="pt-2">
          <button
            class="w-10 aspect-8/5 text-white"
            :class="
              selectedSeat.floorSeatSeq && selectedEmployee
                ? 'bg-blue-800 cursor-pointer'
                : 'bg-blue-300 cursor-not-allowed'
            "
            @click="submit()"
          >
            送出
          </button>
        </div>
      </div>
    </div>

    <!-- employee display -->
    <div class="w-1/3">
      <p class="text-3xl font-bold mb-2">員工選擇</p>
      <select class="border-1 border-solid border-gray-400 rounded-sm" v-model="selectedEmployee">
        <option :value="undefined">-</option>
        <option v-for="emp in employees" :key="emp.empId" :value="emp.empId">
          [{{ emp.empId }}] {{ emp.name }}
        </option>
      </select>
      <div
        class="flex flex-col gap-1 pt-2"
        v-for="emp in employees.filter((emp) => {
          if (selectedEmployee) {
            return emp.empId === selectedEmployee
          }
        })"
        :key="emp.empId"
      >
        <div>員工編號: {{ emp.empId }}</div>
        <div>員工姓名: {{ emp.name }}</div>
        <div>員工電子郵件: {{ emp.email }}</div>
        <div>
          座位資訊: {{ emp.floorNo }}樓-座位{{ emp.seatNo }}
          {{
            selectedSeat.floorSeatSeq
              ? `-> ${selectedSeat.floorNo}樓-座位${selectedSeat.seatNo}`
              : ''
          }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, provide } from 'vue'
import { getAllEmployees, getAllSeats, updateEmployeeSeat } from './api.js'
import Floor from './components/Floor.vue'

const seats = reactive([])
const employees = reactive([])
const selectedSeat = reactive({ floorSeatSeq: 0 })
const selectedEmployee = defineModel()

provide('employees', employees)
provide('selectedSeat', selectedSeat)
provide('selectedEmployee', selectedEmployee)

getAllEmployees().then((resp) => {
  console.log(resp.message)
  if (resp.status === 'ok') {
    employees.push(...resp.data)
  }
})
getAllSeats().then((resp) => {
  console.log(resp.message)
  if (resp.status === 'ok') {
    seats.push(...resp.data)
  }
})

const submit = () => {
  if (selectedEmployee.value && selectedSeat.floorSeatSeq) {
    updateEmployeeSeat(selectedEmployee.value, selectedSeat.floorSeatSeq).then((resp) => {
      console.log(resp.message)
      if (resp.status === 'ok') {
        Object.assign(
          employees.find((emp) => {
            return emp.empId === resp.data.empId
          }),
          resp.data,
        )
        selectedSeat.floorSeatSeq = 0
      }
    })
  }
}
</script>
