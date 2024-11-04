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
      protocol: 'wss',
      host: 'k11e202.p.ssafy.io', // 배포된 서버의 호스트 이름
      clientPort: 443             // HTTPS의 기본 포트로 설정
    }

  }
})