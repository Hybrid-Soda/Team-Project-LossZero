<template>
  <div class="weekly-record-con con shadow">
    <!-- 아이콘과 텍스트 영역 -->
    <div class="icon-text">
      <div class="icon-box">
        <img src="@/assets/img/Vector.svg" alt="Icon" class="icon-img" />
      </div>
      <h2 class="header-text">주간 기록</h2>
    </div>

    <!-- 차트를 그릴 캔버스 요소 -->
    <canvas id="myChart2"></canvas>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import { weeklyProduct } from "@/api/data";
import Chart, { elements } from "chart.js/auto"; // Chart.js 자동 임포트
import { useCounterStore } from "@/stores/counter";

const values = ref([]);
const labels = ref([]);
const productData = ref([]);
const oeeData = ref([]);
const cntStore = useCounterStore();
let myChart2 = null;

// 랜덤 값 생성 함수
function getRandomValues(min, max, count) {
  const values = [];
  for (let i = 0; i < count; i++) {
    values.push(Math.floor(Math.random() * (max - min + 1)) + min);
  }
  return values;
}

onMounted(async () => {
  await weeklyProduct()
    .then((res) => {
      console.log(res.data);
      values.value = res.data;
      labels.value = values.value.map((element) => {
        return element.date;
      });
      productData.value = values.value.map((element) => {
        return element.sumNormal;
      });
      oeeData.value = values.value.map((element) => {
        return Math.ceil(100 * ((60 * element.sumNormal) / 7200));
      });
    })
    .catch((err) => {
      console.log(err);
    });

  const canvas = document.getElementById("myChart2");

  if (canvas) {
    const ctx = canvas.getContext("2d");

    // 차트 생성
    myChart2 = new Chart(ctx, {
      type: "bar", // 메인 타입을 막대형으로 설정
      data: {
        labels: labels.value,
        datasets: [
          {
            label: "생산량",
            type: "bar", // 생산량은 막대 차트로 설정
            data: productData.value, // 생산량 데이터
            fill: true, // 배경 채우기
            backgroundColor: "rgba(85, 42, 254, 0.7)", // 막대 배경 색상
            backgroundColor: "rgba(237, 235, 253, 0.7)", // 배경 색상
            tension: 0.4, // 곡선 스무딩
            borderColor: "rgba(85, 42, 254, 1)", // 막대 테두리 색상
            borderWidth: 2, // 막대 테두리 두께
            yAxisID: "y", // 왼쪽 Y축
          },
          {
            label: "설비 종합 효율",
            type: "line", // 설비 종합 효율은 라인 차트로 유지
            data: oeeData.value, // 랜덤한 설비 종합 효율 데이터
            fill: true, // 배경 채우기
            borderColor: "rgba(255, 34, 34, 0.775)", // 라인 색상
            backgroundColor: "rgba(25, 118, 210, 0.1)", // 배경 색상
            tension: 0.4, // 곡선 스무딩
            pointBackgroundColor: "white", // 포인트 색상
            pointBorderWidth: 2, // 포인트 테두리 너비
            borderWidth: 3, // 라인 두께
            yAxisID: "y1", // 오른쪽 Y축
          },
        ],
      },
      options: {
        maintainAspectRatio: false, // 비율 유지하지 않음
        scales: {
          y: {
            beginAtZero: true, // 왼쪽 Y축 0부터 시작
            position: "left", // 왼쪽 Y축
            min: 0,
            max: Math.max(productData.value) + 50, // 생산량의 최대값에 맞춘 범위,
            ticks: {
              font: {
                size: 12,
                family: "Pretendard-Regular",
              },
            },
          },
          y1: {
            beginAtZero: false, // 오른쪽 Y축 60부터 시작
            position: "right", // 오른쪽 Y축
            min: 50,
            max: 150, // 설비 종합 효율의 최대값에 맞춘 범위
            ticks: {
              font: {
                size: 12,
                family: "Pretendard-Regular",
              },
            },
          },
          x: {
            ticks: {
              font: {
                size: 12,
                family: "Pretendard-Regular",
              },
            },
          },
        },
        plugins: {
          legend: {
            display: true, // 범례 표시
            labels: {
              font: {
                size: 12,
                family: "Pretendard-Regular",
              },
            },
          },
          tooltip: {
            // 툴팁 옵션
            enabled: true, // 툴팁을 활성화
            mode: "index", // x축 위에 있는 모든 요소를 표시
            intersect: false, // 교차하지 않는 데이터도 표시
            callbacks: {
              label: function (tooltipItem) {
                if (tooltipItem.dataset.label === "생산량") {
                  return `생산량: ${tooltipItem.raw}`;
                } else if (tooltipItem.dataset.label === "설비 종합 효율") {
                  return `효율: ${tooltipItem.raw}%`;
                }
              },
            },
          },
        },
      },
    });
  } else {
    console.error("Canvas element not found!");
  }
});
</script>

<style scoped>
.header-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.icon-text {
  display: flex;
  align-items: center;
  /* margin-bottom: 10px; */
}

.icon-box {
  width: 40px;
  height: 40px;
  background-color: rgba(25, 118, 210, 1); /* 보라색 사각형 */
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 8px; /* 모서리를 둥글게 */
}

.icon-img {
  width: 24px; /* 아이콘 크기 조절 */
  height: 24px;
}

.header-text {
  font-size: 20px;
  margin-left: 10px;
  font-weight: bold;
}

.weekly-record-con {
  padding: 10px;
  width: 100%;
  margin-top: 20px;
  height: 280px; /* 높이를 명시적으로 설정 */
  position: relative;
}

canvas {
  width: 100% !important; /* 캔버스 크기 조정 */
  height: 85% !important; /* 캔버스 크기 조정 */
}
</style>
