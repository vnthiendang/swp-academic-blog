import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

const getAllCategory = async () => {
  try {
    const response = await request.get(`category/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}` // Include the token in the request headers
      }
    });

    console.log(response);
    return response;
  } catch (error) {
    
    throw new Error("An error occurred. Please try again later.");
  }
};

export {
    getAllCategory
};