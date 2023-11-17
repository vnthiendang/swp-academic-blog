import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

export const getAllTag = async () => {
  try {
    const response = await request.get(`tag/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    return response;
  } catch (error) {
    
    throw new Error("Error fetching tag!");
  }
};

export const searchTagByName = async (keyword) => {
  try {
    const response = await request.get(`tag/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}`
      },
      params: {
        keyword
      }
    });

    return response;
  } catch (error) {
    
    throw new Error("Error searching tag by name!");
  }
};