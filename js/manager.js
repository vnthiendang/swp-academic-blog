import {addAccountViolation, approvedPostList, userList, violationList, updatePostStatus} from './Services/admin.service.js';
import { getAllAward } from "./Services/award.service.js";
import { getAllReports } from "./Services/report.service.js";
import { getAllTeacherCategory } from "./Services/category.service.js";
import { userInfo } from "./Services/auth.service.js";

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
  const headers = ['Post Status', 'Teacher Review', 'Created Date', 'Post', 'Action'];
  
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

    const actionCell = document.createElement('td');

    const updateStatusBtn = document.createElement('button');
    updateStatusBtn.classList.add('styled-button');
    updateStatusBtn.dataset.postId = post.post;
    updateStatusBtn.textContent = 'Update Status';
    actionCell.appendChild(updateStatusBtn);

    row.appendChild(actionCell);
  
    tbody.appendChild(row);

    updateStatusBtn.addEventListener('click', async () => {
      const postId = updateStatusBtn.dataset.postId;
      console.log(postId);
      const userInfos = await userInfo();
      try {
        const model = {
          viewedByUser: userInfos.userId,
          postApprovalsStatus: 'APPROVED'
        };
        const response = await updatePostStatus(postId, model);
        if(response != null){
          alert('Status changed');
        }else{
          alert('Fail to perform!');
        }
      } catch (error) {
        console.error('Error change status:', error);
      }
    });
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
    const headers = ['Display Name', 'Additional Info', 'Email', 'Created Date', 'Role ID', 'Action'];
  
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

      const actionCell = document.createElement('td');

      const addViolationButton = document.createElement('button');
      addViolationButton.classList.add('styled-button');
      addViolationButton.dataset.userId = user.userId;
      addViolationButton.textContent = 'Add violation';
      actionCell.appendChild(addViolationButton);

      const createUserBtn = document.createElement('button');
      createUserBtn.classList.add('styled-button');
      createUserBtn.textContent = 'Create User';
      actionCell.appendChild(createUserBtn);

      row.appendChild(actionCell);
  
      tbody.appendChild(row);

      addViolationButton.addEventListener('click', async () => {
        const userId = addViolationButton.dataset.userId;
        try {
          const model = {
            userId: userId,
            violation_type: 2
          };
          const response = await addAccountViolation(model);
          if(response != null){
            alert('Account violation Added');
          }else{
            alert('Fail to perform add violation!');
          }
        } catch (error) {
          console.error('Error adding violation:', error);
        }
      });

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


// MANAGE AWARD
const displayAwards = async () => {
  const awardTable = document.getElementById('awardTable');

  try {
    const table = document.createElement('table');
    table.classList.add('award-table');
    
    // Create table header
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    const headers = ['Post', 'Given By Teacher', 'Award', 'Action'];
    
    headers.forEach(headerText => {
      const th = document.createElement('th');
      th.textContent = headerText;
      headerRow.appendChild(th);
    });
    
    thead.appendChild(headerRow);
    table.appendChild(thead);
    
    // Create table body
    const tbody = document.createElement('tbody');
    const awards = await getAllAward();

    awards.forEach(award => {
      const row = document.createElement('tr');

      row.classList.add('award-row');

      const postAward = document.createElement('td');
      postAward.textContent = award.post;
      row.appendChild(postAward);

      const givenBy = document.createElement('td');
      givenBy.textContent = award.givenByUser;
      row.appendChild(givenBy);

      const awardType = document.createElement('td');
      awardType.textContent = award.awardType;
      row.appendChild(awardType);

      const actionCell = document.createElement('td');

      const updateAwardBtn = document.createElement('button');
      updateAwardBtn.classList.add('styled-button');
      //updateAwardBtn.dataset.userId = user.userId;
      updateAwardBtn.textContent = 'Update award';
      actionCell.appendChild(updateAwardBtn);

      const createBtn = document.createElement('button');
      createBtn.classList.add('styled-button');
      createBtn.textContent = 'Create';
      actionCell.appendChild(createBtn);

      row.appendChild(actionCell);

      tbody.appendChild(row);
    });
    table.appendChild(tbody);
    awardTable.appendChild(table);
  } catch (error) {
    console.error('Error retrieving awards:', error);
  }
};

displayAwards();

// MANAGE Report
const displayReports = async () => {
  const awardTable = document.getElementById('reportTable');

  try {
    const table = document.createElement('table');
    table.classList.add('report-table');
    
    // Create table header
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    const headers = ['Report Type', 'Reported By', 'Detail', 'Violation Rule', 'Action'];
    
    headers.forEach(headerText => {
      const th = document.createElement('th');
      th.textContent = headerText;
      headerRow.appendChild(th);
    });
    
    thead.appendChild(headerRow);
    table.appendChild(thead);
    
    // Create table body
    const tbody = document.createElement('tbody');
    const reports = await getAllReports();

    reports.forEach(report => {
      const row = document.createElement('tr');

      row.classList.add('award-row');

      const reportType = document.createElement('td');
      reportType.textContent = report.reportType;
      row.appendChild(reportType);

      const reportBy = document.createElement('td');
      reportBy.textContent = report.reportedByUser;
      row.appendChild(reportBy);

      const detail = document.createElement('td');
      detail.textContent = report.reportDetail;
      row.appendChild(detail);

      const violationRule = document.createElement('td');
      violationRule.textContent = report.violationRuleList.join(', ');
      row.appendChild(violationRule);

      const actionCell = document.createElement('td');

      const updateReportBtn = document.createElement('button');
      updateReportBtn.classList.add('styled-button');
      updateReportBtn.textContent = 'Update';
      actionCell.appendChild(updateReportBtn);

      const createBtn = document.createElement('button');
      createBtn.classList.add('styled-button');
      createBtn.textContent = 'Create';
      actionCell.appendChild(createBtn);

      row.appendChild(actionCell);

      tbody.appendChild(row);
    });
    table.appendChild(tbody);
    awardTable.appendChild(table);
  } catch (error) {
    console.error('Error retrieving reports:', error);
  }
};

displayReports();


// MANAGE Teacher Category
const displayTeacherCategory = async () => {
  const awardTable = document.getElementById('categoryTable');

  try {
    const table = document.createElement('table');
    table.classList.add('category-table');
    
    // Create table header
    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');
    const headers = ['Teacher', 'Category', 'Action'];
    
    headers.forEach(headerText => {
      const th = document.createElement('th');
      th.textContent = headerText;
      headerRow.appendChild(th);
    });
    
    thead.appendChild(headerRow);
    table.appendChild(thead);
    
    // Create table body
    const tbody = document.createElement('tbody');
    const reports = await getAllTeacherCategory();

    reports.forEach(report => {
      const row = document.createElement('tr');

      const teacher = document.createElement('td');
      teacher.textContent = report.teacher;
      row.appendChild(teacher);

      const cateTeacher = document.createElement('td');
      cateTeacher.textContent = report.category;
      row.appendChild(cateTeacher);

      const actionCell = document.createElement('td');

      const updateCateBtn = document.createElement('button');
      updateCateBtn.classList.add('styled-button');
      updateCateBtn.textContent = 'Update';
      actionCell.appendChild(updateCateBtn);

      const createBtn = document.createElement('button');
      createBtn.classList.add('styled-button');
      createBtn.textContent = 'Create';
      actionCell.appendChild(createBtn);

      row.appendChild(actionCell);

      tbody.appendChild(row);
    });
    table.appendChild(tbody);
    awardTable.appendChild(table);
  } catch (error) {
    console.error('Error retrieving teacher categories:', error);
  }
};

displayTeacherCategory();
  