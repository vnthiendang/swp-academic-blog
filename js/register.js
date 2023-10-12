// Get a reference to the form element in your HTML page
const form = document.getElementById("createUser");

// Define the URL of the API endpoint that can handle the user data
const api_url = "http://localhost:8080/blog/auth/register";

// Add an event listener to the form element to handle the submit event
form.addEventListener("submit", async (event) => {
  // Prevent the default behavior of the form, which is to reload the page
  event.preventDefault();

  // Get the values of the input fields from the form element
  const displayName = document.getElementById("display_Name");
  const email = document.getElementById("email");
  const role_id = document.getElementById("role_id");
  const password = document.getElementById("password");
  const passwordConf = document.getElementById("passwordConf");



  // Similarly, check for other input fields such as email, role_id, password, and passwordConf
  // You can use regular expressions or other methods to validate the input values
  // You can also use some libraries or frameworks to simplify the validation process
  // For example, see [How to Validate Forms Using JavaScript] or [Form Validation Using jQuery]

  // Change these lines to use different error messages

  if (!password || password.length < 8) {
    alert("Oops! Your password is not strong enough. Please use at least 8 characters.");
    return;
  }

  if (!passwordConf || passwordConf !== password) {
    alert("Oops! Your passwords do not match. Please re-enter your password correctly.");
    return;
  }

  // If all the input values are valid, create an object that contains the user data
  const user = {
    displayName,
    email,
    role_id,
    password,
    passwordConf
  };

  // Use fetch () to send a POST request to the API with the user data as JSON
  fetch(api_url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  })
    .then(response => response.json()) // Convert the response to JSON
    .then(data => {
      // Do something with the data
      // For example, check if the data contains an error message or a success message
      if (data.error) {
        // Display the error message to the user
        // Change this line to use a different error message
        alert("Sorry! Something went wrong. " + data.error);
      } else {
        // Display the success message to the user and redirect them to another page
        // Change this line to use a different success message
        alert("You have successfully created your account. " + data.message);
        window.location.href = "login.html";
      }
    })
    .catch(error => {
      // Handle any errors
      console.error(error);
      // Change this line to use a different error message
      alert("Sorry! Something went wrong. Please try again later.");
    });
});