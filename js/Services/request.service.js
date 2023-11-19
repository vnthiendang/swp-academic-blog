import * as request from '../utils/request.js'

const token = localStorage.getItem("token");

//TEACHER GET POST REQUESTS
export const getPostRequest = async () => {
    try {
      const response = await request.get('post/postRequest', {
        headers: {
          Authorization: `Bearer ${token}` 
        }
      });
  
      return response;
    } catch (error) {
      console.error('Error fetching posts:', error);
    }
};


//   API REQUEST
export const getAllRequests = async () => {
  try {
    const response = await request.get('request/GetAll', {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });

    return response;
  } catch (error) {
    console.error('Error fetching requests:', error);
  }
};

//GET REQUEST DETAIL
export const getRequestById = async (id) => {
  try {
    const response = await request.get(`request/${id}`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });

    return response;
  } catch (error) {
    console.error('Error fetching request by id:', error);
  }
};

export const createRequest = async (model) => {
  try {
    const response = await request.post(`request/post`, model,{
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error creating request:', error);
  } 
};

export const editRequest = async (requestId, model) => {
  try {
    const response = await request.post(`request/${requestId}`, model,{
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error updating request:', error);
  } 
};

export const reviewRequest = async (id, model) => {
  try {
    const response = await request.put(`request/review/${id}`, model, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error review request:', error);
  } 
};