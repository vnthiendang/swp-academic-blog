import { createPost } from "../js/Services/post.service.js";
import { userInfo } from "./Services/auth.service.js";
import { getAllCategory } from "./Services/category.service.js";
import { getAllTag, searchTagByName } from "./Services/tag.service.js";

//create post
const displayCategories = (categories) => {
  const selectElement = document.getElementById('Category');

  categories.forEach(category => {
    const option = document.createElement('option');
    option.value = category.content;
    option.textContent = category.content;

    selectElement.appendChild(option);
  });
};

getAllCategory()
  .then((cates) => {
    displayCategories(cates);
});

// const searchForm = document.querySelector("#search-form");
// searchForm.addEventListener("submit", (event) => {
//   event.preventDefault();

//   const searchInput = document.querySelector("#tag-search");
//   const searchTerm = searchInput.value;
//   const searchResults = document.getElementById('searchResults');

//   if (searchTerm) {
//     searchTagByName(searchTerm)
//       .then((tags) => {
//         const resultItem = document.createElement('div');
//         resultItem.textContent = tags.tagName;
//         searchResults.appendChild(resultItem);
//       })
//       .catch((error) => {
//         console.error(error);
//       });
//   } else {
//     alert('Please enter a tag!');
//   }

//   searchInput.value = "";
// });

document.addEventListener("DOMContentLoaded", function() {
  const imageInput = document.getElementById("image-input");

  const form = document.getElementById('createPost');
  form.addEventListener('submit', async (event) => {
    event.preventDefault();

  const text = quill.getText();

  const words = text.split(/\s+/);

  const wordCount = words.length;

  // Check if the word count is less than 100
  if (wordCount < 100) {
    // Display an alert
    alert('Your post detail should have at least 100 words.');
    return;
  }
  
    const titleInput = document.querySelector('#createPost input[type="text"]');
    //const allowCommentSelect = document.querySelector('#comment');
    const categorySelect = document.querySelector('#Category');
    const textEditor = document.querySelector('#text-editor');
    //const tagList = Array.from(document.querySelector("#Tag")).map(li => li.textContent);
    const tagList = document.querySelector("#Tag");
      
    const us = await userInfo();
    const userId = us.userId;

    const formData = new FormData();
    formData.append('categoryName', categorySelect.value);
    formData.append('title', titleInput.value);
    formData.append('userID', userId);
    formData.append('detail', textEditor.innerHTML);
    if (imageInput.files.length > 0) {
      for (let i = 0; i < imageInput.files.length; i++) {
        formData.append('mediaList', imageInput.files[i]);
      }
    } else {
      // Append an empty array if no files are selected
      formData.append('mediaList', new Blob([]));
    }
    formData.append('tagList', tagList.value);

    const config = {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`
      }
    };

    try {
      const res = await createPost(formData, config);

      if (res != null) {
        alert('Your Post created successfully!');
        window.location.href = '/home2.html';
      } else {
        alert('Failed to create post.');
      }
    } catch (error) {
      alert('An error occurred while creating the post.');
      console.error(error);
    }
  });
});

const displayTags = async (tags) => {
  const selectElement = document.getElementById('Tag');
  const listTags = document.getElementById('listTags');

  tags.forEach(tag => {
    const option = document.createElement('option');
    option.value = tag.tagName;
    option.textContent = tag.tagName;

    // const tagItem = document.createElement('div');
    // tagItem.textContent = tag.tagName;
    // listTags.appendChild(tagItem);

    selectElement.appendChild(option);
  });
};

getAllTag().then((tags) => {
  displayTags(tags);
});
