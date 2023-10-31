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
  const file = event.target.files[0];

  if (file) {
    const imageURL = URL.createObjectURL(file);
    const reader = new FileReader();

    reader.onload = function () {
      const imageData = reader.result;
      const binaryData = imageData.split(',')[1];
      
      console.log("Image data:", imageURL);
      const form = document.getElementById('createPost');
      form.addEventListener('submit', async (event) => {
        event.preventDefault();
    
        const titleInput = document.querySelector('#createPost input[type="text"]');
        //const allowCommentSelect = document.querySelector('#comment');
        const categorySelect = document.querySelector('#Category');
        const textEditor = document.querySelector('#text-editor');
        const tagList = Array.from(document.querySelectorAll("#tags li")).map(li => parseInt(li.textContent));
        console.log(tagList);
    
        const us = await userInfo();
        const userId = us.userId;

        const post = {
          categoryID: parseInt(categorySelect.value),
          title: titleInput.value,
          userID: userId,
          detail: textEditor.innerHTML,
          mediaList: [imageURL],
          tagList: tagList
        }
    
          const response = await createPost(post);
          if (response == null) {
            alert("Sorry! Please check your input! ");
            
          } else {
            alert('Your Post created successfully!');
            window.location.href = '/home.html';
          }
      });
    };

    reader.readAsDataURL(file);
  }
}
