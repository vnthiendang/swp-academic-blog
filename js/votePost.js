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

// DISPLAY LIST FILTERED POSTS
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
                      <div class="tag-category">
                        <h2>Tag: </h2>
                      </div>
                      <div class="tag-list" id="tagList">
                        ${tagsHTML}
                      </div>
                    </div>
                  ` :
                                    "";
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

const displayTags = async(tags) => {
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
filterPosts.addEventListener("click", async() => {

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

    if (endDateValue) {
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