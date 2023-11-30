import { userInfo } from "./Services/auth.service.js";
import { getAwardedPosts } from "./Services/post.service.js";

const options = {
  month: "short",
  day: "2-digit",
  hour: "2-digit",
};

// function displayPosts(posts) {
//   const postContainer = document.querySelector("#post .col-span-6");
//   postContainer.innerHTML = "";

//   if (!Array.isArray(posts)) {
//     console.error("Invalid posts data. Expected an array.");
//     return;
//   }

//   if (posts.length === 0) {
//     const noResultsElement = document.createElement("div");
//     noResultsElement.className =
//       "text-center text-4xl font-bold text-gray-500 dark:text-gray-400";
//     noResultsElement.textContent =
//       "No results related to your search. Please use other keywords.";
//     postContainer.appendChild(noResultsElement);
//   } else {
//     for (let i = 0; i < posts.length; i++) {
//       const post = posts[i];

//       if (!post || typeof post !== "object") {
//         console.error(`Invalid post data at index ${i}. Skipping.`);
//         continue;
//       }

//       const postElement = document.createElement("div");

//       postElement.className = "post-container";
//       postElement.style.marginBottom = "25px";

//       // postElement.className = "post-border";
//       // postElement.style.border = "1px solid #000";
//       postContainer.appendChild(postElement);
//       // Bodrer For Post

//       const flexElement = document.createElement("div");
//       flexElement.className = "flex w-full px-8 py-4 items-center";

//       const avatarImage = document.createElement("div");
//       avatarImage.className = "flex";
//       const avatarImg = document.createElement("img");
//       avatarImg.src = "img/Img For User/User1.jpg";
//       avatarImg.alt = "avatar";
//       avatarImg.className = "rounded-full w-8";
//       avatarImage.appendChild(avatarImg);
//       flexElement.appendChild(avatarImage);

//       const createdByUser = document.createElement("div");
//       createdByUser.className = "ml-4";
//       createdByUser.textContent = post.createdByUser;
//       const grayTextSpan = document.createElement("span");
//       grayTextSpan.className = "text-gray-400";
//       grayTextSpan.textContent = " in ";
//       createdByUser.appendChild(grayTextSpan);

//       const categoryLink = document.createElement("a");
//       categoryLink.href = `postByCategory.html?categoryName=${post.belongedToCategory}`;

//       const postTagSpan = document.createElement("span");
//       postTagSpan.className = "tag-name";
//       postTagSpan.textContent = post.belongedToCategory;
//       categoryLink.appendChild(postTagSpan);

//       createdByUser.appendChild(categoryLink);
//       flexElement.appendChild(createdByUser);

//       const dateTimeElement1 = document.createElement("div");
//       dateTimeElement1.className = "p-0.5 bg-gray-900 rounded-full mx-4";
//       flexElement.appendChild(dateTimeElement1);

//       const dateTimeElement2 = document.createElement("div");
//       dateTimeElement2.className = "date-time";

//       const postCreatedTime = new Date(post.createdTime);
//       const formattedTime = postCreatedTime.toLocaleString("en-US", options);
//       dateTimeElement2.textContent = formattedTime;
//       flexElement.appendChild(dateTimeElement2);

//       if (post.awardList != null && post.awardList.length > 0) {
//         const elm4 = document.createElement("div");
//         elm4.className = "p-0.5 bg-gray-900 rounded-full mx-4";
//         flexElement.appendChild(elm4);

//         const awardIcon = document.createElement("i");
//         awardIcon.className = "fas fa-crown";
//         awardIcon.style = "color: #c45d08;";
//         flexElement.appendChild(awardIcon);

//         const awardList = document.createElement("div");
//         awardList.className =
//           "award-list rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
//         const items = document.createElement("span");
//         items.textContent = post.awardList.slice(0, 1)[0];
//         awardList.appendChild(items);
//         flexElement.appendChild(awardList);
//       }

//       if (post.voteList != null && post.voteList.length > 0) {
//         const elm5 = document.createElement("div");
//         elm5.className = "p-0.5 bg-gray-900 rounded-full mx-4";
//         flexElement.appendChild(elm5);

//         const voteIcon = document.createElement("i");
//         voteIcon.className = "fa-regular fa-heart fa-beat-fade fa-xl";
//         flexElement.appendChild(voteIcon);

