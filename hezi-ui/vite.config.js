import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    proxy: {
      // 代理所有以 /api 开头的请求
      '/api': {
        target: 'http://127.0.0.1:10086', // 后端服务器地址，根据实际情况修改
        changeOrigin: true,
        secure: false, // 如果是 https 接口，需要设置为 true
        // 可选：重写路径，去掉 /api 前缀
        rewrite: (path) => path.replace(/^\/api/, ''),
      },
      // 可以添加更多代理规则
      // '/api2': {
      //   target: 'http://localhost:3001',
      //   changeOrigin: true,
      //   rewrite: (path) => path.replace(/^\/api2/, ''),
      // },
    },
  },
})
