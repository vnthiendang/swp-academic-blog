import {
  filterPost,
  getAllApprovedPosts,
  searchedPosts,
} from "./Services/post.service.js";
import { getAllCategory } from "./Services/category.service.js";
import { getAllTag } from "./Services/tag.service.js";
import { userInfo } from "./Services/auth.service.js";

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
      document.getElementById('teacherPage2').style.display = 'block';
    } else {
      // Hide the form
      document.getElementById('teacherPage').style.display = 'none';
      document.getElementById('teacherPage2').style.display = 'none';
    }
  } catch (error) {
  }
};

showHeaderForTeacher();

// DISPLAY LIST APPROVED POSTS
function displayPosts(posts) {
  const postContainer = document.querySelector(".col-span-5");
  postContainer.innerHTML = "";

  if (posts.length === 0) {
    const noResultsElement = document.createElement("div");
    noResultsElement.className =
      "text-center text-4xl font-bold text-gray-500 dark:text-gray-400";
    noResultsElement.textContent =
      "No results related to your filter. Please use other options.";
    postContainer.appendChild(noResultsElement);
  } else {
    for (var i = 0; i < posts.length; i++) {
      var post = posts[i];
    
      const columnElement = document.createElement("div");
      columnElement.className = "column";
    
      const postTitleElement = document.createElement("div");
      postTitleElement.className = "mr-7";
      postTitleElement.id = "postTitle";
      postTitleElement.textContent = post.title;
      columnElement.appendChild(postTitleElement);
    
      postContainer.appendChild(columnElement);
    
      const infoColumnElement = document.createElement("div");
      infoColumnElement.className = "column";
      infoColumnElement.style.backgroundColor = "white";
    
      const postTagElement = document.createElement("div");
      postTagElement.id = "postTag";
      infoColumnElement.appendChild(postTagElement);
    
      const innerColumn1 = document.createElement("div");
      innerColumn1.className = "inner-column";
    
      const containerUserElement = document.createElement("div");
      containerUserElement.className = "container-user";
      containerUserElement.title = "Name User";
    
      const byUserIcon = document.createElement("div");
      byUserIcon.className = "by-user";
      const userIcon = document.createElement("i");
      userIcon.className = "fa-solid fa-user fa-sm";
      userIcon.style.color = "#000000";
      byUserIcon.appendChild(userIcon);
      containerUserElement.appendChild(byUserIcon);
    
      const byUserElement = document.createElement("div");
      byUserElement.className = "by-user";
      byUserElement.id = "postAuthor";
      byUserElement.textContent = post.createdByUser;
      containerUserElement.appendChild(byUserElement);
    
      innerColumn1.appendChild(containerUserElement);
      infoColumnElement.appendChild(innerColumn1);
    
      const innerColumn2 = document.createElement("div");
      innerColumn2.className = "inner-column";
    
      const containerDateTimeElement = document.createElement("div");
      containerDateTimeElement.className = "container-datetime";
      containerDateTimeElement.title = "Date Time";
    
      const dateTimeIcon = document.createElement("div");
      dateTimeIcon.className = "date-time";
      const calendarIcon = document.createElement("i");
      calendarIcon.className = "fa-regular fa-calendar-days";
      calendarIcon.style.color = "#000000";
      dateTimeIcon.appendChild(calendarIcon);
      containerDateTimeElement.appendChild(dateTimeIcon);
    
      const dateTimeElement = document.createElement("div");
      dateTimeElement.className = "date-time";
      dateTimeElement.id = "postDate";
      const postCreatedTime = new Date(post.createdTime);
      const formattedTime = postCreatedTime.toLocaleString("en-US", options);
      dateTimeElement.textContent = formattedTime;
      containerDateTimeElement.appendChild(dateTimeElement);
    
      innerColumn2.appendChild(containerDateTimeElement);
      infoColumnElement.appendChild(innerColumn2);
    
      const innerColumn3 = document.createElement("div");
      innerColumn3.className = "inner-column";
    
      const containerReadElement = document.createElement("div");
      containerReadElement.className = "container-read";
      containerReadElement.title = "Reading Time";
    
      const numberReadIcon = document.createElement("div");
      numberReadIcon.className = "number-read";
      const clockIcon = document.createElement("i");
      clockIcon.className = "fa-regular fa-clock";
      clockIcon.style.color = "#000000";
      numberReadIcon.appendChild(clockIcon);
      containerReadElement.appendChild(numberReadIcon);
    
      const numberReadElement = document.createElement("div");
      numberReadElement.className = "number-read";
      numberReadElement.id = "readingTime";
      numberReadElement.textContent = post.readingTime;
      containerReadElement.appendChild(numberReadElement);
    
      innerColumn3.appendChild(containerReadElement);
      infoColumnElement.appendChild(innerColumn3);
      
      if (post.awardList != null && post.awardList.length > 0) {
        const innerColumn5 = document.createElement("div");
        innerColumn5.className = "inner-column";
        const awardElement = document.createElement("div");
        awardElement.className = "container-award";
        awardElement.title = "Award";
      
        const awardIcon = document.createElement("i");
        awardIcon.className = "fas fa-crown";
        awardIcon.style.color = "#c45d08";
        awardElement.appendChild(awardIcon);
      
        post.awardList.forEach(award => {
          const awardList = document.createElement("div");
          awardList.className = "award-list rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
          const items = document.createElement("span");
          items.textContent = award;
          awardList.appendChild(items);
          awardElement.appendChild(awardList);
        })
        
        innerColumn5.appendChild(awardElement);
        infoColumnElement.appendChild(innerColumn5);
      }
      
      const innerColumn4 = document.createElement("div");
      innerColumn4.className = "inner-column";
    
      const containerTagElement = document.createElement("div");
      containerTagElement.className = "container-tag";
      containerTagElement.title = "Category";
    
      const tagCategoryElement = document.createElement("div");
      tagCategoryElement.className = "tag-category";
      const categoryHeading = document.createElement("h2");
      categoryHeading.textContent = "Category:";
      tagCategoryElement.appendChild(categoryHeading);
      containerTagElement.appendChild(tagCategoryElement);
    
      const containerTagHoverElement = document.createElement("div");
      containerTagHoverElement.className = "container-tag-hover";
    
      const tagCategoryIcon = document.createElement("div");
      tagCategoryIcon.className = "tag-category";
      const hashtagIcon = document.createElement("i");
      hashtagIcon.className = "fa-solid fa-hashtag";
      tagCategoryIcon.appendChild(hashtagIcon);
      containerTagHoverElement.appendChild(tagCategoryIcon);
    
      const tagCategoryElement2 = document.createElement("div");
      tagCategoryElement2.className = "tag-category";
      tagCategoryElement2.id = "postCategory";
      tagCategoryElement2.textContent= post.belongedToCategory;
      containerTagHoverElement.appendChild(tagCategoryElement2);
    
      containerTagElement.appendChild(containerTagHoverElement);
      innerColumn4.appendChild(containerTagElement);
      infoColumnElement.appendChild(innerColumn4);
    
      postContainer.appendChild(infoColumnElement);
    
      const wrapperTitlePostElement = document.createElement("div");
      wrapperTitlePostElement.className = "wapper-title-post";
    
      const columnElement2 = document.createElement("div");
      columnElement2.className = "column";
    
      const postContentElement = document.createElement("p");
      postContentElement.className = "postContent";
      postContentElement.id = "postContent";
      postContentElement.textContent = post.detail;
      columnElement2.appendChild(postContentElement);
    
      wrapperTitlePostElement.appendChild(columnElement2);
      infoColumnElement.appendChild(wrapperTitlePostElement);
      
      if (post.tagList.length > 0) {
        const innerColumn6 = document.createElement("div");
        innerColumn6.className = "inner-column";

        const iconTagElement = document.createElement('div');
        iconTagElement.className = 'icon-tag';
        const iconTagImg = document.createElement('img');
        iconTagImg.src = 'img/tag.png';
        iconTagImg.alt = 'icon-tag';
        iconTagElement.appendChild(iconTagImg);
        innerColumn6.appendChild(iconTagElement);
      
        post.tagList.forEach(tag => {
          const tagLink = document.createElement('a');
          tagLink.href = `/pageByTag.html?tagName=${encodeURIComponent(tag)}`;
          tagLink.id = "postTag";
          const tagDiv = document.createElement('div');
          tagDiv.className = 'rounded-xl bg-gray-300 text-gray-900 px-2 mr-4';
          tagDiv.textContent = tag;
          tagLink.appendChild(tagDiv);
          innerColumn6.appendChild(tagLink);
        });
        infoColumnElement.appendChild(innerColumn6);
      }
    
      postContainer.appendChild(wrapperTitlePostElement);
    
    }
  }
}

