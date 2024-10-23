import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCounterStore = defineStore(
  "counter",
  () => {
    const targetCnt = ref(120);
    const productCnt = ref(0);
    const logData = ref([]);
    const nomalCnt = ref(0);
    const recycleCnt = ref(0);
    const faultyCnt = ref(0);
    const totalCnt = computed(
      () => nomalCnt.value + recycleCnt.value + faultyCnt.value
    );

    const productData = ref([
      nomalCnt.value,
      recycleCnt.value,
      faultyCnt.value,
    ]);

    const doughnutData = ref([
      nomalCnt.value,
      targetCnt.value - nomalCnt.value,
    ]);

    function updateProductCnt(cnt, logTime) {
      logData.value.unshift({
        idx: logTime.idx,
        nomal: logTime.nomal,
        recycle: logTime.recycle,
        faulty: logTime.faulty,
      });

      nomalCnt.value += logTime.nomal;
      recycleCnt.value += logTime.recycle;
      faultyCnt.value += logTime.faulty;

      productData.value = [nomalCnt.value, recycleCnt.value, faultyCnt.value];
      doughnutData.value = [
        nomalCnt.value,
        targetCnt.value - nomalCnt.value < 0
          ? 0
          : targetCnt.value - nomalCnt.value,
      ];
      // console.log(doughnutData.value);
      productCnt.value += cnt;

      // console.log(productData.value);
      // console.log(productCnt.value);
    }

    function changeTargetCnt(cnt) {
      targetCnt.value = cnt;
      doughnutData.value = [
        nomalCnt.value,
        targetCnt.value - nomalCnt.value < 0
          ? 0
          : targetCnt.value - nomalCnt.value,
      ];
      // console.log(doughnutData.value);
    }

    return {
      targetCnt,
      productCnt,
      logData,
      nomalCnt,
      recycleCnt,
      faultyCnt,
      totalCnt,
      productData,
      doughnutData,
      updateProductCnt,
      changeTargetCnt,
    };
  },
  { persist: true }
);
