import {
  getAllApprovedPosts,
  searchedPosts,
} from "../js/Services/post.service.js";
import { getAllCategory } from "./Services/category.service.js";

const options = {
  month: "short", 
  day: "2-digit",
  hour: "2-digit", 
};

// DISPLAY LIST APPROVED POSTS
function displayPosts(posts) {
  const postContainer = document.querySelector("#post .col-span-6");
  postContainer.innerHTML = "";

  if (posts.length === 0) {
    const noResultsElement = document.createElement("div");
    noResultsElement.className =
      "text-center text-4xl font-bold text-gray-500 dark:text-gray-400";
    noResultsElement.textContent =
      "No results related to your search. Please use other keywords.";
    postContainer.appendChild(noResultsElement);
  } else {
    for (var i = 0; i < posts.length; i++) {
      var post = posts[i];

      const postElement = document.createElement("div");

      postElement.className = "post-container";
      postElement.style.marginBottom = "25px";

      // postElement.className = "post-border";
      // postElement.style.border = "1px solid #000";
      postContainer.appendChild(postElement);
      // Bodrer For Post

      const flexElement = document.createElement("div");
      flexElement.className = "flex w-full px-8 py-4 items-center";

      const avatarImage = document.createElement("div");
      avatarImage.className = "flex";
      const avatarImg = document.createElement("img");
      avatarImg.src = "img/Img For User/User1.jpg";
      avatarImg.alt = "avatar";
      avatarImg.className = "rounded-full w-8";
      avatarImage.appendChild(avatarImg);
      flexElement.appendChild(avatarImage);

      const createdByUser = document.createElement("div");
      createdByUser.className = "ml-4";
      createdByUser.textContent = post.createdByUser;
      const grayTextSpan = document.createElement("span");
      grayTextSpan.className = "text-gray-400";
      grayTextSpan.textContent = " in ";
      createdByUser.appendChild(grayTextSpan);

      const postTagLink = document.createElement("a");
      postTagLink.href = "#";

      const postTagSpan = document.createElement("span");
      postTagSpan.className = "tag-name";
      postTagSpan.textContent = post.belongedToCategory;
      postTagLink.appendChild(postTagSpan);

      createdByUser.appendChild(postTagLink);
      flexElement.appendChild(createdByUser);

      const dateTimeElement1 = document.createElement("div");
      dateTimeElement1.className = "p-0.5 bg-gray-900 rounded-full mx-4";
      flexElement.appendChild(dateTimeElement1);

      const dateTimeElement2 = document.createElement("div");
      dateTimeElement2.className = "date-time";
      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString("en-US", options);
      dateTimeElement2.textContent = formattedTime;
      flexElement.appendChild(dateTimeElement2);

      const dateTimeElement3 = document.createElement("div");
      dateTimeElement3.className = "p-0.5 bg-gray-900 rounded-full mx-4";
      flexElement.appendChild(dateTimeElement3);

      const memberOnlyElement = document.createElement("div");
      memberOnlyElement.textContent = "Member only";
      flexElement.appendChild(memberOnlyElement);

      const dateTimeElement4 = document.createElement("div");
      dateTimeElement4.className = "p-0.5 bg-gray-900 rounded-full mx-4";
      flexElement.appendChild(dateTimeElement4);

      if(post.awardList != null && post.awardList.length > 0){
        const elm4 = document.createElement("div");
        elm4.className = "p-0.5 bg-gray-900 rounded-full mx-4";
        flexElement.appendChild(elm4);

        const awardIcon = document.createElement("i");
        awardIcon.className = "fas fa-crown";
        awardIcon.style = "color: #c45d08;";
        flexElement.appendChild(awardIcon);
  
        const awardList = document.createElement("div");
        awardList.className = "award-list rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
        const items = document.createElement("span");
        items.textContent = post.awardList.slice(0, 1)[0];
        awardList.appendChild(items);
        flexElement.appendChild(awardList);
      }

      if(post.voteList != null && post.voteList.length > 0){
        const voteIcon = document.createElement("i");
        voteIcon.className =
        "fa-regular fa-heart fa-beat-fade fa-xl";
        flexElement.appendChild(voteIcon); 

        const voteList = document.createElement("div");
        voteList.className = "vote-list rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
        voteList.textContent = post.vote1Count;
      // post.voteList.forEach((vote) => {
      //   const item = document.createElement("span");
      //   item.textContent = vote + "  ";
      //   voteList.appendChild(item);
      // });
        flexElement.appendChild(voteList);
      }

      postElement.appendChild(flexElement);

      const postLink = document.createElement("a");
      postLink.href = `blogDetail.html?belongedToPostID=${post.postsId}`;

      const titleElement = document.createElement("div");
      titleElement.className = "font-semibold text-2xl px-8";
      titleElement.id = "title";
      titleElement.textContent = post.title;
      postLink.appendChild(titleElement);

      const infoElement = document.createElement("p");
      infoElement.className = "px-6 py-7 post-detail";
      //infoElement.innerHTML = post.postDetail;
      infoElement.textContent = extractTextFromHTML(post.postDetail);
      infoElement.style.maxWidth = "1200px"; 
      infoElement.style.overflow = "hidden";
      infoElement.style.textOverflow = "ellipsis";
      infoElement.style.whiteSpace = "nowrap";
      postLink.appendChild(infoElement);

      postElement.appendChild(postLink);

      const flexItemsElement = document.createElement("div");
      flexItemsElement.className = "flex items-center px-8 mb-1";

      if(post.mediaList.length > 0){
        const imgContainer = document.createElement('div');
        imgContainer.className = 'm-5';

        const mediaList = document.createElement('div');
        mediaList.className = 'media-list';
        
        post.mediaList.forEach(media => {
          const mediaItem = document.createElement('img');
          mediaItem.src = `data:image/jpeg;base64, ${media}`;
          mediaItem.style.width = '50%'; 
          mediaItem.style.height = 'auto';
          mediaList.appendChild(mediaItem);
        });

        imgContainer.appendChild(mediaList);
        flexItemsElement.appendChild(imgContainer);
      }

      if(post.tagList.length > 0){
        const iconTagElement = document.createElement("div");
        iconTagElement.className = "icon-tag";
        const iconTagImg = document.createElement("img");
        iconTagImg.src = "img/tag.png";
        iconTagImg.alt = "icon-tag";
        iconTagElement.appendChild(iconTagImg);
        flexItemsElement.appendChild(iconTagElement);

        const tagLink1 = document.createElement("a");
        tagLink1.href = "/pageByCategory.html?tagId";

        const postTagList = document.createElement("div");
        postTagList.className =
          "post-tag-list rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
        post.tagList.forEach((tag) => {
          const tagItem = document.createElement("span");
          tagItem.textContent = tag;
          postTagList.appendChild(tagItem);
        });

        tagLink1.appendChild(postTagList);
        flexItemsElement.appendChild(tagLink1);
      }

      postElement.appendChild(flexItemsElement);

      postContainer.appendChild(postElement);

      // const hr = document.createElement("hr");
      // hr.style.border = "none"; // Remove the default border
      // hr.style.backgroundColor = "red"; // Set the background color to create a solid line
      // hr.style.height = "10px";
      // hr.style.marginBottom="30px" // Set the thickness of the line to 2 pixels/ Increase the line height to 4 pixels
      // postContainer.appendChild(hr);
    }
  }
}