//         const voteList = document.createElement("div");
//         voteList.className =
//           "vote-list rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
//         voteList.textContent = post.vote1Count;
//         // post.voteList.forEach((vote) => {
//         //   const item = document.createElement("span");
//         //   item.textContent = vote + "  ";
//         //   voteList.appendChild(item);
//         // });
//         flexElement.appendChild(voteList);
//       }

//       postElement.appendChild(flexElement);

//       const postLink = document.createElement("a");
//       postLink.href = `blogDetail.html?belongedToPostID=${post.postsId}`;

//       const titleElement = document.createElement("div");
//       titleElement.className = "font-semibold text-2xl px-8";
//       titleElement.id = "title";
//       titleElement.textContent = post.title;
//       postLink.appendChild(titleElement);

//       const infoElement = document.createElement("p");
//       infoElement.className = "px-6 py-7 post-detail";
//       // infoElement.innerHTML = post.postDetail;
//       // infoElement.textContent = extractTextFromHTML(post.postDetail);
//       infoElement.style.maxWidth = "1200px";
//       infoElement.style.overflow = "hidden";
//       infoElement.style.textOverflow = "ellipsis";
//       infoElement.style.whiteSpace = "nowrap";
//       postLink.appendChild(infoElement);

//       postElement.appendChild(postLink);

//       const flexItemsElement = document.createElement("div");
//       flexItemsElement.className = "flex items-center px-8 mb-1";

//       if (post.mediaList.length > 0) {
//         const imgContainer = document.createElement("div");
//         imgContainer.className = "m-5";

//         const mediaList = document.createElement("div");
//         mediaList.className = "media-list";

//         post.mediaList.forEach((media) => {
//           const mediaItem = document.createElement("img");
//           mediaItem.src = `data:image/jpeg;base64, ${media}`;
//           mediaItem.style.width = "50%";
//           mediaItem.style.height = "auto";
//           mediaList.appendChild(mediaItem);
//         });

//         imgContainer.appendChild(mediaList);
//         flexItemsElement.appendChild(imgContainer);
//       }

//       if (post.tagList.length > 0) {
//         const iconTagElement = document.createElement("div");
//         iconTagElement.className = "icon-tag";
//         const iconTagImg = document.createElement("img");
//         iconTagImg.src = "img/tag.png";
//         iconTagImg.alt = "icon-tag";
//         iconTagElement.appendChild(iconTagImg);
//         flexItemsElement.appendChild(iconTagElement);

//         post.tagList.forEach((tag) => {
//           const tagLink = document.createElement("a");
//           tagLink.href = `/pageByTag.html?tagName=${encodeURIComponent(tag)}`;
//           const tagDiv = document.createElement("div");
//           tagDiv.className = "rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
//           tagDiv.textContent = tag;
//           tagLink.appendChild(tagDiv);
//           flexItemsElement.appendChild(tagLink);
//         });
//       }

//       postElement.appendChild(flexItemsElement);

//       postContainer.appendChild(postElement);

//       // const hr = document.createElement("hr");
//       // hr.style.border = "none"; // Remove the default border
//       // hr.style.backgroundColor = "red"; // Set the background color to create a solid line
//       // hr.style.height = "10px";
//       // hr.style.marginBottom="30px" // Set the thickness of the line to 2 pixels/ Increase the line height to 4 pixels
//       // postContainer.appendChild(hr);
//     }
//   }
// }

