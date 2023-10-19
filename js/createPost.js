import { createPost } from "../js/Services/post.service.js";

  
  const form = document.getElementById('createPost');
  form.addEventListener('submit', async (event) => {
    event.preventDefault();
  
    const titleInput = document.querySelector('#createPost input[type="text"]');
    const allowCommentSelect = document.querySelector('#comment');
    const categorySelect = document.querySelector('#Category');
    const textEditor = document.querySelector('#text-editor');
  
    const post = {
      title: titleInput.value,
      tag: 9,
      category: parseInt(categorySelect.value),
      detail: textEditor.innerHTML,
      media: 'https://files.fullstack.edu.vn/f8-prod/public-images/65164dd850098.png'
    };
  
    try {
      await createPost(post);
      window.location.href = '/home.html';
    } catch (error) {
      
    }
  });