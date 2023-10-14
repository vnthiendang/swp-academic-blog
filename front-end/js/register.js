// Get a reference to the form element in your HTML page
const form = document.getElementById("createUser");

// Define the URL of the API endpoint that can handle the user data
const api_url = "http://localhost:8080/blog/auth/register";

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

  if (password !== passwordConf) {
    alert("Oops! Your passwords do not match. Please re-enter your password correctly.");
    return;
}

    // Create the user object
    const user = {
      displayName,
      email,
      password,
      passwordConf
  };
    // Send the user data to the server
    try {
      const response = await fetch(api_url, {
          method: "POST",
          headers: {
              "Content-Type": "application/json"
          },
          body: JSON.stringify(user)
      });

      // Check if the response is empty
      if (!response.ok) {
          throw new Error("Failed to create the user.");
      }

      const data = await response.json();

      // Handle the response data
      if (data.error) {
          alert("Sorry! Something went wrong. " + data.error);
      } else {
          alert("You have successfully created your account. " + data.message);
          window.location.href = "login.html";
      }
  } catch (error) {
      console.error(error);
      alert("Sorry! Something went wrong. Please try again later.");
  }
});
