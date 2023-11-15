import * as request from '../utils/request.js'

const token = localStorage.getItem("token");


//POST
export const getAllApprovedPosts = async () => {
  try {
    const response = await request.get(`post/GetAllApproved`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });
    return response;
  } catch (error) {
    console.error('Error fetching posts:', error);
    if (error.response && error.response.status === 401) {
      // Token is invalid or expired
      alert('Your session logged out! You must login again!');
    }
    throw new Error("An error occurred. Please try again later.");
  }
};

export const searchedPosts = async (keyword) => {
  try {
    const response = await request.get(`post/search`, {
      headers: {
        Authorization: `Bearer ${token}`
      },
      params: {
        keyword
      }
    });
    return response;
  } catch (error) {
    console.error('Error searching posts:', error);
    throw new Error("An error occurred. Please try again later.");
  }
};

export const createPost = async (post, config) => {
  try {
    const response = await request.post(`post/create`, post, config);
    return response;
  } catch (error) {
    console.error('Error creating post:', error);
  }
};

export const updatePost = async (postId, post) => {
  try {
    const response = await request.put(`post/edit/${postId}`, post, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
    
  } catch (error) {
    console.error('Error updating post:', error);
  }
};

export const getPostById = async (id) => {
  try {
    const response = await request.get(`post/GetAllApproved/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error retrieving post:', error);
  }
};

export const getPostByUserId = async (userId) => {
  try {
    const response = await request.get(`post/GetAllApproved?userId=${userId}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error retrieving post:', error);
  }
};
//POST

//COMMENT
export const getCommentById = async (postId) => {
  try {
    const response = await request.get(`comment/getall/${postId}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error retrieving comment:', error);
    throw new Error("An error occurred while retrieving the comment. Please try again later.");
  }
};

export const createComment = async (model) => {
  try {
    const response = await request.post(`comment/post`, model,{
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error creating comment:', error);
  } 
};

export const editComment = async (postId, model) => {
  try {
    const response = await request.put(`comment/${postId}`, model, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
    
  } catch (error) {
    console.error('Error edit comment:', error);
  }
};
//COMMENT

// FILTER
export const getPostByCategory = async (categoryName) => {
  try {
    const response = await request.get('post/GetAllApproved', {
      headers: {
        Authorization: `Bearer ${token}`
      },
      params: {
        categoryName
      }
    });
    return response;
  } catch (error) {
    console.error('Error retrieving post by category:', error);
    throw new Error("An error occurred while retrieving the post by category. Please try again later.");
  }
};

export const getPostByCate = async (categoryId) => {
  try {
    const response = await request.get(`post/GetAllApproved/filter?categoryId=${categoryId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
      // params: {
      //   categoryId
      // }
    });
    return response;
  } catch (error) {
    console.error('Error retrieving post by vote count:', error);
  }
};

export const getPostByTags = async (tagIds) => {
  try {
    const response = await request.get(`post/GetAllApproved?tagIds=${tagIds}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
      // params: {
      //   tagIds
      // }
    });
    return response;
  } catch (error) {
    console.error('Error retrieving post:', error);
  }
};

export const getPostSortBy = async () => {
  try {
    const response = await request.get(`post/GetAllApproved/filter?sortDirection`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error retrieving post:', error);
  }
};

export const getMostVotePost = async (categoryId) => {
  try {
    const response = await request.get(`post/mostVotedPost`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      },
      params: {
        categoryId
      }
    });
    return response;
  } catch (error) {
    console.error('Error retrieving post by vote count:', error);
  }
};
//FILTER