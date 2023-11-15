import * as request from '../utils/request.js'

const token = localStorage.getItem("token");


export const getVoteType = async () => {
    try {
      const response = await request.get(`votetype/getall`, {
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
  
      return response;
    } catch (error) {
      
      throw new Error("Error fetching vote type!");
    }
};

export const getVoteByPostId = async (id) => {
  try {
    const response = await request.get(`vote/getall/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    return response;
  } catch (error) {
    
    throw new Error("Error fetching vote list!");
  }
};


export const votePost = async (model) => {
    try {
      const response = await request.post(`vote/post`, model,{
        headers: {
          Authorization: `Bearer ${token}`
        }
      });
      return response;
    } catch (error) {
      console.error('Error creating vote:', error);
    } 
};

export const getMostVotePosts = async () => {
  try {
    const response = await request.get(`post/GetAllApproved/filter`, {
      headers: {
        Authorization: `Bearer ${token}`
      },
      params: {
        sortDirection
      }
    });

    return response;
  } catch (error) {
    
    throw new Error("Error fetching most vote posts!");
  }
};

export const updateVote = async (postId, model) => {
  try {
    const response = await request.put(`vote/${postId}`, model, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
    
  } catch (error) {
    console.error('Error edit your vote:', error);
  }
};