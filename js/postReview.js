import { userInfo } from './Services/profile.service.js';
import * as request from './utils/request.js';
const token = localStorage.getItem("token");

const options = {
  month: 'short', 
  day: '2-digit', 
  hour: '2-digit', 
};

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
      document.getElementById('postContent').innerHTML = post.postDetail;
      
      if(post.mediaList.length > 0){

        // post.mediaList.forEach(media => {
        // const mediaList = post.mediaList;
          
        //   mediaList.appendChild(mediaItem);
        // });

        const imageElement = document.getElementById('postMedia');
        imageElement.src = `data:image/jpeg;base64, ${post.mediaList[0]}`;
        // imageElement.style.width = '240px'; 
        // imageElement.style.height = 'auto';

        const readingTimeElement = document.getElementById('readingTime');
        const minuteText = post.readingTime === 1 ? 'minute' : 'minutes';
        readingTimeElement.textContent = post.readingTime + ' ' + minuteText + '' || '';
        document.getElementById('postVote').textContent = post.vote1Count ?? '#';
        document.getElementById('postVote2').textContent = post.vote2Count ?? '#';
      }

    })
    .catch(error => {
      console.error('Error retrieving post:', error);
      // Handle error display or logging
    });
}

// Display the post
displayPost();

const approveButton = document.getElementById("approveButton");
const rejectButton = document.getElementById("rejectButton");

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

function arrayBufferToBase64(buffer) {
  let binary = '';
  const bytes = new Uint8Array(buffer);
  const len = bytes.byteLength;
  for (let i = 0; i < len; i++) {
    binary += String.fromCharCode(bytes[i]);
  }
  return btoa(binary);
}