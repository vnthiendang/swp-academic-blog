import * as request from "../utils/request.js";

const token = localStorage.getItem("token");

// export const updateUser = async (model) => {
//   try {
//     const response = await request.post('user', model);

//     return user;
//   } catch (error) {
//     // Handle error here
//     throw error;
//   }
// };

export const userList = async () => {
  try {
    const response = await request.get(`user/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error get user lists:", error);
  }
};

export const violationList = async () => {
  try {
    const response = await request.get(`accountViolation/getall`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error get violation accounts:", error);
  }
};

// export const approvedPostList = async () => {
//   try {
//     const response = await request.get(`postapproval/getall`, {
//       headers: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//     return response;
//   } catch (error) {
//     console.error('Error get approved posts:', error);
//   }
// };

export const approvedPostList = async (postApprovalStatuses) => {
  if (
    postApprovalStatuses !== undefined &&
    typeof postApprovalStatuses !== "string"
  ) {
    throw new Error(
      "Invalid input. Please provide valid postApprovalStatuses."
    );
  }

  const approvalStatusesArray =
    postApprovalStatuses !== undefined ? postApprovalStatuses.split(",") : [];

  try {
    const response = await fetch(
      `http://localhost:8080/blog/postapproval/getall?postApprovalStatuses=${approvalStatusesArray.join(
        ","
      )}`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error getting approved posts:", error);
    throw error; // Re-throw the error to handle it at the calling site if needed
  }
};

export const violationRuleList = async () => {
  try {
    const response = await request.get(`violationRule/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    throw error;
  }
};

export const addAccountViolation = async (model) => {
  try {
    const response = await request.post(`accountViolation/post`, model, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error ban user:", error);
  }
};

export const updatePostStatus = async (postId, post) => {
  try {
    const response = await request.put(`postapproval/${postId}`, post, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error updating post status:", error);
  }
};

export const deleteViolation = async (id) => {
  try {
    const response = await request.del(`accountViolation/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error remove violation account:", error);
  }
};
