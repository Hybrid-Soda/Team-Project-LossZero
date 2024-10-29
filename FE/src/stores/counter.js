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
    const normalCnt = ref(0);
    const recycleCnt = ref(0);
    const faultyCnt = ref(0);
    const sumNormal = ref(0);
    const sumReusable = ref(0);
    const sumDefective = ref(0);
    const issue = ref(false);
    const totalCnt = ref(0);

    const DPO = computed(() =>
      Math.ceil(100 * ((recycleCnt.value + faultyCnt.value) / totalCnt.value))
        ? Math.ceil(
            100 * ((recycleCnt.value + faultyCnt.value) / totalCnt.value)
          )
        : 0
    );

    const productData = ref([
      normalCnt.value,
      recycleCnt.value,
      faultyCnt.value,
    ]);

    const doughnutData = ref([
      normalCnt.value,
      targetCnt.value - normalCnt.value,
    ]);

    function sseData(data) {
      // console.log(totalCnt.value, data.total);
      if (totalCnt.value !== data.total) {
        console.log("실시간!");
        normalCnt.value = data.normal;
        recycleCnt.value = data.reusable;
        faultyCnt.value = data.defective;
        issue.value = !issue.value;
        sumNormal.value = data.sumNormal;
        sumReusable.value = data.sumReusable;
        sumDefective.value = data.sumDefective;
        totalCnt.value = data.total;
      }
    }

    function updateLogDate(date) {
      logDate.value = date;
    }

    function updateProductCnt(cnt, logTime) {
      // console.log(typeof logDate.value.minutes);
      logData.value.unshift({
        logDate: `${logDate.value.period} ${logDate.value.hours
          .toString()
          .padStart(2, "0")}시 ${logDate.value.minutes
          .toString()
          .padStart(2, "0")}분`,
        normal: logTime.normal,
        recycle: logTime.recycle,
        faulty: logTime.faulty,
      });

      productData.value = [
        sumNormal.value,
        sumReusable.value,
        sumDefective.value,
      ];
      doughnutData.value = [
        sumNormal.value,
        targetCnt.value - sumNormal.value < 0
          ? 0
          : targetCnt.value - sumNormal.value,
      ];
      // console.log(doughnutData.value);
      productCnt.value = cnt;

      // console.log(productData.value);
      // console.log(productCnt.value);
    }

    function changeTargetCnt(cnt) {
      targetCnt.value = cnt;
      doughnutData.value = [
        normalCnt.value,
        targetCnt.value - normalCnt.value < 0
          ? 0
          : targetCnt.value - normalCnt.value,
      ];
      // console.log(doughnutData.value);
    }

    return {
      logDate,
      targetCnt,
      productCnt,
      logData,
      normalCnt,
      sumNormal,
      recycleCnt,
      faultyCnt,
      totalCnt,
      issue,
      DPO,
      productData,
      doughnutData,
      updateProductCnt,
      changeTargetCnt,
      updateLogDate,
      sseData,
    };
  },
  { persist: true }
);
