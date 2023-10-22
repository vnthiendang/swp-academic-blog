
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

  axios.get(`http://localhost:8080/blog/post/postRequest/${postId}`, {
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
      document.getElementById('postTag').textContent = post.postTagList ?? 'tag';
      document.getElementById('postCategory').textContent = post.belongedToCategory ?? 'category';
      document.getElementById('postContent').textContent = post.postDetail;
      document.getElementById('postMedia').src = post.mediaList[0] ?? '#';
    })
    .catch(error => {
      console.error('Error retrieving post:', error);
      // Handle error display or logging
    });
}

// Call the function to display the post
displayPost();

const approveButton = document.getElementById("approveButton");
const rejectButton = document.getElementById("rejectButton");

// Attach an event listener to the button
approveButton.addEventListener("click", function() {
  approvePost(postId);
});
rejectButton.addEventListener("click", function(){
  rejectPost(postId);
});


function approvePost(postId) {
  const baseUrl = 'http://localhost:8080/blog/post/postRequest/approve';

  // Create the request URL by appending the postId to the base URL
  const requestUrl = `${baseUrl}/${postId}`;

  // Send the POST request using Axios
  axios.post(requestUrl)
    .then(response => {
      // Logic to handle successful approval
      alert('Post approved successfully.');
      // Redirect or perform any necessary actions
    })
    .catch(error => {
      // Logic to handle unsuccessful approval
      alert('Failed to approve post:', error);
      // Handle the error or display an appropriate message
    });
}

// Import Axios library (if using a module bundler like Webpack)
// import axios from 'axios';

function rejectPost(postId) {
  const baseUrl = 'http://localhost:8080/blog/post/postRequest/reject';

  // Create the request URL by appending the postId to the base URL
  const requestUrl = `${baseUrl}/${postId}`;

  // Send the POST request using Axios
  axios.post(requestUrl)
    .then(response => {
      // Logic to handle successful approval
      alert('Post rejected successfully.');
      // Redirect or perform any necessary actions
    })
    .catch(error => {
      // Logic to handle unsuccessful approval
      console.error('Failed to reject post:', error);
      // Handle the error or display an appropriate message
    });
}