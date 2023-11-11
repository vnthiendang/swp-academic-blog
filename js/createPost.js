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
  //imageInput.addEventListener("change", handleImageUpload);

  const form = document.getElementById('createPost');
  form.addEventListener('submit', async (event) => {
    event.preventDefault();
  
    const titleInput = document.querySelector('#createPost input[type="text"]');
    //const allowCommentSelect = document.querySelector('#comment');
    const categorySelect = document.querySelector('#Category');
    const textEditor = document.querySelector('#text-editor');
    const tagList = Array.from(document.querySelectorAll("#tags li")).map(li => li.textContent);
      
    const us = await userInfo();
    const userId = us.userId;

    const formData = new FormData();
    formData.append('categoryID', parseInt(categorySelect.value));
    formData.append('title', titleInput.value);
    formData.append('userID', userId);
    formData.append('detail', textEditor.innerHTML);
    for (let i = 0; i < imageInput.files.length; i++) {
      formData.append('mediaList', imageInput.files[i]);
    }
    formData.append('tagList', tagList);

    const config = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    };

    try {
      //const response = await axios.post('http://localhost:8080/blog/post/create', formData, config);
      const res = await createPost(formData, config);

      if (res !== null) {
        alert('Your Post created successfully!');
        window.location.href = '/home.html';
      } else {
        alert('Failed to create post.');
      }
    } catch (error) {
      alert('An error occurred while creating the post.');
      console.error(error);
    }
  });
});
