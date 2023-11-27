import { createRequest } from "./Services/request.service.js";

const urlParams = new URLSearchParams(window.location.search);
const requestedByUserId = urlParams.get('requestedByUserId');
  

const inputElement = document.querySelector('input[readonly]');

inputElement.value = requestedByUserId;

const saveButton = document.getElementById('createRequest');

saveButton.addEventListener('submit', async () => {
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
    } else {
      alert('Failed to send category request.');
    }
  } catch (error) {
    console.error(error);
  }
});