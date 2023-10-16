// Get the form element by its id
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

  // Use the Fetch API to send a POST request to the server with the data as JSON
  try {
    const response = await fetch("http://localhost:8080/blog/auth/authenticate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    // Check if the response status is OK (200)
    if (response.ok) {
      // Parse the response body as JSON and get the token
      const { token } = await response.json();

      // Store the token in the local storage or cookie
      localStorage.setItem("token", token);

      // Redirect to the home page or dashboard
      window.location.href = "/home.html";
    } else {
      // If the response status is not OK, throw an error with the status text
      throw new Error(response.statusText);
    }
  } catch (error) {
    // If there is an error, display it in an alert box
    alert(error.message);
  }
});