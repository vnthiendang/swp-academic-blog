import { userInfo } from "./Services/auth.service.js";
import { getAllCategory } from "./Services/category.service.js";
import { updatePost } from "./Services/post.service.js";
import { getAllTag } from "./Services/tag.service.js";
import * as request from './utils/request.js';

const token = localStorage.getItem("token");

const urlParams = new URLSearchParams(window.location.search);
const postId = urlParams.get('belongedToPostID');

//EDIT POST
function displayEditPost() {

  // post.mediaList.forEach(media => {
  //   const mediaItem = document.getElementById('postMedia');
  //   mediaItem.src = `data:image/jpeg;base64, ${media}`;
  //   mediaItem.style.width = '240px'; 
  //   mediaItem.style.height = 'auto';
  //   //mediaList.appendChild(mediaItem);

  request.get(`post/GetAll/${postId}`, {
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
      // post.tagList.forEach((tag) => {
      //   const tagItem = document.getElementById('tags');
      //   tagItem.textContent = tag;
      // });

      document.getElementById('opt').textContent = post.belongedToCategory;
      document.getElementById('tagLists').textContent = post.tagList;

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
    option.value = category.content;
    option.textContent = category.content;

    selectElement.appendChild(option);
  });
};

getAllCategory()
  .then((cates) => {
    displayCategories(cates);
});

const displayTags = async (tags) => {
  const selectElement = document.getElementById('tags');
  //const listTags = document.getElementById('listTags');

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

const getChosenTags = () => {
  const selectElement = document.getElementById('tags');
  const selectedOptions = Array.from(selectElement.selectedOptions);
  const chosenTags = selectedOptions.map(option => option.value);
  return chosenTags > 0 ? chosenTags : [document.getElementById('tagLists').textContent];
};

document.addEventListener("DOMContentLoaded", function () {
  const mediaInput = document.getElementById('mediaList');
  // const selectedFiles = mediaInput.files;
  // const mediaList = Array.from(selectedFiles);

  const form = document.getElementById('updatePost');
  form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const textElement = document.querySelector('#text-editor');
    const text = textElement.textContent || textElement.innerText;
    
    const words = text.trim().split(/\s+/);
    
    const wordCount = words.length;
    
    if (wordCount < 100) {
      alert('Your post detail should have at least 100 words.');
      return;
    }

    const titleInput = document.querySelector('#updatePost input[type="text"]');
    //const allowCommentSelect = document.querySelector('#comment');
    const categorySelect = document.querySelector('#opt');
    const textEditor = document.querySelector('#text-editor');
    
    const usersInfo = await userInfo();
    const userId = usersInfo.userId;

    const formData = new FormData();
    formData.append('categoryName', categorySelect.textContent);
    formData.append('title', titleInput.value);
    formData.append('userID', userId);
    formData.append('detail', textEditor.innerHTML);
    if (mediaInput.files.length > 0) {
      for (let i = 0; i < mediaInput.files.length; i++) {
        formData.append('mediaList', mediaInput.files[i]);
      }
    } else {
      formData.append('mediaList', new Blob([]));
    }
    formData.append('tagList', getChosenTags());

    const res = await updatePost(postId, formData);
    if (res != null) {
      alert('Your Post Edit successfully!');
      window.location.href = '/profile.html';
    } else {
      alert(res);
    }
  });
});