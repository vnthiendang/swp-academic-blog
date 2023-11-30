import { userInfo } from '../js/Services/auth.service.js';
import { deletePost, getPostById, getPostByUserId } from './Services/post.service.js';
import { updateProfile } from './Services/profile.service.js';

const showHeaderForTeacher = async () => {
  try {
    const usersInfo = await userInfo();
    const userRole = usersInfo.role_id;

    if (userRole === 'Teacher') {
      // Display the form
      document.getElementById('teacherPage').style.display = 'block';
      document.getElementById('teacherPage2').style.display = 'block';
      document.getElementById('requestCategoryBtn').style.display = 'inline-block';
    } else {
      // Hide the form
      document.getElementById('teacherPage').style.display = 'none';
      document.getElementById('teacherPage2').style.display = 'none';
      document.getElementById('requestCategoryBtn').style.display = 'none';
    }
  } catch (error) {
  }
};
showHeaderForTeacher();

const showHeaderForTeacher2 = async () => {
  try {
    const usersInfo = await userInfo();
    const userRole = usersInfo.role_id;

    const createLink = document.getElementById('createLink');
    const bestArticlesLink = document.getElementById('bestArticlesLink');
    const homeLink = document.getElementById('homeLink');
    const logoLink = document.getElementById('logoLink');
    const logoLink2 = document.getElementById('logoLink2');

    if (userRole === 'Student' || userRole === 'Teacher') {
      // Display the links
      createLink.style.display = 'block';
      bestArticlesLink.style.display = 'block';
      homeLink.style.display = 'block';
      logoLink.style.display = 'none';
      logoLink2.style.display = 'show'
    } else {
      // Hide the links
      createLink.style.display = 'none';
      bestArticlesLink.style.display = 'none';
      homeLink.style.display = 'none';
      logoLink.style.display = 'show';
      logoLink2.style.display = 'none'
    }
  } catch (error) {
    // Handle errors here
  }
};

// Call the function when the page loads
showHeaderForTeacher2();


const getUserInfo = async () => {

    const userInfos = await userInfo();
  
    document.getElementById('roleField').value = userInfos.role_id;
    
    document.getElementById('nameField').value = userInfos.display_name;
    document.getElementById('emailField').value = userInfos.email;
}
    
getUserInfo();

  const saveButton = document.getElementById('saveButton');

  saveButton.addEventListener('click', async () => {

    const userInfos = await userInfo();
    var usId = userInfos.userId; 
  
    const name = document.getElementById("nameField").value;
    const email = document.getElementById("emailField").value;
    const currentPassword = document.getElementById("currentpass").value;
    const newPassword = document.getElementById("newpass").value;
    const repeatPassword = document.getElementById("repass").value;
  
    if(newPassword !== repeatPassword){
      alert('Repeat password does not match!');
      return;
    }

    if(repeatPassword != ""){
      var model = {
        userId:`${usId}`,
        display_name: name,
        email: email,
        password: repeatPassword
      };
    }else{
      var model = {
        userId:`${usId}`,
        display_name: name,
        email: email
      };
    }

    try {
      const res = await updateProfile(model);
  
      if (res != null) {
        alert('Update successfully!');
        location.reload();
      } else {
        alert('Failed to update profile.');
      }
    } catch (error) {
      console.error(error);
    }
  });

// Retrieve posts by userId
const displayPosts = async() => {
  try {
    const userInfos = await userInfo();
    const usId = userInfos.userId;
    const postApprovalStatuses = "approved,rejected,pending";

    const posts = await getPostByUserId(postApprovalStatuses, usId);

    if(posts.length === 0){
      const postDiv = document.querySelector('.postDiv');
      const noResultsElement = document.createElement("div");
      noResultsElement.className =
        "text-center text-4xl font-bold text-gray-500 dark:text-gray-400";
      noResultsElement.textContent =
        "Do not have any posted blog yet.";
      postDiv.appendChild(noResultsElement);
    }else{
      const tableBody = document.querySelector('#postTable tbody');
      tableBody.innerHTML = '';

      posts.forEach((post, index) => {
        console.log(post);
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${index + 1}</td>
          <td>${post.title}</td>
          <td>${post.belongedToCategory}</td>
          <td>${post.status || 'pending'}</td>
          <td>${post.postApprovals}</td>
          <td>
            <form class="form"> 
              <div class="flex flex-col">
                <label for="">
                  <button 
                    class="editPostButton" 
                    style="background-color: green; color: white;" 
                    class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-55 p-2.5" 
                    data-post-id="${post.postsId}">Edit
                  </button>
                </label>
              </div>
            </form>
          </td>
          <td><button id="deleteBtn" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-55 p-2.5"
          data-post-id="${post.postsId}">Delete
          </button></td>
        `;
  
        const editButton = row.querySelector('.editPostButton');
        editButton.addEventListener('click', () => {
          const postId = editButton.getAttribute('data-post-id');
        
          const width = window.innerWidth;
          const height = window.innerHeight;
          const left = 0;
          const top = 0;
          const url = `editPost.html?belongedToPostID=${postId}`;
        
          window.open(url, "Edit Post", `width=${width}, height=${height}, left=${left}, top=${top}`);
        });
  
        const delButton = row.querySelector('#deleteBtn');
        delButton.addEventListener('click', async () => {
          const postId = editButton.getAttribute('data-post-id');
  
          const confirmed = confirm("Are you sure you want to delete this post?");
          if (confirmed) {
            const res = await deletePost(postId);
            if (res != null) {
              alert('Delete Post Successfully!');
              location.reload();
            } else {
              alert('Fail to delete post!');
            }
          }
        });
    
        tableBody.appendChild(row);
      });
    }
  

  } catch (error) {
    console.error('Error retrieving and displaying posts:', error);
  }
}

displayPosts();

const requestCategoryBtn = document.getElementById("requestCategoryBtn");

requestCategoryBtn.addEventListener("click", async function () {
  try {
    const userInfos = await userInfo();

    const width = 1000; // Set your desired width
    const height = 450; // Set your desired height
    const left = (window.innerWidth - width) / 2;
    const top = (window.innerHeight - height) / 2;
    const url = `requestCategory.html?requestedByUserId=${userInfos.userId}`;

    window.open(
      url,
      "Request Category",
      `width=${width}, height=${height}, left=${left}, top=${top}`
    );
  } catch (error) {
    console.error("An error occurred:", error);
  }
});