// Function to display all approved posts
// const displayAllPosts = () => {
//   getAllApprovedPosts()
//     .then((posts) => {
//       displayPosts(posts);
//     })
//     .catch((error) => {
//       console.error(error);
//       // Handle the error as needed
//     });
// };

getAllCategory().then((cates) => {
  displayCategories(cates);
});

getAllTag().then((tags) => {
  displayTags(tags);
});

//displayAllPosts();


//GET LIST CATEGORIES
const displayCategories = (categories) => {
  const selectElement = document.getElementById('Category');

  categories.forEach(category => {
    const option = document.createElement('option');
    option.value = category.content;
    option.textContent = category.content;

    selectElement.appendChild(option);
  });
};

const displayTags = async (tags) => {
  const tagList = document.querySelector("#sort-tag");

  tags.forEach((tag) => {
    const listItem = document.createElement("label");
    listItem.style.backgroundColor = "burlywood";
    listItem.innerHTML = `
      <input type="checkbox" class="tag-checkbox" value="${tag.tagName}" />
      ${tag.tagName}
    `;

    tagList.appendChild(listItem);
  });
};

const displaySortBy = () => {
  const selectElement = document.querySelector('#sort-by');

  const opt1 = document.createElement("label");
  opt1.style.backgroundColor = "burlywood";
  opt1.innerHTML = `
    <input type="checkbox" class="sortBy-checkbox" value="likeCount" />
    Like Count
  `;

  const opt2 = document.createElement("label");
  opt2.style.backgroundColor = "burlywood";
  opt2.innerHTML = `
    <input type="checkbox" class="sortBy-checkbox" value="dislikeCount" />
    Dislike Count
  `;

  const opt3 = document.createElement("label");
  opt3.style.backgroundColor = "burlywood";
  opt3.innerHTML = `
    <input type="checkbox" class="sortBy-checkbox" value="createdDate" />
    Created Date
  `;

  const opt4 = document.createElement("label");
  opt4.style.backgroundColor = "burlywood";
  opt4.innerHTML = `
    <input type="checkbox" class="sortBy-checkbox" value="awardCount" />
    Award Count
  `;

  selectElement.appendChild(opt1);
  selectElement.appendChild(opt2);
  selectElement.appendChild(opt3);
  selectElement.appendChild(opt4);
};

