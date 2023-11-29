import * as request from "../utils/request.js";

const token = localStorage.getItem("token");

export const getAllCategory = async () => {
  try {
    const response = await request.get(`category/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    throw new Error("An error occurred. Please try again later.");
  }
};

// // //              CATEGORY MANAGEMENT
export const getAllTeacherCategory = async () => {
  try {
    const response = await request.get("categoryManagement/GetAll", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response;
  } catch (error) {
    console.error("Error fetching category managements:", error);
  }
};

export const getTeacherCategoryById = async (id) => {
  try {
    const response = await request.get(`categoryManagement/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response;
  } catch (error) {
    console.error("Error fetching category management by id:", error);
  }
};

export const getTeacherCategoryByUserId = async (userId) => {
  try {
    const response = await request.get(`categoryManagement/GetAll`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
      params: {
        userId,
      },
    });

    return response;
  } catch (error) {
    console.error("Error fetching category management by id:", error);
  }
};

export const createTeacherCategory = async (model) => {
  try {
    const response = await request.post(`categoryManagement/post`, model, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.error("Error creating category management:", error);
  }
};

export const createTeacherCategory2 = async (model) => {
  try {
    const response = await fetch(
      `http://localhost:8080/blog/categoryManagement/post`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(model),
      }
    );

    return response;
  } catch (error) {
    console.error("Error creating category management:", error);
  }
};
