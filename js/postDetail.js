import {
  createComment,
  deleteComment,
  editComment,
  getCommentById,
} from "./Services/post.service.js";
import * as request from "./utils/request.js";
import { userInfo } from "./Services/auth.service.js";
import { votePost } from "./Services/vote.service.js";
import { createAward, getAllAwardType } from "./Services/award.service.js";

const token = localStorage.getItem("token");

const options = {
  month: "short",
  day: "2-digit",
  hour: "2-digit",
};

// Get the belongedToPostID from the URL query parameters
const urlParams = new URLSearchParams(window.location.search);
const postId = urlParams.get("belongedToPostID");

const showHeaderForTeacher = async () => {
  try {
    const usersInfo = await userInfo();
    const userRole = usersInfo.role_id;

    if (userRole === "Teacher") {
      // Display the form
      document.getElementById("teacherPage").style.display = "block";
      document.getElementById("teacherPage2").style.display = "block";
    } else {
      // Hide the form
      document.getElementById("teacherPage").style.display = "none";
      document.getElementById("teacherPage2").style.display = "none";
    }
  } catch (error) {}
};

showHeaderForTeacher();

function displayPost() {
  request
    .get(`post/GetAllApproved/${postId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
    .then(async (response) => {
      const post = response;

      const usersInfo = await userInfo();
      const user = usersInfo.display_name;
  
      if (user === post.createdByUser) {
        // Display the form
        document.getElementById("reportButton").style.display = "none";
      } else {
        // Hide the form
        document.getElementById("reportButton").style.display = "block";
      }

      // Store the post data in session storage
      sessionStorage.setItem("postData", JSON.stringify(post));

      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString("en-US", options);

      // Update HTML elements with post data
      document.getElementById("postTitle").textContent = post.title;
      document.getElementById("postAuthor").textContent = post.createdByUser;
      document.getElementById("postDate").textContent = formattedTime;
      if (post.tagList.length > 0) {
        const iconTagElement = document.createElement("div");
        iconTagElement.className = "icon-tag";
        const iconTagImg = document.createElement("img");
        iconTagImg.src = "img/tag.png";
        iconTagImg.alt = "icon-tag";
        iconTagElement.appendChild(iconTagImg);

        const tagLink1 = document.createElement("a");
        tagLink1.href = "#";

        post.tagList.forEach((tag) => {
          const tagItem = document.getElementById("postTag");
          tagItem.textContent = tag;

          const tagLink = document.createElement('a');
          tagLink.href = `/pageByTag.html?tagName=${tag}`;
          tagLink.textContent = tag;

          tagItem.innerHTML = '';
          tagItem.appendChild(tagLink);
        });
      }

      const postCategoryElement = document.getElementById("postCategory");
      postCategoryElement.textContent = post.belongedToCategory;

      const categoryLink = document.createElement('a');
      categoryLink.href = `postByCategory.html?categoryName=${post.belongedToCategory}`;
      categoryLink.textContent = post.belongedToCategory;

      postCategoryElement.innerHTML = '';
      postCategoryElement.appendChild(categoryLink);

      const postContentElement = document.getElementById("postContent");
      postContentElement.contentEditable = false;
      postContentElement.innerHTML = post.postDetail;

      if (post.mediaList.length > 0) {
        const imageElement = document.getElementById("postMedia");
        imageElement.src = `data:image/jpeg;base64, ${post.mediaList[0]}`;
      }

      // post.mediaList.forEach(media => {
      //   const mediaItem = document.getElementById('postMedia');
      //   mediaItem.src = `data:image/jpeg;base64, ${media}`;
      //   mediaItem.style.width = '240px';
      //   mediaItem.style.height = 'auto';
      //   mediaList.appendChild(mediaItem);
      // });

      const readingTimeElement = document.getElementById("readingTime");
      const minuteText = post.readingTime === 1 ? "minute" : "minutes";
      readingTimeElement.textContent =
        post.readingTime + " " + minuteText + "" || "";
      document.getElementById("postVote").textContent = post.vote1Count ?? "#";
      document.getElementById("postVote2").textContent = post.vote2Count ?? "#";
    })
    .catch((error) => {
      console.error("Error retrieving post:", error);
      // Handle error display or logging
    });
}
displayPost();

// Get comments by PostID
const displayComments = async (comments) => {
  const commentContainer = document.getElementById("commentContainer");
  comments.forEach(async (comment) => {
    if (comment.status !== "deleted") {
      const us = await userInfo();
      const article = document.createElement("article");
      article.classList.add(
        "p-6",
        "mb-3",
        "text-base",
        "bg-white",
        "border-t",
        "border-gray-200",
        "dark:border-gray-700",
        "dark:bg-gray-900"
      );
      const footer = document.createElement("footer");
      footer.classList.add("flex", "justify-between", "items-center", "mb-2");
      const commentByUser = document.createElement("p");
      commentByUser.classList.add(
        "inline-flex",
        "items-center",
        "mr-3",
        "text-sm",
        "text-gray-900",
        "dark:text-white",
        "font-semibold"
      );
      commentByUser.textContent = comment.createdByUser;
      footer.appendChild(commentByUser);
      article.appendChild(footer);
      const commentText = document.createElement("p");
      commentText.classList.add("text-gray-500", "dark:text-gray-400");
      commentText.textContent = comment.commentText;
      article.appendChild(commentText);
      const buttonContainer = document.createElement("div");
      buttonContainer.classList.add(
        "flex",
        "items-center",
        "mt-4",
        "space-x-4"
      );
      const editBtn = document.createElement("button");
      editBtn.type = "button";
      editBtn.dataset.commentId = comment.id;
      editBtn.classList.add(
        "flex",
        "items-center",
        "text-sm",
        "text-gray-500",
        "hover:underline",
        "dark:text-gray-400",
        "font-medium"
      );
      editBtn.innerHTML = '<i class="fa-regular fa-comment px-2"></i>Edit';
      buttonContainer.appendChild(editBtn);
      const delButton = document.createElement("button");
      delButton.type = "button";
      delButton.dataset.commentId = comment.id;
      delButton.classList.add(
        "flex",
        "items-center",
        "text-sm",
        "text-gray-500",
        "hover:underline",
        "dark:text-gray-400",
        "font-medium"
      );
      delButton.innerHTML = '<i class="fa-regular fa-delete px-2"></i>Delete';
      buttonContainer.appendChild(delButton);
      if (us.display_name == comment.createdByUser) {
        article.appendChild(buttonContainer);
      }
      commentContainer.appendChild(article);
      //EDIT COMMENT
      editBtn.addEventListener("click", async () => {
        const commentId = editBtn.dataset.commentId;
        // Make comment editable
        commentText.contentEditable = true;
        commentText.focus();
        commentText.classList.add("editable");
        // Disable other buttons
        editBtn.disabled = true;
        delButton.disabled = true;
        const originalCommentText = commentText.textContent.trim();
        const submitForm = async () => {
          const updatedCommentText = commentText.textContent.trim();
          if (
            updatedCommentText !== "" &&
            updatedCommentText !== originalCommentText
          ) {
            try {
              const model = {
                commentText: updatedCommentText,
              };
              const response = await editComment(parseInt(commentId), model);
              if (response != null) {
                location.reload();
              } else {
                alert("Failed to edit comment!");
              }
            } catch (error) {
              console.error("Error editing comment:", error);
            }
          } else {
            // If the updated comment text is empty or unchanged, revert the changes
            commentText.textContent = comment.commentText;
          }
          // Disable editing and enable buttons
          commentText.contentEditable = false;
          commentText.classList.remove("editable");
          editBtn.disabled = false;
          delButton.disabled = false;
        };
        commentText.addEventListener("keydown", (event) => {
          if (event.key === "Enter" && !event.shiftKey) {
            event.preventDefault();
            submitForm();
          }
        });
      });
      delButton.addEventListener("click", async () => {
        const commentId = delButton.dataset.commentId;
        const confirmDelete = confirm(
          "Are you sure you want to delete this comment?"
        );
        if (confirmDelete) {
          try {
            const response = await deleteComment(commentId);
            if (response != null) {
              location.reload();
            } else {
              alert("Failed to delete comment!");
            }
          } catch (error) {
            console.error("Error deleting comment:", error);
          }
        }
      });
    }
  });
};

getCommentById(postId).then((comments) => {
  displayComments(comments);
});

const form = document.getElementById("comment");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const commentText = document.getElementById("cmt").value.trim();
  const belongedToPostID = parseInt(postId);

  if (commentText === "") {
    alert("Please enter a valid comment.");
    return;
  }

  const us = await userInfo();
  const createdByUserID = us.userId;

  const data = { commentText, belongedToPostID, createdByUserID };

  try {
    const res = await createComment(data);
    if (res == null) {
      alert("Sorry! Please check your comment! ");
    } else {
      location.reload();
      document.getElementById("cmt").value = "";
    }
  } catch (error) {
    console.error(error);
    alert("An error occurred while posting the comment.");
  }
});

//STUDENT VOTE POST
const showFormForStudent = async () => {
  try {
    const usersInfo = await userInfo();
    const userRole = usersInfo.role_id;

    if (userRole === "Student") {
      // Display the form
      document.getElementById("likeIcon").style.display = "block";
      document.getElementById("dislikeIcon").style.display = "block";
    } else {
      document.getElementById("likeIcon").style.pointerEvents = "none";
      document.getElementById("dislikeIcon").style.pointerEvents = "none";
    }
  } catch (error) {
    console.error("Error retrieving user info:", error);
  }
};

showFormForStudent();

function toggleLikeIcon() {
  var likeIcon = document.getElementById("likeIcon");

  // Toggle between the regular and solid thumbs-up icons
  if (likeIcon.classList.contains("fa-regular")) {
    likeIcon.classList.remove("fa-regular");
    likeIcon.classList.add("fa-solid");
    likeIcon.style.color = "#000000"; // Set the color as needed
  } else {
    likeIcon.classList.remove("fa-solid");
    likeIcon.classList.add("fa-regular");
    likeIcon.style.color = "#000000"; // Set the color as needed
  }
}

function toggleDislikeIcon() {
  var dislikeIcon = document.getElementById("dislikeIcon");

  // Toggle between the regular and solid thumbs-down icons
  if (dislikeIcon.classList.contains("fa-regular")) {
    dislikeIcon.classList.remove("fa-regular");
    dislikeIcon.classList.add("fa-solid");
    dislikeIcon.style.color = "#0c0d0d"; // Set the color as needed
  } else {
    dislikeIcon.classList.remove("fa-solid");
    dislikeIcon.classList.add("fa-regular");
    dislikeIcon.style.color = "#000000"; // Set the color as needed
  }
}

async function handleVotePost(voteType) {
  const us = await userInfo();
  const usId = us.userId;
  //const typeSelect = document.querySelector('#VoteType');

  var model = {
    userID: usId,
    postID: postId,
    voteTypeID: voteType,
  };

  try {
    const response = await votePost(model);

    if (response != null) {
      location.reload();
    } else {
      alert("Fail to vote post!");
    }
  } catch (error) {
    console.error("Error creating vote:", error);
  }
}
document.querySelector("#likeIcon").addEventListener("click", () => {
  handleVotePost(1);
  //toggleLikeIcon();
});

document.querySelector("#dislikeIcon").addEventListener("click", () => {
  handleVotePost(2);
  //toggleDislikeIcon();
});

//TEACHER GIVE AWARD
const showFormForTeacher = async () => {
  try {
    const usersInfo = await userInfo();
    const userRole = usersInfo.role_id;

    if (userRole === "Teacher") {
      // Display the form
      document.getElementById("createAward").style.display = "block";
    } else {
      // Hide the form
      document.getElementById("createAward").style.display = "none";
    }
  } catch (error) {
    console.error("Error retrieving user info:", error);
  }
};

showFormForTeacher();

//GET LIST AWARD TYPE
const displayAwardType = (types) => {
  const selectElement = document.getElementById("AwardType");

  types.forEach((type) => {
    const option = document.createElement("option");
    option.value = type.id;
    option.textContent = type.awardType;

    selectElement.appendChild(option);
  });
};

getAllAwardType().then((types) => {
  displayAwardType(types);
});

const reportButton = document.getElementById("reportButton");
reportButton.addEventListener("click", async function () {
  // const postsId = reportButton.getAttribute('data-post-id');

  const width = window.innerWidth;
  const height = window.innerHeight;
  const left = 0;
  const top = 0;
  const url = `reportPost.html?postId=${postId}`;

  window.open(
    url,
    "Report Violation",
    `width=${width}, height=${height}, left=${left}, top=${top}`
  );
});

// //Give Award
// const handleCreateAward = async (event) => {
//   event.preventDefault();

//   const awardTypeSelect = document.querySelector("#AwardType");
//   const us = await userInfo();
//   const usId = us.userId;

//   var model ={
//       awardTypeID : awardTypeSelect.value,
//       postID: postId,
//       givenByUserID: usId
//   }
//   try {
//       const response = await createAward(model);

//         const responseData = response;
//         if (response) {
//           alert('Give Award Successfully!');
//         } else {
//           const errorMessage = responseData ? responseData : 'Unknown error occurred.';
//           alert(errorMessage);
//         }
//     } catch (error) {
//       console.error(error);
//     }

// };

const handleCreateAward = async (event) => {
  event.preventDefault();

  const awardTypeSelect = document.querySelector("#AwardType");
  const us = await userInfo();
  const usId = us.userId;

  const model = {
    awardTypeID: awardTypeSelect.value,
    postID: postId,
    givenByUserID: usId,
  };

  try {
    const response = await createAward(model);

    if (response && response.status !== undefined) {
      switch (response.status) {
        case 200:
          // Handle successful response
          alert("Give Award successfully!");
          break;
        case 400:
          // Handle bad request
          const errorMessage = await response.text();
          handleBadRequestAlert(errorMessage);
          break;
        case 401:
          // Handle unauthorized
          alert("Unauthorized. Please log in.");
          break;
        case 500:
          // Handle internal server error
          alert("Internal Server Error. Please try again later.");
          break;
        default:
          // Handle other status codes
          alert(`Failed to give award. Status: ${response.status}`);
      }
    }
  } catch (error) {
    console.error(error);
    // Handle the error as needed
  }
};

const handleBadRequestAlert = (errorMessage) => {
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

const createAwardForm = document.querySelector("#createAward");
createAwardForm.addEventListener("submit", handleCreateAward);
