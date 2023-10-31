import { getAllAwardType } from './Services/award.service.js';
import { userInfo } from './Services/profile.service.js';
import * as request from './utils/request.js';
const token = localStorage.getItem("token");

    const options = {
        month: 'short', // Two-digit month (e.g., 01)
        day: '2-digit', // Two-digit day (e.g., 18)
        hour: '2-digit', // Two-digit hour (e.g., 14)
      };

  // Get the postId from the URL query parameters
  const urlParams = new URLSearchParams(window.location.search);
  const postId = urlParams.get('postId');

function displayPost() {

  request.get(`post/postRequest/${postId}`, {
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
      document.getElementById('postTag').textContent = post.tagList ?? '';
      document.getElementById('postCategory').textContent = post.belongedToCategory ?? '';
      document.getElementById('postContent').textContent = post.postDetail;
      document.getElementById('postMedia').src = post.mediaList[0] ?? '#';
    })
    .catch(error => {
      console.error('Error retrieving post:', error);
      // Handle error display or logging
    });
}

// Call the function to display the post
displayPost();

const approveButton = document.getElementById("approveButton");
const rejectButton = document.getElementById("rejectButton");

// Attach an event listener to the button
approveButton.addEventListener("click", async (event) =>{
  event.preventDefault();

  const user = await userInfo();
  const usId = user.userId;

  const post = {
    post: postId,
    viewedByUser: usId,
    postApprovalsStatus: "APPROVED"
  }

  const response = await approvePost(post);

  if(response == null){
    alert('Fail to approve!');
  }else{
    alert('Post approved!');
    window.location.href = "../teacherPage.html";
  }
});

rejectButton.addEventListener("click", async (event) =>{
  event.preventDefault();

  const user = await userInfo();
  const usId = user.userId;

  const post = {
    post: postId,
    viewedByUser: usId,
    postApprovalsStatus: "REJECTED"
  }

  const response = await rejectPost(post);

  if(response == null){
    alert('Fail to reject!');
  }else{
    alert('Post rejected!');
    window.location.href = "../teacherPage.html";
  }
});


const approvePost = async (model) => {
  try {
    const response = await request.post(`postapproval/post`, model, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error approve post:', error);
  } 
};

const rejectPost = async (model) => {
  try {
    const response = await request.post(`postapproval/post`, model, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response;
  } catch (error) {
    console.error('Error reject post:', error);
  }
  
}

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