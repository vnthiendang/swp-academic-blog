import { createComment, getCommentById } from "./Services/post.service.js";
import * as request from './utils/request.js';
import { userInfo } from "./Services/auth.service.js";
import { getVoteType, votePost } from "./Services/vote.service.js";
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
      document.getElementById('postTag').textContent = post.tagList ?? '';
      document.getElementById('postCategory').textContent = post.belongedToCategory;
      document.getElementById('postContent').textContent = post.postDetail;
      document.getElementById('postMedia').src = post.mediaList ?? '#';
      document.getElementById('postVote').textContent = post.vote1Count ?? '#';
      document.getElementById('readingTime').textContent = post.readingTime + ' readed' ?? '';
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
        commentByUser.textContent = 'User ' + comment.createdByUser;
        
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

const displayVoteType = (types) => {
  const selectElement = document.getElementById('VoteType');

  types.forEach(type => {
    const option = document.createElement('option');
    option.value = type.id;
    option.textContent = type.voteType;

    selectElement.appendChild(option);
  });
};

getVoteType()
  .then((votes) => {
    displayVoteType(votes);
});


const form = document.getElementById("comment");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const commentText = document.getElementById("cmt").value;
  const us = await userInfo();
  const createdByUserID = us.userId;

  const data = { commentText, belongedToPostID, createdByUserID };

    const res = await createComment(data);
    if(res == null){
      alert("Sorry! Please check your comment! ");
    }else{
      location.reload();
    }

});

//STUDENT VOTE POST
const showFormForStudent = async () => {
  try {
    const usersInfo = await userInfo();
    const userRole = usersInfo.role_id; // Assuming the role is accessible as `userInfo.role`

    if (userRole === 'Student') {
      // Display the form
      document.getElementById('createVote').style.display = 'block';
    } else {
      // Hide the form
      document.getElementById('createVote').style.display = 'none';
    }
  } catch (error) {
    console.error('Error retrieving user info:', error);
  }
};

showFormForStudent();

async function handleVotePost() {
    const us = await userInfo();
    const usId = us.userId;

    const typeSelect = document.querySelector('#VoteType');


    var model = {
      userID: usId,
      postID: belongedToPostID,
      voteTypeID: typeSelect.value
    };

    try {
      // Call the votePost method passing the model
      const response = await votePost(model);

      if(response != null){
        alert('Vote post success!');
      }
    } catch (error) {
      console.error('Error creating vote:', error);
    }
}
document.querySelector(".fa-heart").addEventListener("click", handleVotePost);


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


//STUDENT EDIT POST
const showFormEditPost = async () => {
  try {
    const usersInfo = await userInfo();
    const userRole = usersInfo.role_id; 

    if (userRole === 'Student') {
      // Display the form
      document.getElementById('editPost').style.display = 'block';
    } else {
      // Hide the form
      document.getElementById('editPost').style.display = 'none';
    }
  } catch (error) {
    console.error('Error retrieving user info:', error);
  }
};

showFormEditPost();


function openEditPostPopup() {

  const postData = JSON.parse(sessionStorage.getItem('postData'));
  
  const width = 600;
  const height = 400;
  const left = (window.innerWidth - width) / 2;
  const top = (window.innerHeight - height) / 2;
  const url = "editPost.html"; 
  
  window.open(url, "Edit Post", `width=${width}, height=${height}, left=${left}, top=${top}`);
}

// Add event listener to the button
const editButton = document.querySelector("#editPost button");
editButton.addEventListener("click", openEditPostPopup);

