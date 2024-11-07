import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useOperateStore = defineStore("operate", () => {
  const machineOnOff = ref(false);
  const coveyerOnOff = ref(false);
  const armOnOff = ref(false);

  function machineOnOffFun() {
    machineOnOff.value = !machineOnOff.value;
    console.log(machineOnOff.value);
  }

  function armOn() {
    armOnOff.value = true;
    // console.log(armOnOff.value);
  }

  function armOff() {
    armOnOff.value = false;
    // console.log(armOnOff.value);
  }

  function coveyerOn() {
    // coveyerOnOff.value = true;
    console.log(coveyerOnOff.value);
  }

  function coveyerOff() {
    // coveyerOnOff.value = false;
    console.log(coveyerOnOff.value);
  }

  return {
    armOnOff,
    machineOnOff,
    coveyerOnOff,
    machineOnOffFun,
    armOn,
    armOff,
    coveyerOn,
    coveyerOff,
  };
});
