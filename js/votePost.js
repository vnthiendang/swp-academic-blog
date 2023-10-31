import {
  getAllApprovedPosts,
  getPostByVoteCount,
  searchedPosts,
} from "./Services/post.service.js";
import { getAllCategory } from "./Services/category.service.js";

const options = {
  month: "short", // Two-digit month (e.g., 01)
  day: "2-digit", // Two-digit day (e.g., 18)
  hour: "2-digit", // Two-digit hour (e.g., 14)
};

// DISPLAY LIST APPROVED POSTS
function displayPosts(posts) {
  const postContainer = document.querySelector("#post .col-span-5");
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
      postElement.className = "";

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
      infoElement.textContent = post.postDetail;
      postLink.appendChild(infoElement);

      postElement.appendChild(postLink);

      const flexItemsElement = document.createElement("div");
      flexItemsElement.className = "flex items-center px-8";

      const iconTagElement = document.createElement("div");
      iconTagElement.className = "icon-tag";
      const iconTagImg = document.createElement("img");
      iconTagImg.src = "img/tag.png";
      iconTagImg.alt = "icon-tag";
      iconTagElement.appendChild(iconTagImg);
      flexItemsElement.appendChild(iconTagElement);

      const tagLink1 = document.createElement("a");
      tagLink1.href = "/pageByTag.html?tagId";

      
      post.tagList.forEach((tag) => {
        const postTagList = document.createElement("div");
        postTagList.className = "rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
        //const tagItem = document.createElement("span");
        postTagList.textContent = tag;
        tagLink1.appendChild(postTagList);
        //postTagList.appendChild(tagItem);
      });
      // postElement.appendChild(postTagList);

      flexItemsElement.appendChild(tagLink1);

      const voteIcon = document.createElement("i");
      voteIcon.className =
        "fa-regular fa-heart fa-beat-fade fa-xl";
      flexItemsElement.appendChild(voteIcon); 

      const voteList = document.createElement("div");
      voteList.className =
        "vote-list rounded-xl bg-gray-300 text-gray-900 px-2 mr-4";
        voteList.textContent = post.vote1Count;
      // post.voteList.forEach((vote) => {
      //   const item = document.createElement("span");
      //   item.textContent = vote + "  ";
      //   voteList.appendChild(item);
      // });
      flexItemsElement.appendChild(voteList);

      postElement.appendChild(flexItemsElement);

      // if(post.mediaList.length > 0){
      //   const imgContainer = document.createElement('div');
      //   imgContainer.className = 'm-5';

      //   const mediaList = document.createElement('div');
      //   mediaList.className = 'media-list';
      //   post.mediaList.forEach(media => {
      //         const mediaItem = document.createElement('img');
      //         mediaItem.src = media;
      //         mediaList.appendChild(mediaItem);
      //   });
      //   imgContainer.appendChild(mediaList);
      //   postElement.appendChild(imgContainer);
      // }

      postContainer.appendChild(postElement);

      const hr = document.createElement("hr");
      hr.style.border = "none"; // Remove the default border
      hr.style.backgroundColor = "grey"; // Set the background color to create a solid line
      hr.style.height = "2px";
      hr.style.margin="10px " // Set the thickness of the line to 2 pixels/ Increase the line height to 4 pixels
      postContainer.appendChild(hr);
    }
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


//GET LIST CATEGORIES
const displayCategories = async (categories) => {
  const categoryList = document.querySelector(".absolute.hidden.text-gray-700.pt-1.group-hover\\:block");

  categories.forEach((category) => {
    const listItem = document.createElement("li");
    const link = document.createElement("a");
    link.className = "rounded-b bg-black hover:bg-gray-400 py-5 px-5 block whitespace-no-wrap text-white";
    link.textContent = category.content;

    listItem.appendChild(link);
    categoryList.appendChild(listItem);

    link.addEventListener("click", async () => {
    const posts = await getPostByVoteCount(category.id);
    displayPosts(posts);
      
    });
  });
};
