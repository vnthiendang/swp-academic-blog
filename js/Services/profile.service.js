import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

export const userInfo = async () => {
  try {
    const response = await request.get(`user/profile`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });
    return response;
  } catch (error) {
    alert(error);
  }
};

export const updateProfile = async (model) => {
  try {
    const response = await request.put(`user`, model, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });
    return response;
  } catch (error) {
    alert(error);
  }
};