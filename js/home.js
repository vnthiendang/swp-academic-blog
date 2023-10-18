import { getAllApprovedPosts } from "../js/Services/post.service.js";
import { getAllCategory } from "./Services/category.service.js";

const options = {
  month: 'short', // Two-digit month (e.g., 01)
  day: '2-digit', // Two-digit day (e.g., 18)
  hour: '2-digit', // Two-digit hour (e.g., 14)
};

  function displayPosts(posts) {
    const postContainer = document.getElementById('post');
  
    posts.forEach((post) => {
      const postElement = document.createElement('div');
      postElement.className = 'col-span-5';
  
      const flexElement = document.createElement('div');
      flexElement.className = 'flex w-full px-8 py-4 items-center';
  
      const avatarImage = document.createElement('div');
      avatarImage.className = 'flex';
      const avatarImg = document.createElement('img');
      avatarImg.src = 'img/Img For User/User1.jpg';
      avatarImg.alt = 'avatar';
      avatarImg.className = 'rounded-full w-8';
      avatarImage.appendChild(avatarImg);
      flexElement.appendChild(avatarImage);
  
      const createdByUser = document.createElement('div');
      createdByUser.className = 'ml-4';
      createdByUser.textContent = post.createdByUser;
      const grayTextSpan = document.createElement('span');
      grayTextSpan.className = 'text-gray-400';
      grayTextSpan.textContent = ' in ';
      createdByUser.appendChild(grayTextSpan);
  
      const postTagLink = document.createElement('a');
      postTagLink.href = '/BLOG_UPDATE_VER2.3 - Create 4 Page/Page SE SA AI BS/html/pageBusiness.html';
  
      const postTagSpan1 = document.createElement('span');
      postTagSpan1.className = 'tag-name';
      postTagSpan1.textContent = post.belongedToCategory;
      postTagLink.appendChild(postTagSpan1);
  
      const commaSpan = document.createElement('span');
      commaSpan.className = 'tag-name';
      commaSpan.textContent = ' , ';
      postTagLink.appendChild(commaSpan);
  
      const postTagSpan2 = document.createElement('span');
      postTagSpan2.className = 'tag-name';
      postTagSpan2.textContent = '';
      postTagLink.appendChild(postTagSpan2);
  
      const commaSpan2 = document.createElement('span');
      commaSpan2.className = 'tag-name';
      commaSpan2.textContent = ' , ';
      postTagLink.appendChild(commaSpan2);
  
      const postTagSpan3 = document.createElement('span');
      postTagSpan3.className = 'tag-name';
      postTagSpan3.textContent = '';
      postTagLink.appendChild(postTagSpan3);
  
      createdByUser.appendChild(postTagLink);
      flexElement.appendChild(createdByUser);
  
      const dateTimeElement1 = document.createElement('div');
      dateTimeElement1.className = 'p-0.5 bg-gray-900 rounded-full mx-4';
      flexElement.appendChild(dateTimeElement1);
  
      const dateTimeElement2 = document.createElement('div');
      dateTimeElement2.className = 'date-time';
      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString('en-US', options);
      dateTimeElement2.textContent = formattedTime;
      flexElement.appendChild(dateTimeElement2);
  
      const dateTimeElement3 = document.createElement('div');
      dateTimeElement3.className = 'p-0.5 bg-gray-900 rounded-full mx-4';
      flexElement.appendChild(dateTimeElement3);
  
      const memberOnlyElement = document.createElement('div');
      memberOnlyElement.textContent = 'Member only';
      flexElement.appendChild(memberOnlyElement);
  
      postElement.appendChild(flexElement);
  
      const postLink = document.createElement('a');
      postLink.href = 'blog1.html';
  
      const titleElement = document.createElement('div');
      titleElement.className = 'font-semibold text-2xl px-8';
      titleElement.id = 'title';
      titleElement.textContent = post.title;
      postLink.appendChild(titleElement);
  
      const infoElement = document.createElement('p');
      infoElement.className = 'px-8 py-4 post-detail';
      infoElement.textContent = post.postDetail;
      postLink.appendChild(infoElement);
  
      postElement.appendChild(postLink);
  
      const flexItemsElement = document.createElement('div');
      flexItemsElement.className = 'flex items-center justify-between px-8';
  
      const iconTagElement = document.createElement('div');
      iconTagElement.className = 'icon-tag';
      const iconTagImg = document.createElement('img');
      iconTagImg.src = 'img/tag.png';
      iconTagImg.alt = 'icon-tag';
      iconTagElement.appendChild(iconTagImg);
      flexItemsElement.appendChild(iconTagElement);
  
      const tagLink1 = document.createElement('a');
      tagLink1.href = '/BLOG_UPDATE_VER2.3 - Create 4 Page/Page SE SA AI BS/html/pageTechnoloy.html';
      const tagDiv1 = document.createElement('div');
      tagDiv1.className = 'rounded-xl bg-gray-300 text-gray-900 px-2 mr-4';  

      tagDiv1.textContent = post.postTag ?? 'tag';
      tagLink1.appendChild(tagDiv1);
      flexItemsElement.appendChild(tagLink1);
  
      const tagLink2 = document.createElement('a');
      tagLink2.href = '/BLOG_UPDATE_VER2.3 - Create 4 Page/Page SE SA AI BS/html/pageBusiness.html';
      const tagDiv2 = document.createElement('div');
      tagDiv2.className = 'rounded-xl bg-gray-300 text-gray-900 px-2 mr-4';
      tagDiv2.textContent = post.postTag ?? 'tag';
      tagLink2.appendChild(tagDiv2);
      flexItemsElement.appendChild(tagLink2);
  
      const tagLink3 = document.createElement('a');
      tagLink3.href = '/BLOG_UPDATE_VER2.3 - Create 4 Page/Page SE SA AI BS/html/pageDigitalTransformation.html';
      const tagDiv3 = document.createElement('div');
      tagDiv3.className = 'rounded-xl bg-gray-300 text-gray-900 px-2 mr-4';
      tagDiv3.textContent = post.postTag ?? 'tag';
      tagLink3.appendChild(tagDiv3);
      flexItemsElement.appendChild(tagLink3);
  
      postElement.appendChild(flexItemsElement);
  
      const imgContainer = document.createElement('div');
      imgContainer.className = 'm-5';
  
      const imgLink = document.createElement('a');
      imgLink.href = 'blog1.html';
  
      const imgElement = document.createElement('img');
      imgElement.src = 'img/Business/8.webp';
      imgElement.alt = 'img-post 4';
      imgElement.className = 'w-full h-full';
  
      imgLink.appendChild(imgElement);
      imgContainer.appendChild(imgLink);
  
      postElement.appendChild(imgContainer);
  
      postContainer.appendChild(postElement);
    });
  }

// Call the API function and pass the response to displayPosts
getAllApprovedPosts()
  .then((posts) => {
    displayPosts(posts);
  });

  const displayCategories = (categories) => {
    const categoryList = document.querySelector('.absolute.hidden.text-gray-700.pt-1.group-hover\\:block');
  
    categories.forEach(category => {
      const listItem = document.createElement('li');
      const link = document.createElement('a');
      link.className = 'rounded-b bg-black hover:bg-gray-400 py-5 px-5 block whitespace-no-wrap text-white';
      link.href = category.url ?? ''; 
      link.textContent = category.content;
  
      listItem.appendChild(link);
      categoryList.appendChild(listItem);
    });
  };

  getAllCategory()
  .then((cates) => {
    displayCategories(cates);
  });