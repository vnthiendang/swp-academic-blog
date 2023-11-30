import { getPostRequest } from "./Services/request.service.js";
import { userInfo } from "./Services/auth.service.js";



const options = {
    month: 'short', 
    day: '2-digit',
    hour: '2-digit',
};

const showHeaderForTeacher = async() => {
  try {
      const usersInfo = await userInfo();
      const userRole = usersInfo.role_id;

      if (userRole === 'Teacher') {
          // Display the form
          document.getElementById('teacherPage').style.display = 'block';
          document.getElementById('teacherPage2').style.display = 'block';
      } else {
          // Hide the form
          document.getElementById('teacherPage').style.display = 'none';
          document.getElementById('teacherPage2').style.display = 'none';
      }
  } catch (error) {}
};

showHeaderForTeacher();

// DISPLAY LIST APPROVED POSTS
const displayPosts = async (posts) => {
  const postContainer = document.querySelector(".main-blog");
  postContainer.innerHTML = "";

  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;
  if (userRole === 'Teacher'){
    if (posts.length === 0) {
      const noResultsElement = document.createElement("div");
      noResultsElement.className =
          "text-center text-4xl font-bold text-gray-500 dark:text-gray-400";
      noResultsElement.textContent =
          "No post requests available.";
      postContainer.appendChild(noResultsElement);
  } else {
      $(document).ready(function() {
                  const container = $(".main-blog");

                  // DISPLAY LIST OF POSTS
                  posts.forEach(function(post) {
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

                              const containerReadHTML = tagsHTML ?
                                  `
                                    <div class="container-read">
                                      <div class="tag-category" id="tag-category">
                                        <h2>Tag: </h2>
                                      </div>
                                      <div class="tag-list" id="tagList">
                                        ${tagsHTML}
                                      </div>
                                    </div>
                                  ` :
                                  "";

                              const postLink = document.createElement("a");
                              postLink.href = `index.html?postId=${post.postsId}`;

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
                      <h2><a href="${postLink.href}">${
                        post.postDetail
                      }</a></h2>
                    </div>
                  </div>

                </div>

                ${
                  post.mediaList.length > 0
                    ? `
                  <div class="container-img" title="Cover Image">
                    <img src="data:image/jpeg;base64, ${post.mediaList[0]}" alt="Post Image">
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
    
  }else{
    postContainer.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }

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