// DISPLAY LIST APPROVED POSTS
const displayPosts = async (posts) => {
  const postContainer = document.querySelector("#post .col-span-6");
  postContainer.innerHTML = "";

  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;

  if (userRole === 'Teacher'){
    if (posts.length === 0) {
      const noResultsElement = document.createElement("div");
      noResultsElement.className =
        "text-center text-4xl font-bold text-gray-500 dark:text-gray-400";
      noResultsElement.textContent =
        "No posts available.";
      postContainer.appendChild(noResultsElement);
    } else {
      for (let i = 0; i < posts.length; i++) {
        const post = posts[i];
  
        if (!post || typeof post !== "object") {
          console.error(`Invalid post data at index ${i}. Skipping.`);
          continue;
        }
  
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
  
        const categoryLink = document.createElement("a");
        categoryLink.href = `postByCategory.html?categoryName=${post.belongedToCategory}`;
  
        const postTagSpan = document.createElement("span");
        postTagSpan.className = "tag-name";
        postTagSpan.textContent = post.belongedToCategory;
        categoryLink.appendChild(postTagSpan);
  
        createdByUser.appendChild(categoryLink);
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
  
        if (post.awardList != null && post.awardList.length > 0) {
          const elm4 = document.createElement("div");
          elm4.className = "p-0.5 bg-gray-900 rounded-full mx-4";
          flexElement.appendChild(elm4);
  
          const awardIcon = document.createElement("i");
          awardIcon.className = "fas fa-crown";
          awardIcon.style = "color: #c45d08;";
          flexElement.appendChild(awardIcon);
  
          const awardList = document.createElement("div");
          awardList.className =
            "award-list rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
          const items = document.createElement("span");
          items.textContent = post.awardList.slice(0, 1)[0];
          awardList.appendChild(items);
          flexElement.appendChild(awardList);
        }
  
        if (post.voteList != null && post.voteList.length > 0) {
          const elm5 = document.createElement("div");
          elm5.className = "p-0.5 bg-gray-900 rounded-full mx-4";
          flexElement.appendChild(elm5);
  
          const voteIcon = document.createElement("i");
          voteIcon.className = "fa-regular fa-heart fa-beat-fade fa-xl";
          flexElement.appendChild(voteIcon);
  
          const elm6 = document.createElement("div");
          elm6.className = "p-0.5 bg-gray-900 rounded-full mx-4";
          flexElement.appendChild(elm6);
  
          const containerReadHTML = tagsHTML
            ? `
            <div class="container-read">
              <div class="tag-category">
                <h2>Tag: </h2>
              </div>
              <div class="tag-list" id="tagList">
                ${tagsHTML}
              </div>
            </div>
          `
            : "";
  
          const postLink = document.createElement("a");
          postLink.href = `blogDetail.html?belongedToPostID=${post.postsId}`;
  
        const titleElement = document.createElement("div");
        titleElement.className = "font-semibold text-2xl px-8";
        titleElement.id = "title";
        titleElement.textContent = post.title;
        postLink.appendChild(titleElement);
  
        const infoElement = document.createElement("p");
        infoElement.className = "px-6 py-7 post-detail";
        // infoElement.innerHTML = post.postDetail;
        // infoElement.textContent = extractTextFromHTML(post.postDetail);
        infoElement.style.maxWidth = "1200px";
        infoElement.style.overflow = "hidden";
        infoElement.style.textOverflow = "ellipsis";
        infoElement.style.whiteSpace = "nowrap";
        postLink.appendChild(infoElement);
  
        postElement.appendChild(postLink);
  
        const flexItemsElement = document.createElement("div");
        flexItemsElement.className = "flex items-center px-8 mb-1";
  
        if (post.mediaList.length > 0) {
          const imgContainer = document.createElement("div");
          imgContainer.className = "m-5";
  
          const mediaList = document.createElement("div");
          mediaList.className = "media-list";
  
          post.mediaList.forEach((media) => {
            const mediaItem = document.createElement("img");
            mediaItem.src = `data:image/jpeg;base64, ${media}`;
            mediaItem.style.width = "50%";
            mediaItem.style.height = "auto";
            mediaList.appendChild(mediaItem);
          });
  
          imgContainer.appendChild(mediaList);
          flexItemsElement.appendChild(imgContainer);
        }
  
        if (post.tagList.length > 0) {
          // const iconTagElement = document.createElement("div");
          // iconTagElement.className = "icon-tag";
          // const iconTagImg = document.createElement("img");
          // iconTagImg.alt = "icon-tag";
          // iconTagElement.appendChild(iconTagImg);
          // flexItemsElement.appendChild(iconTagElement);
  
          post.tagList.forEach((tag) => {
            const tagLink = document.createElement("a");
            tagLink.href = `/pageByTag.html?tagName=${encodeURIComponent(tag)}`;
            const tagDiv = document.createElement("div");
            tagDiv.className = "rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
            tagDiv.textContent = tag;
            tagLink.appendChild(tagDiv);
            flexItemsElement.appendChild(tagLink);
          });
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
    }}
  }else{
    postContainer.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }

}

const displayAllPosts = async () => {
  getAwardedPosts(3, "approved,rejected")
  .then((posts) => {
    displayPosts(posts);
  })
  .catch((error) => {
    console.error(error);
    // Handle the error as needed
  });
};

displayAllPosts();
