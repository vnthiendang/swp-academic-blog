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

const form = document.getElementById('createPost');
form.addEventListener('submit', async (event) => {
  event.preventDefault();

  const titleInput = document.querySelector('#createPost input[type="text"]');
  //const allowCommentSelect = document.querySelector('#comment');
  const categorySelect = document.querySelector('#Category');
  const textEditor = document.querySelector('#text-editor');
  //const fileUpload = document.querySelector("#input-file");

  const us = await userInfo();
  const userId = us.userId;

    // const { files } = event.target;
    // console.log("files", files);
    const post = {
      categoryID: parseInt(categorySelect.value),
      title: titleInput.value,
      userID: userId,
      detail: textEditor.innerHTML,
      mediaList: [],
      tagList: []
    };

    const response = await createPost(post);
    if (response == null) {
      alert("Sorry! Please check your input! ");
      
    } else {
      alert('Your Post created successfully!');
      window.location.href = '/home.html';
    }
});