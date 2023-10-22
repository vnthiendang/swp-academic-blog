
import { login } from "../js/Services/auth.service.js";

const form = document.getElementById("login");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const data = { email, password };

  try {
    const userData = await login(data);
    const roleId = userData.role_id;

    if (roleId === 1) {
      window.location.href = "../managerAdmin.html";
    } else if (roleId === 2) {
      window.location.href = "../teacherPage.html";
    } else if (roleId === 3) {
      window.location.href = "../home.html";
    }
  } catch (error) {
    // Handle login error here
    console.error(error);
  }
});