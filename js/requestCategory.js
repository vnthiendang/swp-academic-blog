import { createRequest } from "./Services/request.service.js";

const urlParams = new URLSearchParams(window.location.search);
const requestedByUserId = urlParams.get('requestedByUserId');
  

const inputElement = document.querySelector('input[readonly]');

inputElement.value = requestedByUserId;


document.addEventListener("DOMContentLoaded", function() {

  const form = document.getElementById('createRequest');
  form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const detail = document.getElementById("requestDetail").value;

    var model = {
      requestedByUserId: requestedByUserId,
      requestDetail: detail,
      requestTypeId: 1
    };
  
    try {
      const res = await createRequest(model);
  
      if (res != null) {
        alert('Request sent!');
        window.location.href = '/profile.html';
      } else {
        alert('Failed to send category request.');
      }
    } catch (error) {
      console.error(error);
    }

  });
});