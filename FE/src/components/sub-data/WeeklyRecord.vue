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
import { onMounted } from 'vue';
import Chart from 'chart.js/auto';  // Chart.js 자동 임포트

onMounted(() => {
  const canvas = document.getElementById('myChart2');

  if (canvas) {
    const ctx = canvas.getContext('2d');

    // 차트 생성
    new Chart(ctx, {
      type: 'line',  // 라인 차트로 설정
      data: {
        labels: ['10월 17일', '10월 18일', '10월 19일', '10월 20일', '10월 21일', '10월 22일'],
        datasets: [
          {
            label: '생산량',
            data: [3, 6, 4, 8, 7, 9], // 생산량 데이터
            fill: true,  // 배경 채우기
            borderColor: 'rgba(85, 42, 254, 1)', // 라인 색상
            backgroundColor: 'rgba(237, 235, 253, 0.7)', // 배경 색상
            tension: 0.4, // 곡선 스무딩
            pointBackgroundColor: 'white', // 포인트 색상
            pointBorderWidth: 2, // 포인트 테두리 너비
            borderWidth: 3  // 라인 두께
          }
        ]
      },
      options: {
        maintainAspectRatio: false, // 비율 유지하지 않음
        scales: {
          y: {
            beginAtZero: true // Y축 0부터 시작
          }
        },
        plugins: {
          legend: {
            display: true // 범례 표시
          },
          tooltip: { // 툴팁 옵션
            enabled: true, // 툴팁을 활성화
            mode: 'index', // x축 위에 있는 모든 요소를 표시
            intersect: false, // 교차하지 않는 데이터도 표시
            callbacks: {
              label: function(tooltipItem) {
                // 툴팁에 생산량, 온도, 습도 정보를 추가
                if (tooltipItem.dataset.label === '생산량') {
                  return `생산량: ${tooltipItem.raw}`;
                } 
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
.header-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
}

.icon-text {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.icon-box {
  width: 40px;
  height: 40px;
  background-color: rgba(85, 42, 254, 1); /* 보라색 사각형 */
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
  height: 280px; /* 높이를 명시적으로 설정 */
  position: relative;
}

canvas {
  width: 100% !important; /* 캔버스 크기 조정 */
  height: 80% !important; /* 캔버스 크기 조정 */
}
</style>
