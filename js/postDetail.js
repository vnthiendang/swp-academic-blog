import { createComment, getCommentById } from "./Services/post.service.js";
import * as request from './utils/request.js';
import { userInfo } from "./Services/auth.service.js";

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

      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString('en-US', options);

      // Update HTML elements with post data
      document.getElementById('postTitle').textContent = post.title;
      document.getElementById('postAuthor').textContent = post.createdByUser;
      document.getElementById('postDate').textContent = formattedTime;
      document.getElementById('postTag').textContent = post.tagList ?? 'tag';
      document.getElementById('postCategory').textContent = post.belongedToCategory;
      document.getElementById('postContent').textContent = post.postDetail;
      document.getElementById('postMedia').src = post.mediaList ?? '#';
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
      comments.forEach((comment) => {
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

        const voteButton = document.createElement('button');
        voteButton.type = 'button';
        voteButton.classList.add('flex', 'items-center', 'text-sm', 'text-gray-500', 'hover:underline', 'dark:text-gray-400', 'font-medium');
        voteButton.innerHTML = '<i class="fa-regular fa-thumbs-up mx-2"></i> Vote';
        buttonContainer.appendChild(voteButton);

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

