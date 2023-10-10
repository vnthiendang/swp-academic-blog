// Get the form element by its id
const form = document.getElementById("createUser");

// Add an event listener for the submit event
form.addEventListener("submit", async (event) => {
  // Prevent the default behavior of the form, which is to reload the page
  event.preventDefault();

  // Get the username, email, password, and role values from the input elements
  const username = document.getElementById("displayName").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  const passwordConf = document.getElementById("passwordConf").value;
  const role = document.getElementById("role_id").value;

  // Check if the password and password confirmation match
  if (password !== passwordConf) {
    // Display an error message if they don't match
    var message = document.querySelector(".form__message");
    message.textContent = "Password and password confirmation do not match.";
    message.style.color = "red";
    return;
  }

  // Create an object with the username, email, password, and role as properties
  const data = { username, email, password, role };

  // Use the Fetch API to send a POST request to the server with the data as JSON
  try {
    const response = await fetch("https://localhost:8080/blog/auth/register", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    // Check if the response status is OK (200)
    if (response.ok) {
      // Parse the response body as JSON and get the message
      const { message } = await response.json();

      // Display a success message in an alert box
      alert(message);

      // Redirect to the login page or dashboard
      window.location.href = "/login.html";
    } else {
      // If the response status is not OK, throw an error with the status text
      throw new Error(response.statusText);
    }
  } catch (error) {
    // If there is an error, display it in the form message element
    var message = document.querySelector(".form__message");
    message.textContent = error.message;
    message.style.color = "red";
  }
});
