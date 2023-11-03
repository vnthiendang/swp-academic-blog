import { userInfo } from '../js/Services/auth.service.js';


const getUserInfo = async () => {

    const userInfos = await userInfo();
    console.log(userInfos);
  
    document.getElementById('roleField').value = userInfos.role_id;
    
    document.getElementById('nameField').value = userInfos.display_name;
    document.getElementById('emailField').value = userInfos.email;
}
    
getUserInfo();

  //document.getElementById("saveButton").addEventListener("click", updateUserProfile);

  const form = document.getElementById("update-form");

  form.addEventListener("submit", async (event) => {
    event.preventDefault();
  
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
      const response = await axios.put('http://localhost:8080/blog/user/update', model, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem("token")}`,
          'Content-Type': 'application/json'
        }
      });
  
      if (response.status === 200) {
        alert('Update successfully!');
        location.reload();
      } else {
        alert('Failed to update profile.');
      }
    } catch (error) {
      alert('An error occurred during the update process.');
      console.error(error);
    }
  });



