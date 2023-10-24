import request from "../utils/request";

const token = localStorage.getItem("token");

const userInfo = async () => {
  try {
    const response = await request.get(`user/profile`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });
    return response.data;
  } catch (error) {
    alert(response.status);
  }
};
  
  export {
    userInfo
  }