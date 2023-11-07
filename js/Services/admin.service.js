import * as request from '../utils/request.js'

// export const updateUser = async (model) => {
//   try {
//     const response = await request.post('user', model);

//     return user;
//   } catch (error) {
//     // Handle error here
//     throw error;
//   }
// };

export const userList = async () => {
  try {
    const response = await request.get(`user`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    return response;
  } catch (error) {
    throw error;
  }
};

export const violationList = async () => {
  try {
    const response = await request.get(`accountViolation/getall`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error get violation accounts:', error);
  }
};

export const violationRuleList = async () => {
  try {
    const response = await request.get(`violationRule/GetAll`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    return response;
  } catch (error) {
    throw error;
  }
};

export const addAccountViolation = async (model) => {
  try {
    const response = await request.post(`accountViolation/post`, model, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error ban user:', error);
  } 
};