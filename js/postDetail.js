import { getAllCategory } from "../js/Services/category.service.js";

const token = localStorage.getItem("token");

const options = {
  month: 'short', // Two-digit month (e.g., 01)
  day: '2-digit', // Two-digit day (e.g., 18)
  hour: '2-digit', // Two-digit hour (e.g., 14)
};

  // Get the postId from the URL query parameters
  const urlParams = new URLSearchParams(window.location.search);
  const postId = urlParams.get('postId');

function displayPost() {

  axios.get(`http://localhost:8080/blog/post/GetAllApproved/${postId}`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
    .then(response => {
      const post = response.data;

      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString('en-US', options);

      // Update HTML elements with post data
      document.getElementById('postTitle').textContent = post.title;
      document.getElementById('postAuthor').textContent = post.createdByUser;
      document.getElementById('postDate').textContent = formattedTime;
      document.getElementById('postTag').textContent = post.postTag ?? 'tag';
      document.getElementById('postCategory').textContent = post.belongedToCategory;
      document.getElementById('postContent').textContent = post.postDetail;
      document.getElementById('postMedia').src = post.media ?? '#';
    })
    .catch(error => {
      console.error('Error retrieving post:', error);
      // Handle error display or logging
    });
}

// Call the function to display the post
displayPost();


function displayComments() {

  axios.get(`http://localhost:8080/blog/comment/${postId}`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
    .then(response => {
      const comment = response.data;

      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString('en-US', options);

      // Update HTML elements with post data
      document.getElementById('postTitle').textContent = post.title;
      document.getElementById('postAuthor').textContent = post.createdByUser;
      document.getElementById('postDate').textContent = formattedTime;
      document.getElementById('postTag').textContent = post.postTag ?? 'tag';
      document.getElementById('postCategory').textContent = post.belongedToCategory;
      document.getElementById('postContent').textContent = post.postDetail;
      document.getElementById('postMedia').src = post.media ?? '#';
    })
    .catch(error => {
      console.error('Error retrieving post:', error);
      // Handle error display or logging
    });
}

// Call the function to display the post
displayComments();

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


const createComment = async (model) => {
  try {
    const response = await axios.post(`http://localhost:8080/blog/comment/post`, model,{
      headers: {
        Authorization: `Bearer ${token}`
      }
    });

    if (response.status === 200) {
      alert('Your Comment posted!');
    }
  } catch (error) {
    console.error(error);
    if (error.response) {
      console.error("Response status:", error.response.status);
    }
  }
};

export{
  createComment
}

