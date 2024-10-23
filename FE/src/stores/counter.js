import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCounterStore = defineStore(
  "counter",
  () => {
    const count = ref(0);
    const doubleCount = computed(() => count.value * 2);

    function increment() {
      count.value++;
    }

    const productCnt = ref(0);
    const logData = ref([]);

    function updateProductCnt(cnt, logTime) {
      logData.value.unshift({
        idx: logTime.idx,
        nomal: logTime.nomal,
        recycle: logTime.recycle,
        faulty: logTime.faulty,
      });

      productCnt.value += cnt;
      console.log(productCnt.value);
    }

    return { productCnt, logData, updateProductCnt };
  },
  { persist: true }
);
