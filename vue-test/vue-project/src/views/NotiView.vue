<template>
  <div><h1>TEST</h1></div>
  <div>isConnected : {{ initConnected }}</div>
  <button @click="cancelOrder">주문 취소</button>
  <div>결과 : {{ resData }}</div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

let initConnected = ref('dummy connected')
let resData = ref('before notification')

const sse = new EventSource('http://localhost:8080/connect')

sse.addEventListener('connect', (e) => {
  const { data: receivedConnectData } = e
  console.log('connect event data: ', receivedConnectData) // "connected!"
  initConnected.value = receivedConnectData
})

const cancelOrder = () => {
  axios
    .get('http://localhost:8080/order')
    .then((res) => {
      console.log(res)
    })
    .catch((e) => {
      console.log(e)
    })
}

sse.addEventListener('orderUpdate', function (event) {
  console.log(event.data) // 서버로부터 받은 데이터 출력
  resData.value = event.data
})
</script>

<style lang="scss" scoped></style>
