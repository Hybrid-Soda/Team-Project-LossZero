import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useCounterStore = defineStore(
  "counter",
  () => {
    const targetCnt = ref(120);
    const normalCnt = ref(0);
    const sumNormal = ref(0);
    const sumReusable = ref(0);
    const sumDefective = ref(0);
    const totalCnt = ref(0);

    const DPO = computed(
      () =>
        totalCnt.value
          ? Math.ceil(
              100 * ((sumReusable.value + sumDefective.value) / totalCnt.value)
            )
          : 0 // 재확인
    );

    const OEE = computed(() =>
      Math.ceil(100 * ((60 * sumNormal.value) / 7200))
    );

    const Scrap = computed(() =>
      sumDefective.value + sumReusable.value > 0
        ? Math.ceil(
            100 *
              (sumDefective.value / (sumReusable.value + sumDefective.value))
          )
        : 0
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
      doughnutData.value = [
        sumNormal.value,
        targetCnt.value < sumNormal.value
          ? 0
          : targetCnt.value - sumNormal.value,
      ];
    }

    return {
      targetCnt,
      normalCnt,
      sumNormal,
      productData,
      sumReusable,
      sumDefective,
      totalCnt,
      DPO,
      OEE,
      Scrap,
      doughnutData,
      currentData,
    };
  },
  { persist: true }
);
