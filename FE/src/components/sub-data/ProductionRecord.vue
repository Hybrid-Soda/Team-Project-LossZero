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
      <div class="legend-box">
        <ul>
          <li>
            <span
              class="legend-color"
              style="background-color: rgb(54, 162, 235)"
            ></span>
            정상품: {{ productionData[0] }}개
          </li>
          <li>
            <span
              class="legend-color"
              style="background-color: rgb(255, 99, 132)"
            ></span>
            불량품: {{ productionData[1] }}개
          </li>
          <li>
            <span
              class="legend-color"
              style="background-color: rgb(255, 159, 64)"
            ></span>
            재사용: {{ productionData[2] }}개
          </li>
        </ul>
      </div>

      <!-- Bar chart 캔버스 -->
      <div class="chart-wrapper">
        <canvas id="myBarChart"></canvas>
        <div class="chart-text">합계 : {{ total }}개</div>
      </div>

      <!-- Donut 차트 캔버스 -->
      <div class="donut-chart-wrapper">
        <canvas id="myDonutChart"></canvas>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Chart from "chart.js/auto"; // Chart.js 자동 임포트

const total = ref(0); // 합계를 저장할 변수
const productionData = [60, 30, 10]; // 데이터 값

onMounted(() => {
  const barCanvas = document.getElementById("myBarChart");
  const donutCanvas = document.getElementById("myDonutChart");

  // 데이터의 합계를 계산하여 total에 저장
  total.value = productionData.reduce((a, b) => a + b, 0);

  // Bar Chart 생성
  if (barCanvas) {
    const ctx = barCanvas.getContext("2d");
    new Chart(ctx, {
      type: "bar",
      data: {
        labels: ["정상품", "불량품", "재사용가능"],
        datasets: [
          {
            label: "생산량",
            data: productionData,
            backgroundColor: [
              "rgb(54, 162, 235)",
              "rgb(255, 99, 132)",
              "rgb(255, 159, 64)",
            ],
            borderWidth: 0,
          },
        ],
      },
      options: {
        maintainAspectRatio: false,
        plugins: {
          legend: { display: false },
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
    new Chart(ctx, {
      type: "doughnut",
      data: {
        labels: ["완료", "미완료"],
        datasets: [
          {
            data: [30, 120 - 30],
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
  height: 180px;
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
  left: -25%;
  transform: translate(-50%, -50%);
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  color: black;
  line-height: 1.5;
  z-index: 0;
}
</style>
