
import { getPostByCategory } from './Services/post.service.js';

const urlParams = new URLSearchParams(window.location.search);
const categoryName = urlParams.get('categoryName');

function displayPosts(response) {

  // Get the container element
  var container = document.querySelector('.ListPost');

  // Loop through the JSON response
  for (var i = 0; i < response.length; i++) {
    var post = response[i];

    // Create the post element
    var postElement = document.createElement('div');
    postElement.classList.add('post');

    // Create the anchor element
    var anchorElement = document.createElement('a');
    anchorElement.href = `blogDetail.html?belongedToPostID=${post.postsId}`;

    // Create the title element
    var titleElement = document.createElement('h2');
    titleElement.classList.add('post-title');
    titleElement.textContent = post.title;
    anchorElement.appendChild(titleElement);

    // Create the post info element
    // Create the info element
    var infoElement = document.createElement('p');
    infoElement.classList.add('post-info');
    infoElement.classList.add('two-line-preview');
    infoElement.innerHTML = post.postDetail;
    anchorElement.appendChild(infoElement);

    // Create the tags element
    var tagsElement = document.createElement('div');
    tagsElement.classList.add('tags');
    
    
    // if(post.tagList.length > 0){
    //   // var iconTagElement = document.createElement('div');
    //   // iconTagElement.classList.add('icon-tag');
    //   // var iconTagImageElement = document.createElement('img');
    //   // iconTagImageElement.src = '/img/tag.png';
    //   // iconTagImageElement.alt = 'icon-tag';
    //   // iconTagElement.appendChild(iconTagImageElement);
    //   // tagsElement.appendChild(iconTagElement);
      
    //   post.tagList.forEach(tag => {
    //     const tagLink = document.createElement('a');
    //     tagLink.href = `/pageByTag.html?tagName=${encodeURIComponent(tag)}`;
    //     const tagDiv = document.createElement('div');
    //     tagDiv.className = '';
    //     tagDiv.textContent = tag;
    //     tagLink.appendChild(tagDiv);
    //     tagsElement.appendChild(tagLink);
    //   });

    //   postElement.appendChild(tagsElement);
    // }

    // Append all elements to the post element
    postElement.appendChild(anchorElement);
    

    // Append the post element to the container
    container.appendChild(postElement);
  }
}

  const displayAllPosts = () => {
    getPostByCategory(categoryName)
      .then((posts) => {
        displayPosts(posts);
      })
      .catch((error) => {
        console.error(error);
        // Handle the error as needed
      });
  };

  displayAllPosts();