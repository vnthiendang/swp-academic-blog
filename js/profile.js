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


  const updateUserProfile = async () =>{
    // Get the input field values
    const userInfos = await userInfo();
    var usId = userInfos.userId; 
    var name = document.getElementById("nameField").value;
    var email = document.getElementById("emailField").value;
    var currentPassword = document.getElementById("currentpass").value;
    var newPassword = document.getElementById("newpass").value;
    var repeatPassword = document.getElementById("repass").value;

    // if(currentPassword !== userInfos.password){
    //   alert('Please check your current password!');
    //   return;
    // }

    if(newPassword !== repeatPassword){
      alert('Repeat password does not match!');
      return;
    }

    var model = {
      userId:usId,
      display_name: name,
      email: email,
      password: repeatPassword
    };

    try {
      const response = await updateProfile(model);
      if(response == null){
        alert('Please check your information!');
      }else{
        alert('Update successfully!');
      }
    } catch (error) {
      alert(error);
    }

  }

  document.getElementById("saveButton").addEventListener("click", updateUserProfile);



