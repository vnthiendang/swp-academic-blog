import { getPostRequest } from "./Services/request.service.js";


const options = {
    month: 'short', 
    day: '2-digit',
    hour: '2-digit',
};

// DISPLAY LIST POSTS
function displayPosts(posts) {
    
    const postContainer = document.querySelector('.col-span-5');
    postContainer.innerHTML = '';

    if(posts.length === 0){
      const noResultsElement = document.createElement('div');
      noResultsElement.className = 'text-center text-4xl font-bold text-gray-500 dark:text-gray-400';
      noResultsElement.textContent = 'No requested posts';
      postContainer.appendChild(noResultsElement);
    }else{
      posts.forEach(post => {
        
        const postElement = document.createElement('div');
        postElement.className = 'col-span-5';

        postElement.className = "post-container";
  
        const flexElement = document.createElement('div');
        flexElement.className = 'flex w-full px-8 py-4 items-center';
        flexElement.style.fontSize = '15px';
        flexElement.style.fontWeight = '500';
    
        const avatarImage = document.createElement('div');
        avatarImage.className = 'flex';
        const avatarImg = document.createElement('img');
        avatarImg.src = 'img/Img For User/User1.jpg';
        avatarImage.title = 'Avatar User';
        avatarImg.alt = 'avatar';
        avatarImg.className = 'rounded-full w-8';
        avatarImage.appendChild(avatarImg);
        flexElement.appendChild(avatarImage);
    
        const createdByUser = document.createElement('div');
        createdByUser.className = 'ml-4';
        createdByUser.title = 'Name Users';
        createdByUser.textContent = post.createdByUser;
        const grayTextSpan = document.createElement('span');
        grayTextSpan.className = 'text-gray-400';
        grayTextSpan.textContent = ' in ';
        createdByUser.appendChild(grayTextSpan);
  
        const postTagLink = document.createElement('a');
        postTagLink.href = `/postByCategory.html?categoryName=${encodeURIComponent(post.belongedToCategory)}`;

        const postTagSpan1 = document.createElement('span');
        postTagSpan1.className = 'tag-name';
        postTagSpan1.title = 'Tag Category';
        postTagSpan1.textContent = post.belongedToCategory;
        postTagLink.appendChild(postTagSpan1);
  
        const postTagSpan2 = document.createElement('span');
        postTagSpan2.className = 'tag-name';
        postTagSpan2.title = 'Tag Category';
        postTagSpan2.textContent = '';
        postTagLink.appendChild(postTagSpan2);
    
        createdByUser.appendChild(postTagLink);
        flexElement.appendChild(createdByUser);
    
        const dateTimeElement1 = document.createElement('div');
        dateTimeElement1.className = 'p-0.5 bg-gray-900 rounded-full mx-4';
        dateTimeElement1.title = 'Date Time';
        flexElement.appendChild(dateTimeElement1);
  
      const dateTimeElement2 = document.createElement('div');
      dateTimeElement2.className = 'date-time';
      dateTimeElement2.title = 'Date Time';
      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString('en-US', options);
      dateTimeElement2.textContent = formattedTime;
      flexElement.appendChild(dateTimeElement2);
  
      postElement.appendChild(flexElement);
  
      const postLink = document.createElement('a');
      postLink.href = `index.html?postId=${post.postsId}`;

      const titleElement = document.createElement('div');
      titleElement.className = 'font-semibold text-2xl px-8';
      titleElement.id = 'title';
      titleElement.textContent = post.title;
      postLink.appendChild(titleElement);

      const infoElement = document.createElement('p');
      infoElement.className = 'px-7 py-7 post-detail';
      //infoElement.innerHTML = post.postDetail; 
      infoElement.textContent = extractTextFromHTML(post.postDetail);
      // infoElement.style.maxWidth = "1600px"; 
      // infoElement.style.overflow = "hidden";
      // infoElement.style.textOverflow = "ellipsis";
      // infoElement.style.whiteSpace = "nowrap";

      infoElement.style.marginLeft = "10px";
      postLink.appendChild(infoElement);

      postElement.appendChild(postLink);
  
      const flexItemsElement = document.createElement('div');
      flexItemsElement.className = 'm-5';

      if(post.mediaList.length > 0){
        const imgContainer = document.createElement('div');
        imgContainer.className = 'm-5';

        const mediaList = document.createElement('div');
        mediaList.className = 'media-list';
        post.mediaList.forEach(media => {
          const mediaItem = document.createElement('img');
          mediaItem.src = `data:image/jpeg;base64, ${media}`;
          // mediaItem.style.width = '30%';
          // mediaItem.style.height = 'auto';
          mediaList.appendChild(mediaItem);
        });

        imgContainer.appendChild(mediaList);
        flexItemsElement.appendChild(imgContainer);
      }

      if (post.tagList.length > 0) {
        // const iconTagElement = document.createElement('div');
        // iconTagElement.className = 'icon-tag';
        // const iconTagImg = document.createElement('img');
        // iconTagImg.src = 'img/tag.png';
        // iconTagImg.alt = 'icon-tag';
        // iconTagElement.appendChild(iconTagImg);
        // flexItemsElement.appendChild(iconTagElement);
      
        // post.tagList.forEach(tag => {
        //   const tagLink = document.createElement('a');
        //   tagLink.href = `/pageByTag.html?tagName=${encodeURIComponent(tag)}`;
        //   const tagDiv = document.createElement('div');
        //   tagDiv.className = 'rounded-xl bg-gray-300 text-gray-900 px-2 mr-4';
        //   tagDiv.textContent = tag;
        //   tagLink.appendChild(tagDiv);
        //   flexItemsElement.appendChild(tagLink);
        // });

        post.tagList.forEach(tag => {
          const tagLink = document.createElement('a');
          tagLink.href = `/pageByTag.html?tagName=${encodeURIComponent(tag)}`;
      
          // Create a flex container for the icon and tag
          const flexContainer = document.createElement('div');
          flexContainer.style.display = 'flex';
          flexContainer.style.alignItems = 'center'; // Optional: Align items vertically in the center
      
          // Create the icon element
          const iconTag = document.createElement('i');
          iconTag.className = 'fa-solid fa-tags fa-lg';
          iconTag.style.color = '#000000'; // Set the color as needed
          iconTag.style.marginLeft = '1%';
      
          // Create the tag element
          const tagDiv = document.createElement('div');
          tagDiv.className = 'icon-tag';
          tagDiv.title = 'Tag Name';
          tagDiv.textContent = tag;
      
          // Append the icon and tag to the flex container
          flexContainer.appendChild(iconTag);
          flexContainer.appendChild(tagDiv);
      
          // Append the flex container to the tagLink
          tagLink.appendChild(flexContainer);
      
          // Append the tagLink to the flexItemsElement
          flexItemsElement.appendChild(tagLink);
      });
      }
  
      postElement.appendChild(flexItemsElement);
  
      postContainer.appendChild(postElement);
      });
    }
}

function extractTextFromHTML(html) {
  const tempElement = document.createElement("div");
  tempElement.innerHTML = html;
  return tempElement.textContent || tempElement.innerText || "";
}

const displayPostRequests = () => {
    getPostRequest()
      .then((posts) => {
        displayPosts(posts);
      })
      .catch((error) => {
        console.error(error);
        // Handle the error as needed
      });
};

displayPostRequests();