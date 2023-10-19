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
    const response = await axios.get(`${endpoint}/create`, {
      headers: {
        Authorization: `Bearer ${token}`
      },
      body: {
        post: post
      }
    });
    return response.data;
  } catch (error) {
    console.error('Error searching posts:', error);
    throw new Error("An error occurred. Please try again later.");
  }
};

export {
    getAllApprovedPosts, searchedPosts
};