import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

const getAllCategory = async () => {
  try {
    const response = await request.get(`category/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}`
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


// // //              CATEGORY MANAGEMENT
export const getAllTeacherCategory = async () => {
  try {
    const response = await request.get('categoryManagement/GetAll', {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });

    return response;
  } catch (error) {
    console.error('Error fetching category managements:', error);
  }
};

export const getTeacherCategoryById = async (id) => {
  try {
    const response = await request.get(`categoryManagement/${id}`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });

    return response;
  } catch (error) {
    console.error('Error fetching category management by id:', error);
  }
};

export const createTeacherCategory = async (model) => {
  try {
    const response = await request.post(`categoryManagement/post`, model,{
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error creating category management:', error);
  } 
};