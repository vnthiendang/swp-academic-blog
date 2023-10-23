import { userInfo } from '../js/Services/auth.service.js';


const getUserInfo = async () => {

    const userInfos = await userInfo();
    console.log(userInfos);
  
    document.getElementById('roleField').value = userInfos.role_id;
    
    document.getElementById('nameField').value = userInfos.display_name;
    document.getElementById('emailField').value = userInfos.email;
}
    
  // Call the function to fetch and display the user information
  getUserInfo();