const endpoint = "http://localhost:8080/blog/tag";

const token = localStorage.getItem("token");

const getAllTag = async () => {
  try {
    const response = await axios.get(`${endpoint}/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}` // Include the token in the request headers
      }
    });

    console.log(response.data);
    return response.data;
  } catch (error) {
    
    throw new Error("An error occurred. Please try again later.");
  }
};

export {
    getAllTag
};