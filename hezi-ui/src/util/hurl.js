import config from '../config.js';
const apiHttp = {
  // GET请求方法
  get: async function (endpoint, params = {}) {
    let url = config.API_URL + endpoint;
    
    // 将参数拼接到URL中
    const queryParams = new URLSearchParams(params);
    if (queryParams.toString()) {
      url += '?' + queryParams.toString();
    }

    try {
      const response = await fetch(url, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        }
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data =await response.json();
      if(data.code === 403){
          console.warn('接收到403状态码，跳转到登录页面');
          window.location.href = '/login';
          return;
      }
      return data;
    } catch (error) {
      console.error('GET请求失败:', error);
        return {code:501,message:'网络错误',data:null}
    }
  },

  // POST请求方法
  post: async function (endpoint, data = {}) {
    const url = config.API_URL + endpoint;

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

        const responseData =await response.json();
        if(responseData.code === 403){
            console.warn('接收到403状态码，跳转到登录页面');
            window.location.href = '/login';
            return;
        }
        return responseData;
    } catch (error) {
      console.error('POST请求失败:', error);
      return {code:501,message:'网络错误',data:null}
    }
  }
};

// 导出apiGet对象
export default apiHttp;