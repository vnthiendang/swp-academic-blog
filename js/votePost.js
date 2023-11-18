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

      const postElement = document.createElement("div");
      postElement.className = 'col-span-5';

      postElement.className = "post-container";
      postElement.style.width = "1350px"

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
      postTagLink.href = `/postByCategory.html?categoryName=${encodeURIComponent(post.belongedToCategory)}`;

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
        // post.awardList.forEach((award) => {
        //   const items = document.createElement("span");
        //   items.textContent = award;
        //   awardList.appendChild(items);
        // });
        flexElement.appendChild(awardList);
      }

      const dateTimeElement3 = document.createElement("div");
      dateTimeElement3.className = "p-0.5 bg-gray-900 rounded-full mx-4";
      flexElement.appendChild(dateTimeElement3);

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

      // const infoElement = document.createElement("p");
      // infoElement.className = "px-6 py-7 post-detail";
      // infoElement.innerHTML = post.postDetail;
      // postLink.appendChild(infoElement);

      postElement.appendChild(postLink);

      const flexItemsElement = document.createElement("div");
      flexItemsElement.className = "flex items-center px-8";

      if (post.tagList.length > 0) {
        const iconTagElement = document.createElement('div');
        iconTagElement.className = 'icon-tag';
        const iconTagImg = document.createElement('img');
        iconTagImg.src = 'img/tag.png';
        iconTagImg.alt = 'icon-tag';
        iconTagElement.appendChild(iconTagImg);
        flexItemsElement.appendChild(iconTagElement);
      
        post.tagList.forEach(tag => {
          const tagLink = document.createElement('a');
          tagLink.href = `/pageByTag.html?tagName=${encodeURIComponent(tag)}`;
          const tagDiv = document.createElement('div');
          tagDiv.className = 'rounded-xl bg-gray-300 text-gray-900 px-2 mr-4';
          tagDiv.textContent = tag;
          tagLink.appendChild(tagDiv);
          flexItemsElement.appendChild(tagLink);
        });
      }

      postElement.appendChild(flexItemsElement);

      if(post.mediaList.length > 0){
        const imgContainer = document.createElement('div');
        imgContainer.className = 'm-5';

        const mediaList = document.createElement('div');
        mediaList.className = 'media-list';
        post.mediaList.forEach(media => {
          const mediaItem = document.createElement('img');
          mediaItem.src = `data:image/jpeg;base64, ${media}`;
          mediaItem.style.width = '240px'; 
          mediaItem.style.height = 'auto';
          mediaList.appendChild(mediaItem);
        });
        imgContainer.appendChild(mediaList);
        postElement.appendChild(imgContainer);

      }

      postContainer.appendChild(postElement);

      // const hr = document.createElement("hr");
      // hr.style.border = "none"; // Remove the default border
      // hr.style.backgroundColor = "grey"; // Set the background color to create a solid line
      // hr.style.height = "2px";
      // hr.style.margin="10px " // Set the thickness of the line to 2 pixels/ Increase the line height to 4 pixels
      // postContainer.appendChild(hr);
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

  const selectSortDirection = document.getElementById('sortDirection');
  const sortDirection = selectSortDirection.value;

  const startDates = document.getElementById('date_timepicker_start');
  const startDateValue = startDates.value;

// Parse the date string in the format MM/DD/YYYY
const startDateParts = startDateValue.split('/');
const year = parseInt(startDateParts[2]);
const month = parseInt(startDateParts[0]);
const day = parseInt(startDateParts[1]);

const startDate = {
  year,
  month,
  day,
  hour: 0,
  minute: 0,
  second: 0
};

  const endDates = document.getElementById('date_timepicker_end');
  const endDate = endDates.value;

  try {
    const response = await filterPost(categoryName, tagNames, startDate, endDate, sortBy, sortDirection);
    if(response == null){
      alert('Please check your filter!');
    }else{
      displayPosts(response);
    }
  } catch (error) {
    console.error('Error filtering posts:', error);
  }
});

