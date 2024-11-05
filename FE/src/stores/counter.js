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

    const DPO = computed(
      () =>
        totalCnt.value
          ? Math.ceil(
              100 * ((recycleCnt.value + faultyCnt.value) / totalCnt.value)
            )
          : 0 // 재확인
    );

    const productData = ref([
      sumNormal.value,
      sumReusable.value,
      sumDefective.value,
    ]);

    const doughnutData = ref([
      sumNormal.value,
      targetCnt.value - sumNormal.value,
    ]);

    function currentData(data) {
      // console.log(data);
      sumNormal.value = data.sumNormal;
      sumReusable.value = data.sumReusable;
      sumDefective.value = data.sumDefective;
      totalCnt.value = data.total;

      productData.value = [
        sumNormal.value,
        sumReusable.value,
        sumDefective.value,
      ];

      doughnutData.value = [sumNormal.value, targetCnt.value - sumNormal.value];
    }

    function issueProduct(data) {
      issue.value = !issue.value;
      normalCnt.value = Number(data.normal.replace(",", ""));
      recycleCnt.value = Number(data.reusable.replace(",", ""));
      faultyCnt.value = Number(data.defective.replace(",", ""));

      sumNormal.value += normalCnt.value;
      sumReusable.value += recycleCnt.value;
      sumDefective.value += faultyCnt.value;

      totalCnt.value = sumNormal.value + sumReusable.value + sumDefective.value;
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
      sumReusable,
      sumDefective,
      productData,
      recycleCnt,
      faultyCnt,
      totalCnt,
      issue,
      DPO,
      doughnutData,
      updateProductCnt,
      changeTargetCnt,
      updateLogDate,
      currentData,
      issueProduct,
    };
  },
  { persist: true }
);
