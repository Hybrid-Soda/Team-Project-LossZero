<script setup></script>

<template>
  <div class="info-con">
    <img src="@/assets/img/profile.svg" alt="프로필" class="profile-img" />
    <div class="info-t">
      <span class="name pre-t">이창호</span>
      <span class="position pre-t">Admin</span>
    </div>
  </div>
</template>

<style scoped>
.info-con {
  display: flex;
  align-items: center;
  margin-left: 20px;
  /* margin-right: 100px; */
}

.profile-img {
  width: 30px; /* 프로필 이미지 크기 조정 */
  height: 30px;
  object-fit: cover; /* 이미지 비율 유지 */
}

.info-t {
  display: flex;
  align-items: center;
  margin-left: 10px;
}

.name {
  margin-right: 5px; /* 이름과 Admin 사이 간격 */
}

.position {
  color: rgb(0, 0, 0);
  font-size: 0.8em; /* Admin 텍스트 크기 조정 */
}
</style>
