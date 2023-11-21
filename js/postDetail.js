import { createComment, getCommentById } from "./Services/post.service.js";
import * as request from './utils/request.js';
import { userInfo } from "./Services/auth.service.js";
import { votePost } from "./Services/vote.service.js";
import { createAward, getAllAwardType } from './Services/award.service.js';

const token = localStorage.getItem("token");

const options = {
  month: 'short', // Two-digit month (e.g., 01)
  day: '2-digit', // Two-digit day (e.g., 18)
  hour: '2-digit', // Two-digit hour (e.g., 14)
};

  // Get the belongedToPostID from the URL query parameters
  const urlParams = new URLSearchParams(window.location.search);
  const belongedToPostID = urlParams.get('belongedToPostID');

function displayPost() {

  request.get(`post/GetAllApproved/${belongedToPostID}`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
    .then(response => {
      const post = response;

      // Store the post data in session storage
      sessionStorage.setItem('postData', JSON.stringify(post));

      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString('en-US', options);

      // Update HTML elements with post data
      document.getElementById('postTitle').textContent = post.title;
      document.getElementById('postAuthor').textContent = post.createdByUser;
      document.getElementById('postDate').textContent = formattedTime;
      if(post.tagList.length > 0){
        const iconTagElement = document.createElement("div");
        iconTagElement.className = "icon-tag";
        const iconTagImg = document.createElement("img");
        iconTagImg.src = "img/tag.png";
        iconTagImg.alt = "icon-tag";
        iconTagElement.appendChild(iconTagImg);

        const tagLink1 = document.createElement("a");
        tagLink1.href = "#";

        post.tagList.forEach((tag) => {
          //const tagItem = document.createElement("span");
          //tagItem.textContent = tag;
          document.getElementById('postTag').textContent = tag;
          
        });
      }
      
      document.getElementById('postCategory').textContent = post.belongedToCategory;

      const postContentElement = document.getElementById('postContent');
      postContentElement.contentEditable = false;
      postContentElement.innerHTML = post.postDetail;

      // post.mediaList.forEach(media => {
      //   const mediaItem = document.getElementById('postMedia');
      //   mediaItem.src = `data:image/jpeg;base64, ${media}`;
      //   mediaItem.style.width = '240px'; 
      //   mediaItem.style.height = 'auto';
      //   //mediaList.appendChild(mediaItem);
      // });
      
      const readingTimeElement = document.getElementById('readingTime');
      const minuteText = post.readingTime === 1 ? 'minute' : 'minutes';
      readingTimeElement.textContent = post.readingTime + ' ' + minuteText + '' || '';
      document.getElementById('postVote').textContent = post.vote1Count ?? '#';
      document.getElementById('postVote2').textContent = post.vote2Count ?? '#';
    })
    .catch(error => {
      console.error('Error retrieving post:', error);
      // Handle error display or logging
    });
}

// Call the function to display the post
displayPost();



function displayComments() {
  const commentContainer = document.getElementById('commentContainer');

  // Get comments by belongedToPostID
  getCommentById(belongedToPostID)
    .then((comments) => {
      // Clear existing comments
      commentContainer.innerHTML = '';

      // Iterate over comments and create HTML elements
      comments.forEach(async (comment) => {
        const article = document.createElement('article');
        article.classList.add('p-6', 'mb-3', 'text-base', 'bg-white', 'border-t', 'border-gray-200', 'dark:border-gray-700', 'dark:bg-gray-900');

        const footer = document.createElement('footer');
        footer.classList.add('flex', 'justify-between', 'items-center', 'mb-2');

        const commentByUser = document.createElement('p');
        commentByUser.classList.add('inline-flex', 'items-center', 'mr-3', 'text-sm', 'text-gray-900', 'dark:text-white', 'font-semibold');
        commentByUser.textContent = comment.createdByUser;
        
        footer.appendChild(commentByUser);

        article.appendChild(footer);

        const commentText = document.createElement('p');
        commentText.classList.add('text-gray-500', 'dark:text-gray-400');
        commentText.textContent = comment.commentText;
        article.appendChild(commentText);

        const buttonContainer = document.createElement('div');
        buttonContainer.classList.add('flex', 'items-center', 'mt-4', 'space-x-4');

        const replyButton = document.createElement('button');
        replyButton.type = 'button';
        replyButton.classList.add('flex', 'items-center', 'text-sm', 'text-gray-500', 'hover:underline', 'dark:text-gray-400', 'font-medium');
        replyButton.innerHTML = '<i class="fa-regular fa-comment px-2"></i>Reply';
        buttonContainer.appendChild(replyButton);

        article.appendChild(buttonContainer);

        commentContainer.appendChild(article);
      });
    })
    .catch((error) => {
      console.error('Error retrieving comments:', error);
    });
}


// Call the function to display the post
displayComments();


const form = document.getElementById("comment");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const commentText = document.getElementById("cmt").value.trim();

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

    if (userRole === 'Student') {
      // Display the form
      document.getElementById('likeIcon').style.display = 'block';
      document.getElementById('dislikeIcon').style.display = 'block';
    } else {
      document.getElementById('likeIcon').style.pointerEvents = 'none';
      document.getElementById('dislikeIcon').style.pointerEvents = 'none';
    }
  } catch (error) {
    console.error('Error retrieving user info:', error);
  }
};

