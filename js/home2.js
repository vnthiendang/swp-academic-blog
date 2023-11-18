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
      } else {
        // Hide the form
        document.getElementById('teacherPage').style.display = 'none';
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
      posts.forEach((post) => {
        const postElement = document.createElement("div");
        postElement.innerHTML = `
          <div class="wapper-title">
            <div class="column">
              <div class="inner-column">
                <div class="container-user">
                  <div class="by-user">
                    <i class="fa-solid fa-user fa-sm" style="color: #000000;"></i>
                  </div>
                  <div class="by-user-logo"></div>
                </div>
              </div>
            </div>
            <div class="column">
              <div class="inner-column">
                <div class="container-user">
                  <div class="by-user">
                    <i class="fa-solid fa-user fa-sm" style="color: #000000;"></i>
                  </div>
                  <div class="by-user">${post.createdByUser}</div>
                </div>
              </div>
            </div>
            <div class="column">
              <div class="inner-column">
                <div class="container-datetime">
                  <div class="date-time">
                    <i class="fa-regular fa-calendar-days" style="color: #000000;"></i>
                  </div>
                  <div class="date-time">${new Date(post.createdTime).toLocaleString("en-US", options)}</div>
                </div>
              </div>
            </div>
            <div class="column">
              <div class="inner-column">
                <div class="container-tag">
                  <div class="tag-category">
                    <i class="fa-solid fa-hashtag" style="color: #ff0000;"></i>
                  </div>
                  <div class="tag-category">${post.belongedToCategory}</div>
                </div>
              </div>
            </div>
          </div>
          <div class="container-post-img">
            <div class="post-title-content">
              <div class="column-title">
                <div class="postTitle">
                  <h2>${post.title}</h2>
                </div>
              </div>
              <div class="column-content">
                <div class="postContent">${post.content}</div>
              </div>
            </div>
          </div>
          ${post.imageUrl ? `<img src="${post.imageUrl}" alt="Post Image">` : ""}
        `;
  
        postContainer.appendChild(postElement);
      });
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
  