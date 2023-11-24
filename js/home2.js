import {
    getAllApprovedPosts,
    searchedPosts,
  } from "../js/Services/post.service.js";
  import { userInfo } from "./Services/auth.service.js";
  import { getAllCategory } from "./Services/category.service.js";
  
  const options = {
    month: "short", 
    day: "2-digit",
    hour: "2-digit", 
  };
  
  const showHeaderForTeacher = async () => {
    try {
      const usersInfo = await userInfo();
      const userRole = usersInfo.role_id;
  
      if (userRole === 'Teacher') {
        // Display the form
        document.getElementById('teacherPage').style.display = 'block';
        document.getElementById('giveAwardPage').style.display = 'block';
      } else {
        // Hide the form
        document.getElementById('teacherPage').style.display = 'none';
        document.getElementById('giveAwardPage').style.display = 'none';
      }
    } catch (error) {
    }
  };
  
  showHeaderForTeacher();
  
  
  // DISPLAY LIST APPROVED POSTS
  function displayPosts(posts) {
    const postContainer = document.querySelector(".main-blog");
    postContainer.innerHTML = "";
  
    if (posts.length === 0) {
      const noResultsElement = document.createElement("div");
      noResultsElement.className =
        "text-center text-4xl font-bold text-gray-500 dark:text-gray-400";
      noResultsElement.textContent =
        "No results related to your search. Please use other keywords.";
      postContainer.appendChild(noResultsElement);
    } else {
      $(document).ready(function() {
        const container = $('.col-span-3');
        
        // DISPLAY LIST OF POSTS
        posts.forEach(function(post) {
          const postCreatedTime = new Date(post.createdTime);
          const formattedTime = postCreatedTime.toLocaleString("en-US", options);

          let tagsHTML = '';
          if (typeof post.tagList === 'string') {
            const tags = post.tagList.split(',').map(tag => tag.trim());
      
            tagsHTML = tags.map(tag => `<span class="tag">${tag}</span>`).join(', ');
          }

          const containerReadHTML = post.tagList.length > 0 ? `
          <div class="container-read">
            <div class="tag-list">
              <i class="fa-solid fa-tags fa-lg" style="color: #ff0000;"></i>
            </div>
            <div class="tag-list" id="tagList">
              ${tagsHTML}
            </div>
          </div>
        ` : '';

        const postLink = document.createElement("a");
        postLink.href = `blogDetail.html?belongedToPostID=${post.postsId}`;

          const postHTML = `
            <div class="main-blog">
              <div class="wapper-title">
                <div class="column">
                  <div class="inner-column">
                    <div class="container-user">
                      <div class="by-user-logo">
                        <img src="img/Img For User/User1.jpg" alt="User Logo">
                      </div>
                    </div>
                  </div>
                  <div class="inner-column">
                    <div class="container-user" title="Created By User">
                      <div class="by-user">
                        <i class="fa-solid fa-user fa-sm" style="color: #000000;"></i>
                      </div>
                      <div class="createdUser">
                        ${post.createdByUser}
                      </div>
                    </div>
                  </div>
                  <div class="inner-column">
                    <div class="container-datetime title="Created Time"">
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
                        <i class="fa-solid fa-hashtag" style="color: #ff0000;"></i>
                      </div>
                      <div class="tag-category" id="postCategory">
                        ${post.belongedToCategory}
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
                  <div class="container-img" title="Cover Image">
                    <img src="data:image/jpeg;base64, ${post.mediaList}" alt="Post Image">
                  </div>
                </div>
                <div class="column">
                  <div class="inner-column">
                    <div class="container-user" title="Total Like">
                      <div class="by-user">
                        <i class="fa-regular fa-thumbs-up fa-xl" style="color: #000000;"></i>
                      </div>
                      <div class="total-like" id="voteCount">
                        ${post.vote1Count}
                      </div>
                    </div>
                  </div>
                  <div class="inner-column">
                    <div class="container-read">
                      <div class="number-read">
                        <i class="fa-brands fa-readme fa-bounce" style="color: #000000;"></i>
                      </div>
                      <div class="number-read" id="readingTime">
                        ${post.readingTime}
                      </div>
                    </div>
                  </div>

                  <div class="inner-column">
                    <div class="tag-list" id="tagList">
                      ${containerReadHTML}
                    </div>
                </div>
                </div>
              </div>
            </div>
          `;
          
          container.append(postHTML);

          const infoElement = container.find('.postContent h2:last-child');
          infoElement.css({
            maxWidth: "1200px",
            overflow: "hidden",
            textOverflow: "ellipsis",
            whiteSpace: "nowrap"
          });

        });
      });
    }
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
    const categoryList = document.querySelector(".absolute.hidden.text-gray-700.pt-1.group-hover\\:block");
  
    categories.forEach((category) => {
      const listItem = document.createElement("li");
      const link = document.createElement("a");
      link.className = "rounded-b bg-black hover:bg-gray-400 py-5 px-5 block whitespace-no-wrap text-white";
      link.href = `postByCategory.html?categoryName=${encodeURIComponent(category.content)}`;
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
  