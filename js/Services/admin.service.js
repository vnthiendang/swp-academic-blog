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