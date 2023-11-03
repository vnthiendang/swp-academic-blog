import { createPost } from "../js/Services/post.service.js";
import { userInfo } from "./Services/auth.service.js";
import { getAllCategory } from "./Services/category.service.js";

//create post
const displayCategories = (categories) => {
  const selectElement = document.getElementById('Category');

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
  const imageInput = document.getElementById("image-input");
  imageInput.addEventListener("change", handleImageUpload);
});

function handleImageUpload(event) {
  const file = event.target.files;

  if (file.length > 0) {

    const form = document.getElementById('createPost');
    form.addEventListener('submit', async (event) => {
      event.preventDefault();
    
      const titleInput = document.querySelector('#createPost input[type="text"]');
        //const allowCommentSelect = document.querySelector('#comment');
      const categorySelect = document.querySelector('#Category');
      const textEditor = document.querySelector('#text-editor');
      const tagList = Array.from(document.querySelectorAll("#tags li")).map(li => parseInt(li.textContent));
        
      const us = await userInfo();
      const userId = us.userId;

      const config = {
        headers: {
            'Content-Type': 'multipart/form-data',
            Authorization: `Bearer ${localStorage.getItem("token")}`
        }
      };

        const post = {
          categoryID: parseInt(categorySelect.value),
          title: titleInput.value,
          userID: userId,
          detail: textEditor.innerHTML,
          mediaList: file[0],
          tagList: tagList
        }
    
          const response = await createPost(post, config);
          if (response == null) {
            alert("Sorry! Please check your input! ");
            
          } else {
            alert('Your Post created successfully!');
            window.location.href = '/home.html';
          }
      });
  }
}
