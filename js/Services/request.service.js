import * as request from "../utils/request.js";

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

// export const approvePost = async (id, model) => {
//   try {
//     const response = await request.post(`post/postRequest/approve/${id}`, model, {
//       headers: {
//         Authorization: `Bearer ${localStorage.getItem("token")}`
//       }
//     });
//     return response;
//   } catch (error) {
//     console.error('Error approve post:', error);
//   }
// };

// export const approvePost = async (model) => {
//   try {
//     const response = await request.post(
//       `postapproval/post`, model, {
//         headers: {
//           Authorization: `Bearer ${localStorage.getItem("token")}`,
//         },
//       }
//     );
//     return response;
//   } catch (error) {
//     console.log(error);
//   }
// };

// export const rejectPost = async (model) => {
//   try {
//     const response = await request.post(`postapproval/post`, model, {
//       headers: {
//         Authorization: `Bearer ${localStorage.getItem("token")}`
//       }
//     });
//     return response;
//   } catch (error) {
//     console.log(error);
//   }

// }

export const approvePost = async (id, postData) => {
  try {
    const apiUrl = `http://localhost:8080/blog/post/postRequest/approve/${id}`;

    const response = await fetch(apiUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(postData),
    });

    const data = await response.json();
    console.log("Success:", data);
    // Handle the successful response here
    return data; // You can return the data if needed
  } catch (error) {
    console.error("Error:", error);
    // Handle errors here
  }
};

export const rejectPost = async (id, postData) => {
  try {
    const apiUrl = `http://localhost:8080/blog/post/postRequest/reject/${id}`;

    const response = await fetch(apiUrl, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(postData),
    });

    const data = await response.json();
    console.log("Success:", data);
    // Handle the successful response here
    return data; // You can return the data if needed
  } catch (error) {
    console.error("Error:", error);
    // Handle errors here
  }
};

//   API REQUEST
export const getAllRequests = async () => {
  try {
    const response = await request.get("request/GetAll", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response;
  } catch (error) {
    console.error("Error fetching requests:", error);
  }
};

//GET REQUEST DETAIL
export const getRequestById = async (id) => {
  try {
    const response = await request.get(`request/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response;
  } catch (error) {
    console.error("Error fetching request by id:", error);
  }
};

export const createRequest = async (model) => {
  try {
    const response = await request.post(`request/post`, model, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error creating request:", error);
  }
};

export const editRequest = async (requestId, model) => {
  try {
    const response = await request.post(`request/${requestId}`, model, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error updating request:", error);
  }
};

export const reviewRequest = async (id, model) => {
  try {
    const response = await request.put(`request/review/${id}`, model, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error review request:", error);
  }
};
