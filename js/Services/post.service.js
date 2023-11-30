import * as request from "../utils/request.js";

const token = localStorage.getItem("token");

//POST
export const getAllApprovedPosts = async () => {
  try {
    const response = await request.get(`post/GetAllApproved/filter`, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });
    return response;
  } catch (error) {
    console.error("Error fetching posts:", error);
  }
};

export const getAwardedPosts = async (minimumLikeCount, postApprovalStatuses) => {
  if (
    (minimumLikeCount !== undefined && typeof minimumLikeCount !== "number") ||
    (postApprovalStatuses !== undefined && typeof postApprovalStatuses !== "string")
  ) {
    throw new Error("Invalid input. Please provide valid parameters.");
  }

  const approvalStatusesArray =
    postApprovalStatuses !== undefined ? postApprovalStatuses.split(",") : [];

  try {
    const requestOptions = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    };

    const url = `GetAll/filter?postApprovalStatuses=${approvalStatusesArray.join(",")}` +
      (minimumLikeCount !== undefined ? `&minimumLikeCount=${minimumLikeCount}` : "");

    const response = await request.get(url, requestOptions);
    const data = await response.json();

    console.log("Approved Posts:", data);
    return data; // You can return the data if needed
  } catch (error) {
    console.error("Error:", error);
    // Handle errors here
  }
};

export const searchedPosts = async (keyword) => {
  try {
    const response = await request.get(`post/search`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        keyword,
      },
    });
    return response;
  } catch (error) {
    console.error("Error searching posts:", error);
  }
};

export const createPost = async (post, config) => {
  try {
    const response = await request.post(`post/create`, post, config);
    return response;
  } catch (error) {
    console.error("Error creating post:", error);
  }
};
export const updatePost = async (postId, post) => {
  try {
    const response = await request.put(`post/edit/${postId}`, post, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error updating post:", error);
  }
};
export const deletePost = async (postId) => {
  try {
    const response = await request.put(`post/delete/${postId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error deleting post:", error);
  }
};

export const getPostById = async (id) => {
  try {
    const response = await request.get(`post/GetAllApproved/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error retrieving post:", error);
  }
};

export const getPostByUserId = async (postApprovalStatuses,userId) => {
  try {
    const response = await request.get(`post/GetAll/filter`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        postApprovalStatuses,userId
      },
    });
    return response;
  } catch (error) {
    console.error("Error retrieving post by userId:", error);
  }
};
//POST

//COMMENT
export const getCommentById = async (postId) => {
  try {
    const response = await request.get(`comment/getall/${postId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error retrieving comment:", error);
    throw new Error(
      "An error occurred while retrieving the comment. Please try again later."
    );
  }
};

export const createComment = async (model) => {
  try {
    const response = await request.post(`comment/post`, model, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error creating comment:", error);
  }
};

export const editComment = async (commentId, model) => {
  try {
    const response = await request.put(`comment/${commentId}`, model, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error edit comment:", error);
  }
};

export const deleteComment = async (commentId) => {
  try {
    const response = await request.put(`comment/delete/${commentId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error delete comment:", error);
  }
};
//COMMENT

// FILTER
export const getPostByCategory = async (categoryName) => {
  try {
    const response = await request.get("post/GetAllApproved", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        categoryName,
      },
    });
    return response;
  } catch (error) {
    console.error("Error retrieving post by category:", error);
    throw new Error(
      "An error occurred while retrieving the post by category. Please try again later."
    );
  }
};

export const getPostByTags = async (tagName) => {
  try {
    const response = await request.get(
      `post/GetAllApproved?tagName=${tagName}`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
        // params: {
        //   tagIds
        // }
      }
    );
    return response;
  } catch (error) {
    console.error("Error retrieving post by tag:", error);
  }
};

export const filterPost = async (
  categoryName,
  tagNames,
  startDate,
  endDate,
  sortBy,
  sortDirection
) => {
  try {
    const response = await request.get(`post/GetAllApproved/filter`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      params: {
        categoryName,
        tagNames: tagNames.join(","),
        startDate,
        endDate,
        sortBy: sortBy.join(","),
        sortDirection,
      },
    });
    return response;
  } catch (error) {
    console.error("Error filtering posts:", error);
  }
};
//FILTER
