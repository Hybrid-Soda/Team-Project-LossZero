import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useOperateStore = defineStore(
  "operate",
  () => {
    const machineOnOff = ref(false);
    const coveyerOnOff = ref(false);
    const armOnOff = ref(false);
    const cameraOnOff = ref(false);

    function machineOnOffFun() {
      machineOnOff.value = !machineOnOff.value;
      console.log(machineOnOff.value);
    }

    function armOn() {
      if (machineOnOff.value) {
        armOnOff.value = true;
      }
      // console.log(armOnOff.value);
    }

    function armOff() {
      armOnOff.value = false;
      // console.log(armOnOff.value);
    }

    function coveyerOn() {
      if (machineOnOff.value) {
        coveyerOnOff.value = true;
      }
      // console.log(coveyerOnOff.value);
    }

    function coveyerOff() {
      coveyerOnOff.value = false;
      // console.log(coveyerOnOff.value);
    }

    function cameraOn() {
      if (machineOnOff.value) {
        cameraOnOff.value = true;
      }
      // console.log(cameraOnOff.value);
    }

    function cameraOff() {
      cameraOnOff.value = false;
      // console.log(cameraOnOff.value);
    }

    return {
      armOnOff,
      coveyerOnOff,
      cameraOnOff,
      machineOnOff,
      machineOnOffFun,
      armOn,
      armOff,
      coveyerOn,
      coveyerOff,
      cameraOn,
      cameraOff,
    };
  },
  { persist: true }
);
