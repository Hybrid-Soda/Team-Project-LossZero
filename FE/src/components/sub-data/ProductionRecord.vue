<template>
  <div class="production-record-con con shadow">
    <div class="icon-text">
      <div class="icon-box">
        <img src="@/assets/img/mingcute_warning-line.svg" alt="Icon" class="icon-img" />
      </div>
      <h2 class="header-text">생산 정보</h2>
    </div>

    <!-- 차트 컨테이너 -->
    <div class="chart-container">
      <!-- 범례가 들어가는 부분 -->
      <div class="legend-box">
        <ul>
          <li>
            <span class="legend-color" style="background-color: rgb(54, 162, 235);"></span>
            정상품: {{ (productionData[0])}}개
          </li>
          <li>
            <span class="legend-color" style="background-color: rgb(255, 99, 132);"></span>
            불량품: {{ (productionData[1])}}개
          </li>
          <li>
            <span class="legend-color" style="background-color: rgb(255, 159, 64);"></span>
            재사용: {{ (productionData[2])}}개
          </li>
        </ul>
      </div>
      <!-- 차트 캔버스 및 중앙 텍스트 -->
      <div class="chart-wrapper">
        <canvas id="myChart"></canvas>
        <div class="chart-text">
          합계 : {{ total }}개
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Chart from 'chart.js/auto';  // Chart.js 자동 임포트

const total = ref(0);  // 합계를 저장할 변수
const productionData = [60, 30, 10];  // 데이터 값

onMounted(() => {
  const canvas = document.getElementById('myChart');

  // 데이터의 합계를 계산하여 total에 저장
  total.value = productionData.reduce((a, b) => a + b, 0);

  if (canvas) {
    const ctx = canvas.getContext('2d');

    // 차트 생성
    new Chart(ctx, {
      type: 'bar',  // 도넛 차트로 설정
      data: {
        labels: ['정상품', '불량품', '재사용가능'],
        datasets: [
          {
            label: '생산량',
            data: productionData, // 데이터 값
            backgroundColor: [
              'rgb(54, 162, 235)', // 파랑
              'rgb(255, 99, 132)', // 빨강
              'rgb(255, 159, 64)'  // 주황
            ],
            borderWidth: 0  // 경계선 제거
          }
        ]
      },
      options: {
        cutout: '75%',  // 중앙 부분을 더 넓게 비워 원형을 도넛 형태로 만듦
        maintainAspectRatio: false,  // 차트 비율 유지 안함
        plugins: {
          legend: {
            display: false  // 범례 표시 끔 (우리가 따로 만듦)
          },
          tooltip: {
            enabled: true,  // 툴팁 활성화
            callbacks: {
              label: function(tooltipItem) {
                // 툴팁에 데이터를 추가 (소수점 2자리로 백분율 표시)
                return `${tooltipItem.label}: ${(tooltipItem.raw)}개`;
              }
            }
          }
        }
      }
    });
  } else {
    console.error("Canvas element not found!");
  }
});
</script>

<style scoped>
.production-record-con {
  padding: 10px;
  width: 100%;
  height: 240px;  /* 높이를 고정 */
  margin-top: 20px;
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
  display: flex; /* 차트와 범례를 가로로 배치 */
  align-items: flex-start; /* 차트와 범례를 상단에 맞춤 */
  height: 100%;
}

.legend-box {
  width: 25%;  /* 범례의 너비 조정 */
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
  width: 80%; /* 차트의 너비 조정 */
  height: 200px; /* 차트 높이 고정 */
  padding-right: 250px;
  padding-bottom: 20px;
}

canvas {
  width: 100% !important;  /* 캔버스 크기 고정 */
  height: 100% !important;  /* 캔버스 크기 고정 */
  z-index: 1;
}

.chart-text {
  position: absolute;
  top: 70%;
  left: -15%;
  transform: translate(-50%, -50%);
  font-size: 20px;
  font-weight: bold;
  text-align: center;
  color: black;
  line-height: 1.5; /* 텍스트 줄 간격 조정 */
  z-index: 0;
}
</style>
