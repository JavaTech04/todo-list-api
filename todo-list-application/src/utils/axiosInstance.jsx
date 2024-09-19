import axios from 'axios';
import { tokenService } from './tokenService';

const axiosInstance = axios.create({
  baseURL: 'http://localhost:2004/api/v1',
});

// Thêm interceptor để kiểm tra token hết hạn
axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 403) {
      const message = error.response.data.message;

      // Kiểm tra lỗi token hết hạn
      if (message && message.toLowerCase().includes('jwt expired')) {
        // Token đã hết hạn, clear token và điều hướng về trang login
        tokenService.clearUser();
        window.location.href = "/login"; // Điều hướng về trang đăng nhập
      }
    }
    return Promise.reject(error);
  }
);

export default axiosInstance;
