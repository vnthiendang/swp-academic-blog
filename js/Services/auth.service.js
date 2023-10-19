import {apiUrl} from "./_endpoint.service.js"

const login = async (model) => {
  try {
    const response = await axios.post(`${apiUrl}/authenticate`, model, {
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (response.status === 200) {
      const { token } = response.data;
      localStorage.setItem("token", token);
    } else if (response.status === 401) {
      alert(error.response);
    } else {
      throw new Error("An error occurred. Please try again later.");
    }
  } catch (error) {
    alert(error.response);
  }
  };

  const register = async (model) => {
      try {
        const response = await axios.post(`${apiUrl}/register`, model,{
          headers: {
            "Content-Type": "application/json",
          },
        });

        if (response.status === 200) {
          const { token } = response.data;
        }
      } catch (error) {
        console.error(error);
        if (error.response) {
          console.error("Response status:", error.response.status);
        }
        throw new Error("An error occurred. Please try again later.");
      }
  };

const token = localStorage.getItem("token");

const userInfo = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/blog/user/profile`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });
    return response.data;
  } catch (error) {
    alert(response.status);
  }
};
  
  export {
    login, register, userInfo
  }