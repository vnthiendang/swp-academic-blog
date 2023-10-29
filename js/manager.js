import {userList} from './Services/admin.service.js';

const renderUserTable = (users) => {
    const userTable = document.getElementById('userTable');
    userTable.innerHTML = ''; // Clear previous content
  
    // Create table
    const table = document.createElement('table');
    table.classList.add('user-table');
  
    // Create table header
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    const headers = ['Display Name', 'Additional Info', 'Email', 'Created Date', 'Role ID'];
  
    headers.forEach(headerText => {
      const th = document.createElement('th');
      th.textContent = headerText;
      headerRow.appendChild(th);
    });
  
    thead.appendChild(headerRow);
    table.appendChild(thead);
  
    // Create table body
    const tbody = document.createElement('tbody');
  
    users.forEach(user => {
      const row = document.createElement('tr');
  
      const displayNameCell = document.createElement('td');
      displayNameCell.textContent = user.display_name;
      row.appendChild(displayNameCell);
  
      const additionalInfoCell = document.createElement('td');
      additionalInfoCell.textContent = user.additional_info || '-';
      row.appendChild(additionalInfoCell);
  
      const emailCell = document.createElement('td');
      emailCell.textContent = user.email;
      row.appendChild(emailCell);
  
      const createdDateCell = document.createElement('td');
      createdDateCell.textContent = user.created_date || '-';
      row.appendChild(createdDateCell);
  
      const roleIdCell = document.createElement('td');
      roleIdCell.textContent = user.role_id;
      row.appendChild(roleIdCell);
  
      tbody.appendChild(row);
    });
  
    table.appendChild(tbody);
    userTable.appendChild(table);
}
  

userList()
  .then((users) => {
    renderUserTable(users);
});
  