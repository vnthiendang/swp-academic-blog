import * as request from "../utils/request.js";

const token = localStorage.getItem("token");

export const getAllAwardType = async () => {
  try {
    const response = await request.get(`award_type/getall`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    throw new Error(error);
  }
};

export const getAllAward = async () => {
  try {
    const response = await request.get("award/getall", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response;
  } catch (error) {
    console.log(error);
  }
};

export const getAllAwardForAdminPage = async () => {
  try {
    const response = await fetch(
      `http://localhost:8080/blog/award/getall`,
      {
        method: "GET",
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
          // Add other headers as needed
        },
        // You can add credentials: 'include' if needed
      }
    );

    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error fetching awards:", error);
    throw error; // Re-throw the error to handle it at the calling site if needed
  }
};

// export const createAward = async (model) => {
//   try {
//     const response = await request.postAward(`award/post`, model, {
//       headers: {
//         Authorization: `Bearer ${token}`
//       }
//     });
//     return response.data;

//   } catch (error) {
//     console.error('Error giving award:', error);
//   }
// };

export const createAward = async (model) => {
  try {
    const response = await fetch(
      "http://localhost:8080/blog/award/post",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(model),
      }
    );

    const contentType = response.headers.get("content-type");

    if (response.ok) {
      if (contentType && contentType.includes("application/json")) {
        const responseData = await response.json();

        if (
          responseData.message &&
          responseData.message.includes("successfully")
        ) {
          // Handle successful response
          console.log("Award created successfully:", responseData.message);
          alert("Award created successfully!");
          // You can perform additional actions here if needed
        } else {
          // Handle unsuccessful response
          handleUnsuccessfulResponse(responseData);
        }
      } else {
        // Handle plain text response
        console.log("Award created successfully:", await response.text());
        alert("Award created successfully!");
        // You can perform additional actions here if needed
      }
    } else if (response.status === 400) {
      // Handle specific status code 400 (Bad Request)
      if (contentType && contentType.includes("application/json")) {
        const errorMessage = await response.json().catch(() => null);
        handleBadRequest(errorMessage);
      } else {
        // Handle plain text error response
        const plainTextError = (await response.text()) || "Unknown error";
        console.error("Bad request: ", plainTextError);
        alert("Bad request: " + plainTextError);
        // You can display an alert or perform other error handling actions
      }
    } else {
      // Handle other errors
      console.error("Failed to create award. Please try again.");
      alert("Failed to create award. Please try again.");
      // You can display an alert or perform other error handling actions
    }
  } catch (error) {
    // Handle network or unexpected errors
    console.error("Error creating award:", error.message || "Unknown error");
    alert("Error creating award: " + (error.message || "Unknown error"));
    // You can display an alert or perform other error handling actions
  }
};

const handleUnsuccessfulResponse = (responseData) => {
  console.error(
    "Failed to create award:",
    responseData.message || "Unknown error"
  );
  alert("Failed to create award: " + (responseData.message || "Unknown error"));
  // You can display an alert or perform other error handling actions
};

const handleBadRequest = (errorMessage) => {
  console.error("Bad request: ", errorMessage || "Unknown error");

  alert("Bad request: " + (errorMessage || "Unknown error"));

  if (
    errorMessage &&
    errorMessage.includes("You have already given an award this week.")
  ) {
    // Handle the specific case where the user has already given an award this week
    console.error(
      "You have already given an award this week. Cannot give another award."
    );
    alert(
      "You have already given an award this week. Cannot give another award."
    );
    // You can display an alert or perform other error handling actions
  }

  // You can add more specific checks for other types of errors if needed
};