showFormForStudent();

function toggleLikeIcon() {
  var likeIcon = document.getElementById('likeIcon');
  
  // Toggle between the regular and solid thumbs-up icons
  if (likeIcon.classList.contains('fa-regular')) {
      likeIcon.classList.remove('fa-regular');
      likeIcon.classList.add('fa-solid');
      likeIcon.style.color = '#000000'; // Set the color as needed
  } else {
      likeIcon.classList.remove('fa-solid');
      likeIcon.classList.add('fa-regular');
      likeIcon.style.color = '#000000'; // Set the color as needed
  }
}

function toggleDislikeIcon() {
  var dislikeIcon = document.getElementById('dislikeIcon');
  
  // Toggle between the regular and solid thumbs-down icons
  if (dislikeIcon.classList.contains('fa-regular')) {
      dislikeIcon.classList.remove('fa-regular');
      dislikeIcon.classList.add('fa-solid');
      dislikeIcon.style.color = '#0c0d0d'; // Set the color as needed
  } else {
      dislikeIcon.classList.remove('fa-solid');
      dislikeIcon.classList.add('fa-regular');
      dislikeIcon.style.color = '#000000'; // Set the color as needed
  }
}

async function handleVotePost(voteType) {
    const us = await userInfo();
    const usId = us.userId;
    //const typeSelect = document.querySelector('#VoteType');

    var model = {
      userID: usId,
      postID: belongedToPostID,
      voteTypeID: voteType
    };

    try {
      const response = await votePost(model);

      if(response != null){
        location.reload();
      }else{
        alert('Fail to vote post!');
      }
    } catch (error) {
      console.error('Error creating vote:', error);
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

    if (userRole === 'Teacher') {
      // Display the form
      document.getElementById('createAward').style.display = 'block';
    } else {
      // Hide the form
      document.getElementById('createAward').style.display = 'none';
    }
  } catch (error) {
    console.error('Error retrieving user info:', error);
  }
};

showFormForTeacher();

//GET LIST AWARD TYPE
const displayAwardType = (types) => {
  const selectElement = document.getElementById('AwardType');

  types.forEach(type => {
    const option = document.createElement('option');
    option.value = type.id;
    option.textContent = type.awardType;

    selectElement.appendChild(option);
  });
};

getAllAwardType().then((types) => {
  displayAwardType(types);
});

const reportButton = document.getElementById('reportButton');
reportButton.addEventListener('click', async function() {
    //const postId = editButton.getAttribute('data-post-id');
  
    const width = window.innerWidth;
    const height = window.innerHeight;
    const left = 0;
    const top = 0;
    const url = `reportPost.html` ;
  
    window.open(url, "Report Violation", `width=${width}, height=${height}, left=${left}, top=${top}`);

});

//Give Award
const handleCreateAward = async (event) => {
  event.preventDefault();

  const awardTypeSelect = document.querySelector("#AwardType");
  const us = await userInfo();
  const usId = us.userId;

  var model ={
      awardTypeID : awardTypeSelect.value,
      postID: belongedToPostID,
      givenByUserID: usId
  }
  try {
      const response = await createAward(model);

      if(response != null){
        alert('Give Award successfully!');
      }
    } catch (error) {
      console.error(error);
      // Handle the error as needed
    }

};

const createAwardForm = document.querySelector("#createAward");
createAwardForm.addEventListener("submit", handleCreateAward);
