import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useOperateStore = defineStore("operate", () => {
  const machineOnOff = ref(false);
  const coveyerOnOff = ref(false);

  function machineOnOffFun() {
    machineOnOff.value = !machineOnOff.value;
    console.log(machineOnOff.value);
  }
  return { machineOnOff, coveyerOnOff, machineOnOffFun };
});
