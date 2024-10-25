import { ref, computed } from "vue";
import { defineStore } from "pinia";
import LogTime from "@/components/sub-data/LogTime.vue";

export const useCounterStore = defineStore(
  "counter",
  () => {
    const logDate = ref(0);
    const targetCnt = ref(120);
    const productCnt = ref(0);
    const logData = ref([]);
    const nomalCnt = ref(0);
    const recycleCnt = ref(0);
    const faultyCnt = ref(0);

    const totalCnt = computed(
      () => nomalCnt.value + recycleCnt.value + faultyCnt.value
    );

    const DPO = computed(() =>
      Math.ceil(100 * ((recycleCnt.value + faultyCnt.value) / totalCnt.value))
        ? Math.ceil(
            100 * ((recycleCnt.value + faultyCnt.value) / totalCnt.value)
          )
        : 0
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

    function updateLogDate(date) {
      logDate.value = date;
    }

    function updateProductCnt(cnt, logTime) {
      console.log(typeof logDate.value.minutes);
      logData.value.unshift({
        logDate: `${logDate.value.period} ${logDate.value.hours
          .toString()
          .padStart(2, "0")}시 ${logDate.value.minutes
          .toString()
          .padStart(2, "0")}분`,
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
      logDate,
      targetCnt,
      productCnt,
      logData,
      nomalCnt,
      recycleCnt,
      faultyCnt,
      totalCnt,
      DPO,
      productData,
      doughnutData,
      updateProductCnt,
      changeTargetCnt,
      updateLogDate,
    };
  },
  { persist: true }
);
