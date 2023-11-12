import { userInfo } from '../js/Services/auth.service.js';
import { getPostById, getPostByUserId, updatePost } from './Services/post.service.js';
import { updateProfile } from './Services/profile.service.js';

const getUserInfo = async () => {

    const userInfos = await userInfo();
    console.log(userInfos);
  
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

    var model = {
      userId:`${usId}`,
      display_name: name,
      email: email,
      password: repeatPassword
    };

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
    const posts = await getPostByUserId(usId);
    const tableBody = document.querySelector('#postTable tbody');
    tableBody.innerHTML = '';
  
    posts.forEach((post, index) => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${index + 1}</td>
        <td>${post.title}</td>
        <td>${post.belongedToCategory}</td>
        <td>${post.postApprovals}</td>
        <td>
          <form class="form"> 
            <div class="flex flex-col">
              <label for="">
                <button 
                  class="editPostButton" 
                  style="background-color: green; color: white;" 
                  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-55 p-2.5" 
                  data-post-id="${post.postsId}"
                >
                  Edit
                </button>
              </label>
            </div>
          </form>
        </td>
        <td><a href="#" class="delete">Delete</a></td>
      `;
  
      // Add event listener to the Edit button
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
  
      tableBody.appendChild(row);
    });
  
  } catch (error) {
    console.error('Error retrieving and displaying posts:', error);
  }
}

displayPosts();


// function openEditPostPopup(postId) {
  
//   const width = window.innerWidth;
//   const height = window.innerHeight;
//   const left = 0;
//   const top = 0;
//   const url = `editPost.html?belongedToPostID=${postId}`; 
  
//   window.open(url, "Edit Post", `width=${width}, height=${height}, left=${left}, top=${top}`);
// }


