import { getAllCategory } from "./Services/category.service.js";
import { updatePost } from "./Services/post.service.js";
import * as request from './utils/request.js';

const token = localStorage.getItem("token");

const urlParams = new URLSearchParams(window.location.search);
const postId = urlParams.get('belongedToPostID');
console.log('Post ID: '+postId);

//EDIT POST
function displayEditPost() {    
  
        // post.mediaList.forEach(media => {
        //   const mediaItem = document.getElementById('postMedia');
        //   mediaItem.src = `data:image/jpeg;base64, ${media}`;
        //   mediaItem.style.width = '240px'; 
        //   mediaItem.style.height = 'auto';
        //   //mediaList.appendChild(mediaItem);

        request.get(`post/GetAllApproved/${postId}`, {
          headers: {
            Authorization: `Bearer ${token}`
          }
        })
          .then(response => {
            const post = response;
      
            // Store the post data in session storage
            sessionStorage.setItem('postData', JSON.stringify(post));
      
            document.getElementById('title').value = post.title;
            //document.getElementById('tags').textContent = post.tagList ?? '';
            post.tagList.forEach((tag) => {
              const tagItem = document.getElementById('tags');
              tagItem.textContent = tag;
            });
            
            document.getElementById('opt').textContent = post.belongedToCategory;
      
            const postContentElement = document.getElementById('text-editor');
            postContentElement.innerHTML = post.postDetail;
            postContentElement.contentEditable = true;
      
            // post.mediaList.forEach(media => {
            //   const mediaItem = document.getElementById('postMedia');
            //   mediaItem.src = `data:image/jpeg;base64, ${media}`;
            //   mediaItem.style.width = '240px'; 
            //   mediaItem.style.height = 'auto';
            //   //mediaList.appendChild(mediaItem);
            // });
          })
          .catch(error => {
            console.error('Error display post:', error);
          });
  
  }
  
  displayEditPost();

  const displayCategories = (categories) => {
    const selectElement = document.getElementById('categoryID');
  
    categories.forEach(category => {
      const option = document.createElement('option');
      option.value = category.id;
      option.textContent = category.content;
  
      selectElement.appendChild(option);
    });
  };
  
  getAllCategory()
    .then((cates) => {
      displayCategories(cates);
  });

  document.addEventListener("DOMContentLoaded", function() {
    const imageInput = document.getElementById("mediaList");
  
    const form = document.getElementById('updatePost');
    form.addEventListener('submit', async (event) => {
      event.preventDefault();
    
      const titleInput = document.querySelector('#updatePost input[type="text"]');
      //const allowCommentSelect = document.querySelector('#comment');
      const categorySelect = document.querySelector('#categoryID');
      const textEditor = document.querySelector('#text-editor');
      const tagList = Array.from(document.querySelectorAll("#tags li")).map(li => li.textContent);
        
      //const us = await userInfo();
      //const userId = us.userId;
  
      // const formData = new FormData();
      // formData.append('categoryID', parseInt(categorySelect.value));
      // formData.append('title', titleInput.value);
      // //formData.append('userID', userId);
      // formData.append('detail', textEditor.innerHTML);
      // for (let i = 0; i < imageInput.files.length; i++) {
      //   formData.append('mediaList', imageInput.files[i]);
      // }
      
      // formData.append('tagList', tagList);

      const mediaList = [];
      for (let i = 0; i < imageInput.files.length; i++) {
        mediaList.push(imageInput.files[i]);
      }

      const model = {
        categoryID: parseInt(categorySelect.value),
        title: titleInput.value,
        detail: textEditor.innerHTML,
        mediaList: [],
        tagList: tagList
      };
  
      try {
        
        const res = await updatePost(postId, model);
  
        if (res != null) {
          alert('Your Post Edit successfully!');
          window.location.href = '/home.html';
        } else {
          alert('Failed to edit post!');
        }
      } catch (error) {
        console.log(error);
      }
    });
  });