displaySortBy();

const displaysortDirection = () => {
  const selectElement = document.getElementById('sortDirection');

  const option = document.createElement('option');
  option.value = 'asc';
  option.textContent = 'Ascending';

  const option1 = document.createElement('option');
  option1.value = 'desc';
  option1.textContent = 'Descending';

  selectElement.appendChild(option);
  selectElement.appendChild(option1);
};

displaysortDirection();

const filterPosts = document.querySelector("#button-filter");
filterPosts.addEventListener("click", async () => {

  const selectElement = document.getElementById('Category');
  const categoryName = selectElement.value;

  const tagCheckboxes = document.querySelectorAll('.tag-checkbox');
  const tagNames = Array.from(tagCheckboxes)
    .filter(checkbox => checkbox.checked)
    .map(checkbox => checkbox.value);

  const sortByCheckboxes = document.querySelectorAll('.sortBy-checkbox');
  const sortBy = Array.from(sortByCheckboxes)
    .filter(checkbox => checkbox.checked)
    .map(checkbox => checkbox.value);

  const sortDirection = document.getElementById('sortDirection');

  const startDates = document.getElementById("date_timepicker_start");
  const startDateValue = startDates.value;
  
  let formattedStartDate = null;

  if (startDateValue) {
    const startDateParts = startDateValue.split("-");
    const year = parseInt(startDateParts[0]);
    const month = parseInt(startDateParts[1]);
    const day = parseInt(startDateParts[2]);
  
    if (!isNaN(year) && !isNaN(month) && !isNaN(day)) {
      formattedStartDate = new Date(year, month - 1, day).toISOString().slice(0, -1);
    }
  }
  
    const endDates = document.getElementById("date_timepicker_end");
    const endDateValue = endDates.value;

    let formattedEndDate = null;
  
    if(endDateValue){
      const endDateParts = endDateValue.split("-");
      const yearEnd = parseInt(endDateParts[0]);
      const monthEnd = parseInt(endDateParts[1]);
      const dayEnd = parseInt(endDateParts[2]);
    
      if (!isNaN(yearEnd) && !isNaN(monthEnd) && !isNaN(dayEnd)) {
        formattedEndDate = new Date(yearEnd, monthEnd - 1, dayEnd).toISOString().slice(0, -1);
      }
    }
  
    try {
      const response = await filterPost(
        categoryName,
        tagNames,
        formattedStartDate,
        formattedEndDate,
        sortBy,
        sortDirection.value
      );
      if (response == null) {
        alert("Please check your filter!");
      } else {
        displayPosts(response);
      }
    } catch (error) {
      console.error("Error filtering posts:", error);
    }
});

