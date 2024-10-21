<script setup></script>

<template>
  <div class="info-con">
    <img src="@/assets/img/profile.svg" alt="프로필" />
    <div class="info-t">
      <span class="name pre-t">강현성</span>
      <span class="position pre-t">Admin</span>
    </div>
  </div>
</template>

<style scoped>
.info-con {
  width: 120px;
  height: 100%;

  margin-left: 20px;

  display: flex;
  align-items: center;
  /* background-color: blue; */
}

.info-t {
  margin-left: 10px;
}

.position {
  color: rgb(163, 162, 162);
}
</style>
