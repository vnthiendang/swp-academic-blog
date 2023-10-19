const endpoint = "http://localhost:8080/blog/post";

const token = localStorage.getItem("token");

const getAllApprovedPosts = async () => {
  try {
    const response = await axios.get(`${endpoint}/GetAllApproved`, {
      headers: {
        Authorization: `Bearer ${token}` // Include the token in the request headers
      }
    });

    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching posts:', error);
    if (error.response && error.response.status === 401) {
      // Token is invalid or expired
      alert('Your session logged out! You must login again!');
    }
    throw new Error("An error occurred. Please try again later.");
  }
};

const searchedPosts = async (searchTerm) => {
  try {
    const response = await axios.get(`${endpoint}/search`, {
      headers: {
        Authorization: `Bearer ${token}`
      },
      params: {
        keyword: searchTerm
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error searching posts:', error);
    throw new Error("An error occurred. Please try again later.");
  }
};

const createPost = async (post) => {
  try {
    const response = await axios.post(`${endpoint}/create`, post, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log(response.data);
    alert('Your Post created successfully!');
  } catch (error) {
    console.error('Error creating post:', error);
  }
};

const getPostById = async (id) => {
  try {
    const response = await axios.get(`${endpoint}/GetAllApproved/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error retrieving post:', error);
    throw new Error("An error occurred while retrieving the post. Please try again later.");
  }
};

//TEACHER GET POST REQUESTS
const getPostRequest = async () => {
  try {
    const response = await axios.get(`${endpoint}/postRequest`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });

    return response.data;
  } catch (error) {
    console.error('Error fetching posts:', error);
    if (error.response && error.response.status === 401) {
      // Token is invalid or expired
      alert('Please login again!');
    }
    throw new Error("An error occurred. Please try again later.");
  }
};

const getPostRequestById = async (id) => {
  try {
    const response = await axios.get(`${endpoint}/postRequest/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error retrieving post:', error);
    throw new Error("An error occurred while retrieving the post. Please try again later.");
  }
};

export {
    getAllApprovedPosts, searchedPosts, getPostById, createPost, getPostRequest, getPostRequestById
};