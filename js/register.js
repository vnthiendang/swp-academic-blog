import { register } from "../js/Services/auth.service.js";

// Get a reference to the form element in your HTML page
const form = document.getElementById("createUser");

// Add an event listener to the form element to handle the submit event
form.addEventListener("submit", async (event) => {
  // Prevent the default behavior of the form, which is to reload the page
  event.preventDefault();

    // Get the input field values
    const displayName = document.getElementById("display_Name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const passwordConf = document.getElementById("passwordConf").value;

    //validate inputs
  if (!password || password.length < 8) {
    alert("Oops! Your password is not strong enough. Please use at least 8 characters.");
    return;
  }

  if (password != passwordConf) {
    alert("Oops! Your passwords do not match. Please re-enter your password correctly.");
    return;
}

    // Create the user object
    const user = {
      displayName,
      email,
      password
    };

  const response = await register(user);
      // Handle the response data
      if (response == null) {
        alert("Sorry! Please check your information! ");
      } else {
        alert("You have successfully created your account. ");
        window.location.href = "login.html";
      }

  });
