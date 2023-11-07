import * as request from '../utils/request.js'

const token = localStorage.getItem('token');

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
    const response = await request.get(`user/GetAll`, {
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
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error get violation accounts:', error);
  }
};

export const approvedPostList = async () => {
  try {
    const response = await request.get(`postapproval/getall`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error get approved posts:', error);
  }
};

export const violationRuleList = async () => {
  try {
    const response = await request.get(`violationRule/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}`
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
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error ban user:', error);
  } 
};