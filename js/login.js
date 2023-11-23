
import { login } from "../js/Services/auth.service.js";
import { updateProfile } from './Services/profile.service.js';
import { userInfo } from '../js/Services/auth.service.js';

const form = document.getElementById("login");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const data = { email, password };

  try {
    const userData = await login(data);
    const roleId = userData.role_id;

    if (roleId === 'Admin') {
      window.location.href = "../managerAdmin.html";
    } else if (roleId === 'Teacher') {
      window.location.href = "../home2.html";
    } else if (roleId === 'Student') {
      window.location.href = "../home2.html";
    }
  } catch (error) {
    alert('Invalid email or password!');
  }
});

const saveButton = document.getElementById('resetButton');

saveButton.addEventListener('click', async () => {

  const userInfos = await userInfo();
  console.log(userInfos);

  const email = document.getElementById("emailField").value;
  const newPassword = document.getElementById("newpass").value;
  const repeatPassword = document.getElementById("repass").value;

  if(email !== userInfos.email){
    alert('Please check your correct email!');
    return;
  }

  if(newPassword !== repeatPassword){
    alert('Repeat password does not match!');
    return;
  }

  var model = {
    userId:`${userInfos.userId}`,
    display_name: userInfos.display_name,
    email: userInfos.email,
    password: repeatPassword
  };

  try {
    const res = await updateProfile(model);

    if (res != null) {
      alert('Your password has been reset!');
      window.location.href = "../login.html";
    } else {
      alert('Failed to edit password.');
    }
  } catch (error) {
    console.error(error);
  }
});