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
      posts.forEach((postData) => {
        const postElement = document.querySelector(".wapper-title");
        postElement.innerHTML = `
          <!-- ByUser DateTime NumberRead -->
          <div class="column">
            <!-- Rest of the HTML structure for ByUser, DateTime, and NumberRead -->
          </div>
          
          <!-- Title Content Img -->
          <div class="container-post-img">
            <!-- Rest of the HTML structure for Title, Content, and Img -->
          </div>
  
          <!-- Category NumberRead -->
          <div class="column">
            <!-- Rest of the HTML structure for Category and NumberRead -->
          </div>
        `;
        
        const byUser = document.getElementsByClassName('createdUser');
        if (postData.createdByUser) {
          byUser.textContent = postData.createdByUser;
        } else {
          byUser.textContent = '';
        }
      
        // Date Time
        const postDate = document.getElementById('postDate');
        const postCreatedTime = new Date(postData.createdTime);
        const formattedTime = postCreatedTime.toLocaleString("en-US", options);
        postDate.textContent = formattedTime;
      
        // Tag Category
        const postCategory = document.getElementById('postCategory');
        postCategory.textContent = postData.belongedToCategory;
      
        // Title
        const postTitle = document.querySelector('.postTitle h2');
        postTitle.textContent = postData.title;
      
        // Content
        const postContent = document.querySelector('.postContent h2');
        postContent.textContent = postData.postDetail;
      
        // Image
        const postImage = document.querySelector('.container-img img');
        
        if(postData.mediaList.length > 0){
          
          postData.mediaList.forEach(media => {
            postImage.src = `data:image/jpeg;base64, ${media}`;
          });
        }
      
        // Number Read
        const readingTime = document.getElementById('readingTime');
        readingTime.textContent = postData.readingTime;
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
  