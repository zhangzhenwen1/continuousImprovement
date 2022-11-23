<template>
  <div style="background:gray;">


        <div class="medium1">welcome ...</div>
        <div class="medium">IACAA - 2.0</div>

  </div>
</template>

<script>

export default {
  name: "Main"
}
</script>

<style scoped>
.medium1{
  font-size: 30px;
  color: white;
  font-family: STFangsong;
  padding-top: 5%;
  margin-left: 5%;
}
.medium{
  font-size: 80px;
  color: white;
  font-family: STFangsong;
  margin-top: 5%;
  margin-left: 35%;
}
.el-carousel__item:nth-child(2n) {
  background:-webkit-linear-gradient(#5200cd, #8901d4);
}

.el-carousel__item:nth-child(2n+1) {
  background:-webkit-linear-gradient(#0063d9, #01bd3e);
}
</style>
