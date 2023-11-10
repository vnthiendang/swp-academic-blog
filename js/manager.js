import {approvedPostList, userList, violationList} from './Services/admin.service.js';

const options = {
  month: "short",
  day: "2-digit", 
  hour: "2-digit", 
};

const renderPostTable = (posts) => {
  const tab = document.getElementById('postTable');
  tab.innerHTML = ''; // Clear previous content
  
  // Create table
  const table = document.createElement('table');
  table.classList.add('post-table');
  
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  const headers = ['Post Status', 'Teacher Review', 'Created Date', 'Post'];
  
  headers.forEach(headerText => {
    const th = document.createElement('th');
    th.textContent = headerText;
    headerRow.appendChild(th);
  });
  
  thead.appendChild(headerRow);
  table.appendChild(thead);
  
  // Create table body
  const tbody = document.createElement('tbody');
  
  posts.forEach(post => {
    const row = document.createElement('tr');
  
    const displayNameCell = document.createElement('td');
    displayNameCell.textContent = post.status;
    row.appendChild(displayNameCell);
  
    const additionalInfoCell = document.createElement('td');
    additionalInfoCell.textContent = post.viewedByUser || '-';
    row.appendChild(additionalInfoCell);
  
    const createdDateCell = document.createElement('td');
    const createdTime = new Date(post.createdDate);
    const formattedTime = createdTime.toLocaleString("en-US", options);
    createdDateCell.textContent = formattedTime || '-';
    row.appendChild(createdDateCell);
  
    const roleIdCell = document.createElement('td');
    roleIdCell.textContent = post.post;
    row.appendChild(roleIdCell);
  
    tbody.appendChild(row);
  });
  
  table.appendChild(tbody);
  tab.appendChild(table);
}

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
      const createdTime = new Date(user.created_date);
      const formattedTime = createdTime.toLocaleString("en-US", options);
      createdDateCell.textContent = formattedTime || '-';
      row.appendChild(createdDateCell);
  
      const roleIdCell = document.createElement('td');
      roleIdCell.textContent = user.role_id;
      row.appendChild(roleIdCell);
  
      tbody.appendChild(row);
    });
  
    table.appendChild(tbody);
    userTable.appendChild(table);
}

const renderviolationTable = (users) => {
  const userTable = document.getElementById('violationTable');
  userTable.innerHTML = ''; // Clear previous content
  
  // Create table
  const table = document.createElement('table');
  table.classList.add('violation-table');
  
  // Create table header
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');
  const headers = ['Violation User', 'Expired date', 'Evidence', 'Created Date', 'Violation Type'];
  
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
    displayNameCell.textContent = user.user || '-';
    row.appendChild(displayNameCell);
  
    const additionalInfoCell = document.createElement('td');
    const date = new Date(user.expiredTime);
    const formatted = date.toLocaleString("en-US", options);
    additionalInfoCell.textContent = formatted;
    row.appendChild(additionalInfoCell);
  
    const emailCell = document.createElement('td');
    emailCell.textContent = user.violationEvidence || '-';
    row.appendChild(emailCell);
  
    const createdDateCell = document.createElement('td');
    const createdTime = new Date(user.createdTime);
    const formattedTime = createdTime.toLocaleString("en-US", options);
    createdDateCell.textContent = formattedTime || '-';
    row.appendChild(createdDateCell);
  
    const roleIdCell = document.createElement('td');
    roleIdCell.textContent = user.violationType;
    row.appendChild(roleIdCell);
  
    tbody.appendChild(row);
  });

  table.appendChild(tbody);
  userTable.appendChild(table);
}
  
approvedPostList()
  .then((posts) => {
    renderPostTable(posts);
});

userList()
  .then((users) => {
    renderUserTable(users);
});

violationList()
  .then((user) => {
    renderviolationTable(user);
});
  