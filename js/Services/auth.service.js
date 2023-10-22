import * as request from '../utils/request.js'

export const login = async (model) => {
  try {
    const response = await request.post('auth/authenticate', model);
    const token = response.token;
    localStorage.setItem('token', token);

    const user = await userInfo();
    return user;
  } catch (error) {
    // Handle error here
    throw error;
  }
};
   
export const register = async (model) => {
    const response = await request.post('auth/register', model);
    return response;
};

export const userInfo = async () => {
  try {
    const response = await request.get(`user/profile`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    return response;
  } catch (error) {
    throw error;
  }
};
  