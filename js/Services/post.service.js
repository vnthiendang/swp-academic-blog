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
    throw new Error("An error occurred. Please try again later.");
  }
};

export {
    getAllApprovedPosts
};