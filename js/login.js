
import { login, userInfo } from "../js/Services/auth.service.js";

const form = document.getElementById("login");

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const data = { email, password };

  const response = await login(data);

    const roleResponse = await userInfo();

    if (roleResponse && roleResponse.role_id) {
      const userRole = roleResponse.role_id;
      if (userRole == 1) {
        window.location.href = "../managerAdmin.html";
      } else if (userRole == 2) {
        window.location.href = "../teacherPage.html";
      } else {
        // Default redirect if the role is not recognized
        window.location.href = "../home.html";
      }
    } else {
      // Handle the case when the role cannot be fetched
      alert("Unable to determine user role. Please try again.");
    }

});