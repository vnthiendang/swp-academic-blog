


const token = localStorage.getItem("token");

const userInfo = async () => {
  try {
    const response = await axios.get(`http://localhost:8080/blog/user/profile`, {
      headers: {
        Authorization: `Bearer ${token}` 
      }
    });
    return response.data;
  } catch (error) {
    alert(response.status);
  }
};
  // Function to fetch user information from the API
const getUserInfo = async () => {

    const userInfos = userInfo;
    console.log(userInfos);
  
    // Update the HTML with the user information
    document.getElementById('roleField').value = userInfos.role_id;
    document.getElementById('nameField').value = userInfos.display_name;
    document.getElementById('emailField').value = userInfos.email;
}
    
  
  // Call the function to fetch and display the user information
  getUserInfo;