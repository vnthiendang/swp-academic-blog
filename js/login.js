
import { login } from "../js/Services/auth.service.js";

const form = document.getElementById("login");

// Add an event listener for the submit event
form.addEventListener("submit", async (event) => {
  // Prevent the default behavior of the form, which is to reload the page
  event.preventDefault();

  // Get the email and password values from the input elements
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  // Create an object with the email and password as properties
  const data = { email, password };

  const response = await login(data);
  // Handle the response data
  if (response && response.error) {
    alert("Sorry! Something went wrong. " + response.error);
  } else {
    window.location.href = "/home.html";
  }

});