function extractTextFromHTML(html) {
  const tempElement = document.createElement("div");
  tempElement.innerHTML = html;
  return tempElement.textContent || tempElement.innerText || "";
}

// Function to display all approved posts
const displayAllPosts = () => {
  getAllApprovedPosts()
    .then((posts) => {
      displayPosts(posts);
    })
    .catch((error) => {
      console.error(error);
      // Handle the error as needed
    });
};

//GET LIST CATEGORIES
const displayCategories = (categories) => {
  const categoryList = document.querySelector(
    ".absolute.hidden.text-gray-700.pt-1.group-hover\\:block"
  );

  categories.forEach((category) => {
    const listItem = document.createElement("li");
    const link = document.createElement("a");
    link.className =
      "rounded-b bg-black hover:bg-gray-400 py-5 px-5 block whitespace-no-wrap text-white";
    link.href = `postByCategory.html?categoryId=${category.id}`;
    link.textContent = category.content;

    listItem.appendChild(link);
    categoryList.appendChild(listItem);
  });
};

getAllCategory().then((cates) => {
  displayCategories(cates);
});

// Call the displayAllPosts function when entering the page
displayAllPosts();

// SEARCH POSTS
const searchForm = document.querySelector("#search-form");
searchForm.addEventListener("submit", (event) => {
  event.preventDefault();

  const searchInput = document.querySelector("#simple-search");
  const searchTerm = searchInput.value;

  if (searchTerm) {
    searchedPosts(searchTerm)
      .then((posts) => {
        displayPosts(posts);
      })
      .catch((error) => {
        console.error(error);
        // Handle the error as needed
      });
  } else {
    displayAllPosts();
  }

  searchInput.value = ""; // Clear the search input
});
