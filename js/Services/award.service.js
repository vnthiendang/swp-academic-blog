import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

export const getAllAwardType = async () => {
  try {
    const response = await request.get(`award_type/getall`, {
      headers: {
        Authorization: `Bearer ${token}` // Include the token in the request headers
      }
    });
    return response;
  } catch (error) {
    
    throw new Error("An error occurred. Please try again later.");
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