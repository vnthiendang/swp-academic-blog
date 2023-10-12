const token = localStorage.getItem("token");

const categoryList = document.getElementById("categoryList");

// Fetch categories
fetch("http://localhost:8080/blog/category", {
  headers: {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json"
  }
})
  .then(response => {
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    return response.json();
  })
  .then(data => {
    // Loop through categories and create list items
    data.forEach(category => {
      const listItem = document.createElement("li");
      listItem.className = "w-full";
      
      const link = document.createElement("a");
      link.className = "rounded-b bg-black hover:bg-gray-400 py-2 px-4 block whitespace-no-wrap text-white";
      link.href = `/${category.content}`;
      link.textContent = category.content;
      
      listItem.appendChild(link);
      categoryList.appendChild(listItem);
    });
  })
  .catch(error => {
    console.error("Error:", error);
    // Access the error data
    if (error.response) {
      console.log("Response status:", error.response.status);
      console.log("Response body:", error.response.data);
    }
    // Handle the error
  });