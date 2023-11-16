import { getPostByTags } from "./Services/post.service.js";

const urlParams = new URLSearchParams(window.location.search);
const tagName = urlParams.get('tagName');

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
  
      // var imgElement = document.createElement('img');
      // post.mediaList.forEach(media => {
      //   const mediaItem = document.createElement('img');
      //   mediaItem.src = `data:image/jpeg;base64, ${media}`;
      //   mediaItem.style.width = '240px'; 
      //   mediaItem.style.height = 'auto';
      //   anchorElement.appendChild(mediaItem);
      // });
      
  
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
  
      // Create the icon-tag element
      var iconTagElement = document.createElement('div');
      iconTagElement.classList.add('icon-tag');
      var iconTagImageElement = document.createElement('img');
      iconTagImageElement.src = '/img/tag.png';
      iconTagImageElement.alt = 'icon-tag';
      iconTagElement.appendChild(iconTagImageElement);
      tagsElement.appendChild(iconTagElement);
  
      // Create the tag element
      var tagElement = document.createElement('a');
      tagElement.href = '/Page SE SA AI BS/html/pageTechnoloy.html';
      var tagSpanElement = document.createElement('span');
      tagSpanElement.classList.add('tag');
      tagSpanElement.textContent = post.tagList[0];
      tagElement.appendChild(tagSpanElement);
      tagsElement.appendChild(tagElement);
  
      // Append all elements to the post element
      postElement.appendChild(anchorElement);
      postElement.appendChild(tagsElement);
  
      // Append the post element to the container
      container.appendChild(postElement);
    }
}

  const displayAllPosts = () => {
    getPostByTags(tagName)
      .then((posts) => {
        displayPosts(posts);
      })
      .catch((error) => {
        console.error(error);
        // Handle the error as needed
      });
  };

  displayAllPosts();