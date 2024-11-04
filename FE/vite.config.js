import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    host: '0.0.0.0', // 외부 네트워크에서 접근 가능하게 설정
    port: 5173,      // 원하는 포트 (Docker의 포트 매핑과 일치해야 함)
    
    hmr: {
      protocol: 'wss',          // 보안을 위해 WebSocket Secure 사용
      host: 'k11e202.p.ssafy.io', // 배포된 서버의 호스트 이름
      port: 9001                 // WebSocket에 연결할 포트
    }
    // hmr: {
    //   protocol: 'wss', // HMR을 WebSocket Secure로 설정
    //   host: 'k11e202.p.ssafy.io', // HMR의 호스트를 프론트엔드 서버와 일치하도록 설정
    //   port: 443 // HTTPS 포트
    // },
    // https: true, // HTTPS 사용
  }
})