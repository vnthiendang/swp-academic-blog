import { violationRuleList } from "./Services/admin.service.js";
import { createReport } from "./Services/report.service.js";

const urlParams = new URLSearchParams(window.location.search);
const postId = urlParams.get('postId');
  
  // Function to display the violation rule list in the HTML
  const displayViolationRules = async () => {
    try {
      // Fetch the violation rule list
      const violationRules = await violationRuleList();
  
      // Find the column element
      const columnElement = document.querySelector('.column');
  
      // Find the form container element
      const formContainerElement = document.querySelector('#formContainer');
  
      // Clear existing content
      columnElement.innerHTML = '';
      formContainerElement.innerHTML = '';
  
      // Iterate over each violation rule and populate the HTML elements
      violationRules.forEach((rule, index) => {
        // Create the h2 element inside the column
        const h2Element = document.createElement('h2');
        h2Element.textContent = rule.violationRuleInfo;

        // Create the icon element
        const iconElement = document.createElement('i');
        iconElement.classList.add('fa-solid', 'fa-chevron-right', 'fa-lg');
        iconElement.style.color = '#000000';
        iconElement.style.marginLeft = '1%';

        // Append the icon to the h2 element
        h2Element.appendChild(iconElement);
          
        // Append the h2 element to the column
        columnElement.appendChild(h2Element);
  
        // Create the form
        const formElement = document.createElement('form');
  
        // Create the label and input elements
        const labelElement = document.createElement('label');
        labelElement.htmlFor = `fname${index + 1}`;
        labelElement.textContent = 'Message:';
        labelElement.style.display.replace
        labelElement.style.marginRight = "10px";
  
        const inputElement = document.createElement('input');
        inputElement.type = 'text';
        inputElement.id = `fname${index + 1}`;
        inputElement.name = `fname${index + 1}`;
        inputElement.className = 'textbox-input';
  
        // Append the label and input elements to the form
        formElement.appendChild(labelElement);
        formElement.appendChild(inputElement);
  
        // Append the form to the form container
        formContainerElement.appendChild(formElement);
      });
    } catch (error) {
      console.error('Error displaying violation rules:', error);
      // Handle error display or logging as needed
    }
  };
  
  displayViolationRules();

const reportBtn = document.getElementById("reportBtn");

reportBtn.addEventListener("click", async (event) => {
    event.preventDefault();
  
    var model = {
      reportTypeId: 1,
      postId: parseInt(postId),
      // violationRuleList,
      // reportDetail
    }
    const response = await createReport(model);
  
    if (response == null) {
      alert("Fail to report!");
    } else {
      alert("Post reported!");
      window.location.href = "../home.html";
    }
});