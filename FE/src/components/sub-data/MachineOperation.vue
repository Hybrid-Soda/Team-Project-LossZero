<script setup>
import Conveyer from "@/components/sub-data/machine/Conveyer.vue";
import RobotArm from "@/components/sub-data/machine/RobotArm.vue";
import Camera from "@/components/sub-data/machine/Camera.vue";
</script>

<template>
  <div class="operate-con box-row">
    <Conveyer />
    <Camera />
    <RobotArm />
  </div>
</template>

<style scoped>
.operate-con {
  width: 100%;
  height: 250px;
  margin-top: 20px;
  /* background-color: aqua; */

  justify-content: space-around;
}

.machine-con {
  width: 30%;
  height: 90%;
}
</style>
