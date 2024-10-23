<template>
  <div class="production-record-con con shadow">
    <div class="icon-text">
      <div class="icon-box">
        <img
          src="@/assets/img/mingcute_warning-line.svg"
          alt="Icon"
          class="icon-img"
        />
      </div>
      <h2 class="header-text">생산 정보</h2>
    </div>

    <div class="chart-container">
      <!-- 범례 -->
      <div class="legend-box pre-t">
        <ul>
          <li>
            <span
              class="legend-color"
              style="background-color: rgb(54, 162, 235)"
            ></span>
            정상품: {{ cntStore.productData[0] }}개
          </li>
          <li>
            <span
              class="legend-color"
              style="background-color: rgb(255, 99, 132)"
            ></span>
            불량품: {{ cntStore.productData[2] }}개
          </li>
          <li>
            <span
              class="legend-color"
              style="background-color: rgb(255, 159, 64)"
            ></span>
            재사용: {{ cntStore.productData[1] }}개
          </li>
        </ul>
      </div>

      <!-- Bar chart 캔버스 -->
      <div class="chart-wrapper">
        <canvas id="myBarChart"></canvas>
        <div class="chart-text pre-t">합계 : {{ cntStore.totalCnt }}개</div>
      </div>

      <!-- Donut 차트 캔버스 -->
      <div class="donut-chart-wrapper">
        <canvas id="myDonutChart"></canvas>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import Chart from "chart.js/auto"; // Chart.js 자동 임포트
import { useCounterStore } from "@/stores/counter";

const cntStore = useCounterStore();

watch(
  () => [cntStore.totalCnt, cntStore.targetCnt],
  () => {
    // console.log("이슈!");
    myBarChart.data.datasets[0].data = cntStore.productData;
    myDonutChart.data.datasets[0].data = cntStore.doughnutData;
    myBarChart.update();
    myDonutChart.update();
  }
);

let myBarChart = null;
let myDonutChart = null;

onMounted(() => {
  const donutCanvas = document.getElementById("myDonutChart");
  const barCanvas = document.getElementById("myBarChart");
  // Bar Chart 생성
  if (barCanvas) {
    const ctx = barCanvas.getContext("2d");
    myBarChart = new Chart(ctx, {
      type: "bar",
      data: {
        labels: ["정상품", "재사용가능", "불량품"],
        datasets: [
          {
            label: "생산량",
            data: cntStore.productData,
            backgroundColor: [
              "rgb(54, 162, 235)",
              "rgb(255, 159, 64)",
              "rgb(255, 99, 132)",
            ],
            borderWidth: 0,
          },
        ],
      },
      options: {
        maintainAspectRatio: false,
        scales: {
          x: {
            ticks: {
              font: {
                size: 12,
                family: "Pretendard-Regular",
              },
            },
          },
          y: {
            ticks: {
              font: {
                size: 13,
                family: "Pretendard-Regular",
              },
            },
          },
        },
        plugins: {
          legend: {
            display: false,
            labels: {
              font: { size: 13, family: "Pretendard-Regular" },
            },
          },
          tooltip: {
            callbacks: {
              label: function (tooltipItem) {
                return `${tooltipItem.label}: ${tooltipItem.raw}개`;
              },
            },
          },
        },
      },
    });
  }
  // Donut Chart 생성 (30/120 비율)
  if (donutCanvas) {
    const ctx = donutCanvas.getContext("2d");
    myDonutChart = new Chart(ctx, {
      type: "doughnut",
      data: {
        labels: ["완료", "미완료"],
        datasets: [
          {
            data: cntStore.doughnutData,
            backgroundColor: ["rgb(54, 162, 235)", "rgb(200, 200, 200)"],
            hoverBackgroundColor: ["rgb(54, 162, 235)", "rgb(220, 220, 220)"],
          },
        ],
      },
      options: {
        cutout: "70%", // Donut 차트 내부 비율 조정
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: true, // 범례 표시
            labels: {
              font: { size: 13, family: "Pretendard-Regular" },
            },
          },
        },
      },
    });
  }
});
</script>

<style scoped>
.production-record-con {
  padding: 10px;
  width: 100%;
  height: 240px;

  background-color: #fff;
  border-radius: 8px;
}

.icon-text {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.icon-box {
  width: 40px;
  height: 40px;
  background-color: rgba(255, 74, 74, 1);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8px;
}

.icon-img {
  width: 24px;
  height: 24px;
}

.header-text {
  font-size: 20px;
  margin-left: 10px;
  font-weight: bold;
}

.chart-container {
  display: flex;
  align-items: flex-start;
  height: 100%;
}

.legend-box {
  width: 25%;
  padding-top: 20px;
  padding-left: 20px;
}

.legend-box ul {
  list-style: none;
  padding: 0;
}

.legend-box li {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.legend-color {
  width: 15px;
  height: 15px;
  display: inline-block;
  margin-right: 10px;
}

.chart-wrapper {
  position: relative;
  width: 50%; /* 차트의 너비 조정 */
  height: 170px;
  padding-right: 20px;
}

.donut-chart-wrapper {
  position: relative;
  width: 30%; /* 도넛 차트 너비 조정 */
  height: 180px;
  bottom: 20px;
}

canvas {
  width: 100% !important;
  height: 100% !important;
  z-index: 1;
}

.chart-text {
  position: absolute;
  top: 80%;
  left: -30%;
  transform: translate(-50%, -50%);
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  color: black;
  line-height: 1.5;
  z-index: 0;
}
</style>
