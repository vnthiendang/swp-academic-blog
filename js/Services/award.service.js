import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

export const getAllAwardType = async () => {
  try {
    const response = await request.get(`award_type/getall`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });
    return response;
  } catch (error) {
    
    throw new Error(error);
  }
};

export const getAllAward = async () => {
  try {
    const response = await request.get(`award/getall`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });
    return response;
  } catch (error) {
    
    console.log(error);
  }
};

export const createAward = async (model) => {
  try {
    const response = await request.post(`award/post`, model, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
    
  } catch (error) {
    console.error('Error giving award:', error);
  }
};