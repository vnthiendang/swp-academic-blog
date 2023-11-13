import { createPost } from "../js/Services/post.service.js";
import { userInfo } from "./Services/auth.service.js";
import { getAllCategory } from "./Services/category.service.js";

const urlParams = new URLSearchParams(window.location.search);
const belongedToPostID = urlParams.get('belongedToPostID');

//EDIT POST
function displayEditPost() {
    // const postId = event.target.dataset.postsId;

    const postData = JSON.parse(sessionStorage.getItem('postData'));      
  
      postData.forEach((post, index) => {
        document.getElementById('title').textContent = post.title;
        document.getElementById('tags').textContent = post.tagList ?? '';
        document.getElementById('categoryID').textContent = post.belongedToCategory;
  
        const postContentElement = document.getElementById('text-editor');
        postContentElement.innerHTML = post.postDetail;
  
        // post.mediaList.forEach(media => {
        //   const mediaItem = document.getElementById('postMedia');
        //   mediaItem.src = `data:image/jpeg;base64, ${media}`;
        //   mediaItem.style.width = '240px'; 
        //   mediaItem.style.height = 'auto';
        //   //mediaList.appendChild(mediaItem);
      });
  
  }
  
  // Call the function to display the post
  displayEditPost();