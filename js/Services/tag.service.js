import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

export const getAllTag = async () => {
  try {
    const response = await request.get(`tag/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}` // Include the token in the request headers
      }
    });

    return response;
  } catch (error) {
    
    throw new Error("Error fetching tag!");
  }
};