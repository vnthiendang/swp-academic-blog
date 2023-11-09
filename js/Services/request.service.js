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