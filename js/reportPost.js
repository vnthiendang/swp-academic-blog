import { violationRuleList } from "./Services/admin.service.js";
import { userInfo } from "./Services/auth.service.js";
import { createReport } from "./Services/report.service.js";

const urlParams = new URLSearchParams(window.location.search);
const postId = urlParams.get('postId');
const checkedCheckboxValues = [];
let inputElement;

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

    // Create the label for the single textbox
    const labelElement = document.createElement('label');
    labelElement.htmlFor = 'singleTextbox';
    labelElement.textContent = 'Message:';
    labelElement.style.marginRight = '10px';
    formContainerElement.appendChild(labelElement);

    // Create a single textbox
    inputElement = document.createElement('input');
    inputElement.type = 'text';
    inputElement.id = 'singleTextbox';
    inputElement.name = 'singleTextbox';
    inputElement.className = 'textbox-input';
    formContainerElement.appendChild(inputElement);

    // Iterate over each violation rule and populate the HTML elements
    violationRules.forEach((rule, index) => {
      // Create the h2 element inside the column
      const h2Element = document.createElement('h2');

      // Create a checkbox for each violation rule
      const checkboxElement = document.createElement('input');
      checkboxElement.type = 'checkbox';
      checkboxElement.id = `checkbox${index + 1}`;
      checkboxElement.name = `checkbox${index + 1}`;
      checkboxElement.className = 'checkbox-input';

      // Create a label for the checkbox
      const labelForCheckbox = document.createElement('label');
      labelForCheckbox.htmlFor = `checkbox${index + 1}`;
      labelForCheckbox.textContent = rule.violationRuleInfo;

      // Create the icon element
      const iconElement = document.createElement('i');
      iconElement.classList.add('fa-solid', 'fa-chevron-right', 'fa-lg');
      iconElement.style.color = '#000000';
      iconElement.style.marginLeft = '1%';

      // Append the checkbox, label, and icon to the h2 element
      h2Element.appendChild(checkboxElement);
      h2Element.appendChild(labelForCheckbox);
      h2Element.appendChild(iconElement);

      // Append the h2 element to the column
      columnElement.appendChild(h2Element);

      // Add event listener to the checkbox
      // checkboxElement.addEventListener('change', () => {
      //   const checkboxElements = document.getElementById(`checkbox${index + 1}`);
      //   if (checkboxElements.checked) {
      //     checkedCheckboxValues.push(rule.violationRuleInfo);
      //   }

      //   // if (checkboxElement.checked) {
      //   //   checkedCheckboxValues.push(rule.violationRuleInfo);
      //   // } 
      //   // else {
      //   //   const index = checkedCheckboxValues.indexOf(rule.violationRuleInfo);
      //   //   if (index > -1) {
      //   //     checkedCheckboxValues.splice(index, 1);
      //   //   }
      //   // }
      // });

      checkboxElement.addEventListener('change', () => {
        if (checkboxElement.checked) {
          checkedCheckboxValues.push(rule.violationRuleInfo);
        } else {
          const index = checkedCheckboxValues.indexOf(rule.violationRuleInfo);
          if (index > -1) {
            checkedCheckboxValues.splice(index, 1);
          }
        }
      });

    });
  } catch (error) {
    console.error('Error displaying violation rules:', error);
    // Handle error display or logging as needed
  }
};

displayViolationRules();

const reportBtn = document.getElementById('reportBtn');

reportBtn.addEventListener('click', async (event) => {
  event.preventDefault();

  // const detail = document.getElementById(singleTextbox);
  const userInfos = await userInfo();
  const usId = userInfos.userId;

  var model = {
    reportTypeId: 1,  
    postId: parseInt(postId),
    violationRuleList: checkedCheckboxValues,
    reportDetail: inputElement.value,
    reportedByUserId: usId
  };
  const response = await createReport(model);

  if (response == null) {
    alert('Fail to report!');
  } else {
    alert('Post reported!');
    window.location.href = '../home.html';
  }
});