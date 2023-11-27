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
    $(document).ready(function () {
      const container = $("#post .col-span-6");

      // DISPLAY LIST OF POSTS
      posts.forEach(function (post) {
        const postCreatedTime = new Date(post.createdTime);
        const formattedTime = postCreatedTime.toLocaleString("en-US", options);

        let tagsHTML = "";
        if (Array.isArray(post.tagList) && post.tagList.length > 0) {
          const tags = post.tagList.map((tag) => {
            const tagLink = document.createElement("a");
            tagLink.href = `/pageByTag.html?tagName=${encodeURIComponent(tag)}`;
            tagLink.className = "tag";
            tagLink.textContent = tag;
            tagLink.textContent = `#${tag}`;
            return tagLink.outerHTML;
          });
          tagsHTML = tags.join(" ");
        }

        //award list
        //   const awardList = Object.entries(post.awardTypeCount).map(([awardType, count]) => `
        //   <div class="award" title="${awardType}">
        //     <i class="fa-solid fa-award" style="color: #ddd60e;"></i>
        //     <span class="award-count">${count}</span>
        //   </div>
        // `).join('');
        const awardList = Object.entries(post.awardTypeCount)
          .map(
            ([awardType, count]) => `
        <div class="award" title="${awardType}">
          <i class="fa-solid fa-award" style="color: #ddd60e;"></i>
          <span class="award-count">${count}</span>
        </div>`
          )
          .join("");

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

        const categoryLink = document.createElement("a");
        categoryLink.href = `postByCategory.html?categoryName=${post.belongedToCategory}`;

        const postHTML = `
          <div class="main-blog">
            <div class="wapper-title">
            
              <div class="column">

                <div class="inner-column">
                  <div class="container-user" title="Created By User">
                    <div class="by-user">
                      <i class="fa-solid fa-user fa-sm" style="color: #000000;"></i>
                    </div>
                    <div class="by-user">
                      ${post.createdByUser}
                    </div>
                  </div>
                </div>

                <div class="inner-column">
                  <div class="container-datetime" title="Created Time">
                    <div class="date-time">
                      <i class="fa-regular fa-calendar-days" style="color: #000000;"></i>
                    </div>
                    <div class="date-time" id="postDate">
                      ${formattedTime}
                    </div>
                  </div>
                </div>

                <div class="inner-column">
                  <div class="container-tag" title="Category">
                      <div class="tag-category">
                          <h2 >
                              Category: 
                          </h2>
                      </div>
                      <div class="container-tag" title="Category">
                          <div class="tag-category">
                            <i class="fa-solid fa-hashtag" style="color: #ff0000;"></i>
                          </div>
                          <div class="tag-category" id="postCategory">
                            <a href="${categoryLink.href}">${
          post.belongedToCategory
        }</a>
                          </div>
                      </div> 
                    </div> 
                </div>

                <div class="inner-column">
                  <div class="container-awards" title="Awards">
                    <div class="award-list">
                      ${awardList}
                    </div>
                  </div>
                </div>

              </div>

              <div class="container-post-img">

                <div class="post-title-content" title="Post Title">

                  <div class="column-title">
                    <div class="postTitle">
                      <h2><a href="${postLink.href}">${post.title}</a></h2>
                    </div>
                  </div>

                  <div class="column-content">
                    <div class="postContent">
                      <h2><a href="${postLink.href}">${post.postDetail}</a></h2>
                    </div>
                  </div>

                </div>

                ${
                  post.mediaList.length > 0
                    ? `
                  <div class="container-img" title="Cover Image">
                    <img src="data:image/jpeg;base64, ${post.mediaList}" alt="Post Image">
                  </div>
                `
                    : ""
                }
              </div>

              <div class="column">
              
                <div class="inner-column">
                  <div class="container-vote" title="Total Like">
                    <div class="number-vote">
                      <i class="fa-regular fa-thumbs-up" style="color: #000000;" id="likeIcon"></i>
                    </div>
                    <div class="number-vote" id="postVote">
                      ${post.vote1Count}
                    </div>
                  </div>
                </div>

                <div class="inner-column">
                  <div class="container-read" title="Reading Time">
                    <div class="number-read">
                      <i class="fa-regular fa-clock" style="color: #000000;"></i>
                    </div>
                    <div class="number-read" id="readingTime">
                      ${post.readingTime}
                    </div>
                  </div>
                </div>

                <div class="inner-column">
                  <div class="container-tag" title="Tag">
                      <div class="container-tag-hover">
                        <div class="tag-category" id="postTag">
                          ${containerReadHTML}
                        </div>
                      </div>
                  </div> 
                </div> 

                </div>
              </div>
            </div>
          </div>
        `;

        container.append(postHTML);

        const infoElement = container.find(".postContent h2:last-child");
        infoElement.css({
          maxWidth: "1000px",
          overflow: "hidden",
          textOverflow: "ellipsis",
          whiteSpace: "nowrap",
        });
      });
    });
  }
}

const displayAllPosts = async () => {
  try {
    const posts = await getAwardedPosts(3, "approved,rejected");
    displayPosts(posts);
    // alert("hell22222222222222o");
  } catch (error) {
    console.error("Error displaying posts:", error);
    // Handle the error as needed
  }
};

displayAllPosts();
