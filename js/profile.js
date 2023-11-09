import { userInfo } from '../js/Services/auth.service.js';
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



