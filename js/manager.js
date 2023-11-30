import {
  addAccountViolation,
  approvedPostList,
  userList,
  violationList,
  updatePostStatus,
  violationRuleList,
  deleteViolation,
} from "./Services/admin.service.js";
import {
  getAllAward,
  getAllAwardForAdminPage,
} from "./Services/award.service.js";
import { getAllReports } from "./Services/report.service.js";
import {
  createTeacherCategory,
  getAllTeacherCategory,
  createTeacherCategory2,
} from "./Services/category.service.js";
import { userInfo } from "./Services/auth.service.js";
import { getAllRequests, reviewRequest } from "./Services/request.service.js";

const options = {
  month: "short",
  day: "2-digit",
  hour: "2-digit",
};

const renderPostTable = async (posts) => {
  const tab = document.getElementById("postTable");
  tab.innerHTML = "";
  
  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;

  if (userRole === 'Admin'){
    const table = document.createElement("table");
    table.classList.add("post-table");
  
    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");
    const headers = [
      "Post Status",
      "Teacher Review",
      "Teacher Message",
      "Created Date",
      "Post",
      "Action",
    ];
  
    headers.forEach((headerText) => {
      const th = document.createElement("th");
      th.textContent = headerText;
      headerRow.appendChild(th);
    });
  
    thead.appendChild(headerRow);
    table.appendChild(thead);
  
    // Create table body
    const tbody = document.createElement("tbody");
  
    posts.forEach((post) => {
      const row = document.createElement("tr");
  
      const displayNameCell = document.createElement("td");
      displayNameCell.textContent = post.status;
      row.appendChild(displayNameCell);
  
      const additionalInfoCell = document.createElement("td");
      additionalInfoCell.textContent = post.viewedByUser || "-";
      row.appendChild(additionalInfoCell);

      const teacherMsgCell = document.createElement("td");
      teacherMsgCell.textContent = post.teacherMessage || "-";
      row.appendChild(teacherMsgCell);
  
      const createdDateCell = document.createElement("td");
      const createdTime = new Date(post.createdDate);
      const formattedTime = createdTime.toLocaleString("en-US", options);
      createdDateCell.textContent = formattedTime || "-";
      row.appendChild(createdDateCell);
  
      const postDetailCell = document.createElement("td");
      const postDetailLink = document.createElement("a");
      postDetailLink.textContent = 'view post';
      postDetailLink.href = 'blogDetail.html?belongedToPostID=' + encodeURIComponent(JSON.stringify(post.post));
      postDetailCell.appendChild(postDetailLink);
      row.appendChild(postDetailCell);
  
      const actionCell = document.createElement("td");
  
      const updateStatusBtn = document.createElement("button");
      updateStatusBtn.classList.add("styled-button");
      updateStatusBtn.dataset.id = post.id;
      updateStatusBtn.textContent = "Delete";
      actionCell.appendChild(updateStatusBtn);
  
      row.appendChild(actionCell);
  
      tbody.appendChild(row);
      updateStatusBtn.addEventListener("click", async () => {
        // Display a confirmation popup
        const userConfirmed = window.confirm(
          "Are you sure you want to delete this post approval?"
        );
  
        // Check if the user confirmed the action
        if (!userConfirmed) {
          return; // Do nothing if the user didn't confirm
        }
  
        const id = updateStatusBtn.dataset.id;
        try {
          const response = await fetch(
            `http://localhost:8080/blog/postapproval/${id}`,
            {
              method: "DELETE",
              headers: {
                "Content-Type": "application/json",
                // Add any other headers if needed
              },
            }
          );
  
          if (response.ok) {
            const responseData = await response.text();
            alert(responseData); // Display success message
  
            approvedPostList("approved,rejected").then((posts) => {
              renderPostTable(posts);
            });
          } else if (response.status === 404) {
            alert("PostApprovals not found");
          } else {
            alert("Failed to delete PostApprovals");
          }
        } catch (error) {
          console.error("Error deleting PostApprovals:", error);
        }
      });
    });
  
    table.appendChild(tbody);
    tab.appendChild(table);
  }else{
    tab.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }

};

const renderUserTable = async (users) => {
  const userTable = document.getElementById("userTable");
  userTable.innerHTML = ""; 

  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;
  
  if (userRole === 'Admin'){
    const createUserBtn = document.createElement("button");
    createUserBtn.classList.add("styled-button");
    createUserBtn.textContent = "Create User";
    userTable.appendChild(createUserBtn);
  
    // Create table
    const table = document.createElement("table");
    table.classList.add("user-table");
  
    // Create table header
    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");
    const headers = [
      "Display Name",
      "Additional Info",
      "Email",
      "Created Date",
      "Role ID",
    ];
  
    headers.forEach((headerText) => {
      const th = document.createElement("th");
      th.textContent = headerText;
      headerRow.appendChild(th);
    });
  
    thead.appendChild(headerRow);
    table.appendChild(thead);
  
    // Create table body
    const tbody = document.createElement("tbody");
  
    users.forEach((user) => {
      const row = document.createElement("tr");
  
      const displayNameCell = document.createElement("td");
      displayNameCell.textContent = user.display_name;
      row.appendChild(displayNameCell);
  
      const additionalInfoCell = document.createElement("td");
      additionalInfoCell.textContent = user.additional_info || "-";
      row.appendChild(additionalInfoCell);
  
      const emailCell = document.createElement("td");
      emailCell.textContent = user.email;
      row.appendChild(emailCell);
  
      const createdDateCell = document.createElement("td");
      const createdTime = new Date(user.created_date);
      const formattedTime = createdTime.toLocaleString("en-US", options);
      createdDateCell.textContent = formattedTime || "-";
      row.appendChild(createdDateCell);
  
      const roleIdCell = document.createElement("td");
      roleIdCell.textContent = user.role_id;
      row.appendChild(roleIdCell);
  
      const actionCell = document.createElement("td");
  
      tbody.appendChild(row);
  
      createUserBtn.addEventListener("click", async () => {
        const width = window.innerWidth;
        const height = window.innerHeight;
        const left = 0;
        const top = 0;
        const url = `createUser.html`;
  
        window.open(
          url,
          "Create New User",
          `width=${width}, height=${height}, left=${left}, top=${top}`
        );
      });
    });
  
    table.appendChild(tbody);
    userTable.appendChild(table);
  }else{
    userTable.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }
};

const renderviolationTable = async (users) => {
  const userTable = document.getElementById("violationTable");
  userTable.innerHTML = ""; 

  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;

  if (userRole === 'Admin'){
    const table = document.createElement("table");
    table.classList.add("violation-table");
  
    // Create table header
    const thead = document.createElement("thead");
    const headerRow = document.createElement("tr");
    const headers = [
      "Violation User",
      "Expired date",
      "Evidence",
      "Created Date",
      "Violation Type",
      "Action",
    ];
  
    headers.forEach((headerText) => {
      const th = document.createElement("th");
      th.textContent = headerText;
      headerRow.appendChild(th);
    });
  
    thead.appendChild(headerRow);
    table.appendChild(thead);
  
    // Create table body
    const tbody = document.createElement("tbody");
  
    users.forEach((user) => {
      const row = document.createElement("tr");
  
      const displayNameCell = document.createElement("td");
      displayNameCell.textContent = user.user || "-";
      row.appendChild(displayNameCell);
  
      const additionalInfoCell = document.createElement("td");
      const date = new Date(user.expiredTime);
      const formatted = date.toLocaleString("en-US", options);
      additionalInfoCell.textContent = formatted;
      row.appendChild(additionalInfoCell);
  
      const emailCell = document.createElement("td");
      emailCell.textContent = user.violationEvidence || "-";
      row.appendChild(emailCell);
  
      const createdDateCell = document.createElement("td");
      const createdTime = new Date(user.createdTime);
      const formattedTime = createdTime.toLocaleString("en-US", options);
      createdDateCell.textContent = formattedTime || "-";
      row.appendChild(createdDateCell);
  
      const roleIdCell = document.createElement("td");
      roleIdCell.textContent = user.violationType;
      row.appendChild(roleIdCell);
  
      const removeBtn = document.createElement("button");
      removeBtn.classList.add("styled-button");
      removeBtn.dataset.violatedUser = user.id;
      removeBtn.textContent = "Remove";
      row.appendChild(removeBtn);
  
      tbody.appendChild(row);
  
      removeBtn.addEventListener("click", async () => {
        const obj = removeBtn.dataset.violatedUser;
  
        const confirmed = confirm(
          "Are you sure want to remove this violation record?"
        );
        if (confirmed) {
          try {
            const response = await deleteViolation(obj);
            if (response != null) {
              alert("Account violation Removed");
              // location.reload();
              violationList().then((user) => {
                renderviolationTable(user);
              });
            } else {
              alert("Fail to perform remove violation!");
            }
          } catch (error) {
            console.error("Error removing violation:", error);
          }
        }
      });
    });
  
    table.appendChild(tbody);
    userTable.appendChild(table);
  }else{
    userTable.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }
};

approvedPostList("approved,rejected").then((posts) => {
  renderPostTable(posts);
});

userList().then((users) => {
  renderUserTable(users);
});

violationList().then((user) => {
  renderviolationTable(user);
});

// MANAGE AWARD
const displayAwards = async () => {
  const awardTable = document.getElementById("awardTable");
  awardTable.innerHTML = ""; 

  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;

  if (userRole === 'Admin'){
    try {
      const table = document.createElement("table");
      table.classList.add("award-table");
  
      // Create table header
      const thead = document.createElement("thead");
      const headerRow = document.createElement("tr");
      const headers = ["Post", "Given By Teacher", "Award", "Action"];
  
      headers.forEach((headerText) => {
        const th = document.createElement("th");
        th.textContent = headerText;
        headerRow.appendChild(th);
      });
  
      thead.appendChild(headerRow);
      table.appendChild(thead);
  
      // Create table body
      const tbody = document.createElement("tbody");
      const awards = await getAllAwardForAdminPage();
  
      awards.forEach((award) => {
        const row = document.createElement("tr");
  
        row.classList.add("award-row");
  
        const postAward = document.createElement("td");
        postAward.textContent = award.post;
        row.appendChild(postAward);
  
        const givenBy = document.createElement("td");
        givenBy.textContent = award.givenByUser;
        row.appendChild(givenBy);
  
        const awardType = document.createElement("td");
        awardType.textContent = award.awardType;
        row.appendChild(awardType);
  
        const actionCell = document.createElement("td");
  
        const updateAwardBtn = document.createElement("button");
        updateAwardBtn.classList.add("styled-button");
        updateAwardBtn.dataset.id = award.id;
        updateAwardBtn.textContent = "Delete Award";
        actionCell.appendChild(updateAwardBtn);
  
        row.appendChild(actionCell);
  
        tbody.appendChild(row);
  
        updateAwardBtn.addEventListener("click", async () => {
          // Display a confirmation popup
          const userConfirmed = window.confirm(
            "Are you sure you want to delete this award?"
          );
  
          // Check if the user confirmed the action
          if (!userConfirmed) {
            return; // Do nothing if the user didn't confirm
          }
  
          const id = updateAwardBtn.dataset.id;
          try {
            const response = await fetch(
              `http://localhost:8080/blog/award/${id}`,
              {
                method: "DELETE",
                headers: {
                  "Content-Type": "application/json",
                  // Add any other headers if needed
                },
              }
            );
  
            if (response.ok) {
              const responseData = await response.text();
              alert(responseData); // Display success message
  
              displayAwards();
            } else if (response.status === 404) {
              alert("Award not found");
            } else {
              alert("Failed to delete award");
            }
          } catch (error) {
            console.error("Error deleting Award:", error);
          }
        });
      });
      table.appendChild(tbody);
      awardTable.appendChild(table);
    } catch (error) {
      console.error("Error retrieving awards:", error);
    }
  }else{
    awardTable.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }
};

displayAwards();

// MANAGE Report
const displayReports = async () => {
  const awardTable = document.getElementById("reportTable");
  awardTable.innerHTML = ""; 

  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;

  if (userRole === 'Admin'){
    try {
      const table = document.createElement("table");
      table.classList.add("report-table");
  
      // Create table header
      const thead = document.createElement("thead");
      const headerRow = document.createElement("tr");
      const headers = [
        "Report Type",
        "By",
        "Detail",
        "Status",
        "Violation Rule",
        "Evidence",
        "Action",
      ];
  
      headers.forEach((headerText) => {
        const th = document.createElement("th");
        th.textContent = headerText;
        headerRow.appendChild(th);
      });
  
      thead.appendChild(headerRow);
      table.appendChild(thead);
  
      // Create table body
      const tbody = document.createElement("tbody");
      const reports = await getAllReports();
  
      reports.forEach((report) => {
        const row = document.createElement("tr");
  
        row.classList.add("award-row");
  
        const reportType = document.createElement("td");
        reportType.textContent = report.reportType;
        row.appendChild(reportType);
  
        const reportBy = document.createElement("td");
        reportBy.textContent = report.reportedByUser;
        row.appendChild(reportBy);
  
        const detail = document.createElement("td");
        detail.textContent = report.reportDetail;
        row.appendChild(detail);
  
        const status = document.createElement("td");
        status.textContent = report.status || "Pending";
        row.appendChild(status);
  
        const violationRule = document.createElement("td");
        violationRule.textContent = report.violationRuleList.join(", ");
        row.appendChild(violationRule);
  
        const reportObjLink = document.createElement("td");
        const objLink = document.createElement("a");
        objLink.href = `/blogDetail.html?postId=${report.reportedObjectLink}`;
        objLink.textContent = "Report Evidence";
        reportObjLink.appendChild(objLink);
        row.appendChild(reportObjLink);
  
        const actionCell = document.createElement("td");
  
        const updateReportBtn = document.createElement("button");
        updateReportBtn.classList.add("styled-button");
        updateReportBtn.textContent = "Update";
        actionCell.appendChild(updateReportBtn);
  
        const addViolationBtn = document.createElement("button");
        addViolationBtn.classList.add("styled-button");
        addViolationBtn.dataset.reportedObjectLink = report.reportedObjectLink;
        addViolationBtn.textContent = "Add Violation";
        actionCell.appendChild(addViolationBtn);
  
        const dropdown = document.createElement("select");
        dropdown.innerHTML = "<option value>Choose Violation Type</option>";
  
        const populateDropdownOptions = async () => {
          try {
            const response = await violationRuleList();
            const violationRules = response;
  
            violationRules.forEach((rule) => {
              const option = document.createElement("option");
              option.value = rule.id;
              option.textContent = rule.violationRuleInfo;
              dropdown.appendChild(option);
            });
          } catch (error) {
            console.error("Error fetching violation rules:", error);
            // Handle error
          }
        };
  
        populateDropdownOptions().then(() => {
          actionCell.appendChild(dropdown);
        });
  
        row.appendChild(actionCell);
  
        tbody.appendChild(row);
  
        addViolationBtn.addEventListener("click", async () => {
          const obj = addViolationBtn.dataset.reportedObjectLink;
  
          if (dropdown.value) {
            try {
              const model = {
                userId: obj,
                violation_type: dropdown.value,
              };
              const response = await addAccountViolation(model);
              if (response != null) {
                alert("Account violation Added");
                location.reload();
              } else {
                alert("Fail to perform add violation!");
              }
            } catch (error) {
              console.error("Error adding violation:", error);
            }
          } else {
            alert("Must choose violation type first!");
          }
        });
      });
      table.appendChild(tbody);
      awardTable.appendChild(table);
    } catch (error) {
      console.error("Error retrieving reports:", error);
    }
  }else{
    awardTable.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }

};

displayReports();

// MANAGE Teacher Category
const displayTeacherCategory = async () => {
  const awardTable = document.getElementById("categoryTable");
  awardTable.innerHTML = "";

  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;

  if (userRole === 'Admin'){
    try {
      const table = document.createElement("table");
      table.classList.add("category-table");
  
      // Create table header
      const thead = document.createElement("thead");
      const headerRow = document.createElement("tr");
      const headers = ["Teacher", "Category", "Action"];
  
      headers.forEach((headerText) => {
        const th = document.createElement("th");
        th.textContent = headerText;
        headerRow.appendChild(th);
      });
  
      thead.appendChild(headerRow);
      table.appendChild(thead);
  
      // Create table body
      const tbody = document.createElement("tbody");
      const reports = await getAllTeacherCategory();
  
      reports.forEach((report) => {
        const row = document.createElement("tr");
  
        const teacher = document.createElement("td");
        teacher.textContent = report.teacher;
        row.appendChild(teacher);
  
        const cateTeacher = document.createElement("td");
        cateTeacher.textContent = report.category;
        row.appendChild(cateTeacher);
  
        const actionCell = document.createElement("td");
  
        const updateCateBtn = document.createElement("button");
        updateCateBtn.classList.add("styled-button");
        updateCateBtn.dataset.id = report.id;
        updateCateBtn.textContent = "Delete";
        actionCell.appendChild(updateCateBtn);
  
        // const createBtn = document.createElement("button");
        // createBtn.classList.add("styled-button");
        // createBtn.textContent = "Create";
        // actionCell.appendChild(createBtn);
  
        row.appendChild(actionCell);
  
        tbody.appendChild(row);
  
        updateCateBtn.addEventListener("click", async () => {
          // Display a confirmation popup
          const userConfirmed = window.confirm(
            "Are you sure you want to delete this category management?"
          );
  
          // Check if the user confirmed the action
          if (!userConfirmed) {
            return; // Do nothing if the user didn't confirm
          }
  
          const id = updateCateBtn.dataset.id;
          try {
            const response = await fetch(
              `http://localhost:8080/blog/categoryManagement/${id}`,
              {
                method: "DELETE",
                headers: {
                  "Content-Type": "application/json",
                  // Add any other headers if needed
                },
              }
            );
  
            if (response.ok) {
              const responseData = await response.text();
              alert(responseData); // Display success message
  
              displayTeacherCategory();
            } else if (response.status === 404) {
              alert("Category Management not found");
            } else {
              alert("Failed to delete category management");
            }
          } catch (error) {
            console.error("Error deleting Category Management:", error);
          }
        });
      });
  
      table.appendChild(tbody);
      awardTable.appendChild(table);
    } catch (error) {
      console.error("Error retrieving teacher categories:", error);
    }
  }else{
    awardTable.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }


};

displayTeacherCategory();

// MANAGE Category Request
const displayCategoryRequest = async () => {
  const requestTable = document.getElementById("requestTable");
  requestTable.innerHTML = "";

  const usersInfo = await userInfo();
  const userRole = usersInfo.role_id;

  if (userRole === 'Admin'){
    try {
      const table = document.createElement("table");
      table.classList.add("request-table");
  
      // Create table header
      const thead = document.createElement("thead");
      const headerRow = document.createElement("tr");
      const headers = ["Request Type", "Teacher", "Detail", "Status", "Action"];
  
      headers.forEach((headerText) => {
        const th = document.createElement("th");
        th.textContent = headerText;
        headerRow.appendChild(th);
      });
  
      thead.appendChild(headerRow);
      table.appendChild(thead);
  
      // Create table body
      const tbody = document.createElement("tbody");
      const requests = await getAllRequests();
  
      requests.forEach((request) => {
        const row = document.createElement("tr");
  
        const type = document.createElement("td");
        type.textContent = request.requestType;
        row.appendChild(type);
  
        const byUser = document.createElement("td");
        byUser.textContent = request.requestedByUser;
        row.appendChild(byUser);
  
        const detail = document.createElement("td");
        detail.textContent = request.requestDetail;
        row.appendChild(detail);
  
        const status = document.createElement("td");
        status.textContent = request.status;
        row.appendChild(status);
  
        const actionCell = document.createElement("td");
  
        const approveBtn = document.createElement("button");
        approveBtn.classList.add("styled-button");
        approveBtn.dataset.requestId = request.id;
        approveBtn.textContent = "Approve";
        actionCell.appendChild(approveBtn);
  
        const rejectBtn = document.createElement("button");
        rejectBtn.classList.add("styled-button");
        rejectBtn.dataset.requestId = request.id;
        rejectBtn.textContent = "Reject";
        actionCell.appendChild(rejectBtn);
  
        const addCategoryBtn = document.createElement("button");
        addCategoryBtn.classList.add("styled-button");
        addCategoryBtn.dataset.teacher = request.requestedByUser;
        addCategoryBtn.textContent = "Add category";
        actionCell.appendChild(addCategoryBtn);
  
        // Check the request status and hide buttons accordingly
        if (
          request.status.toLowerCase() === "approved" ||
          request.status.toLowerCase() === "rejected"
        ) {
          approveBtn.style.display = "none";
          rejectBtn.style.display = "none";
          addCategoryBtn.style.display = "none";
        }
  
        row.appendChild(actionCell);
  
        tbody.appendChild(row);
  
        approveBtn.addEventListener("click", async () => {
          const requestId = approveBtn.dataset.requestId;
          const userInfos = await userInfo();
  
          try {
            const model = {
              reviewedByAdminId: userInfos.userId,
              adminMessage: "approved",
              status: "approved",
            };
            const response = await reviewRequest(requestId, model);
            if (response != null) {
              alert("Teacher Category Approved!");
              displayCategoryRequest();
            } else {
              alert("Fail to perform!");
            }
          } catch (error) {
            console.error("Error review requests:", error);
          }
        });
  
        rejectBtn.addEventListener("click", async () => {
          const requestId = approveBtn.dataset.requestId;
          const userInfos = await userInfo();
  
          try {
            const model = {
              reviewedByAdminId: userInfos.userId,
              adminMessage: "",
              status: "rejected",
            };
            const response = await reviewRequest(requestId, model);
            if (response != null) {
              alert("Teacher Category Rejected!");
              displayCategoryRequest();
            } else {
              alert("Fail to perform!");
            }
          } catch (error) {
            console.error("Error review requests:", error);
          }
        });
  
        addCategoryBtn.addEventListener("click", async () => {
          const teacherId = request.teacherId;
          console.log(teacherId);
  
          const categoryName = window.prompt("Enter category:");
          console.log(categoryName);
  
          // Check if the user clicked OK in the prompt
          if (categoryName !== null) {
            try {
              const model = {
                teacherId: teacherId,
                categoryName: categoryName,
              };
  
              const response = await createTeacherCategory2(model);
  
              if (response.ok) {
                const contentType = response.headers.get("content-type");
                if (contentType && contentType.includes("application/json")) {
                  const jsonResponse = await response.json();
                  alert("Teacher Category Added!");
                  // Handle jsonResponse if needed
                } else {
                  const textResponse = await response.text();
                  alert(
                    `Teacher Category Added! Server response: ${textResponse}`
                  );
                }
              } else if (response.status === 400) {
                const errorText = await response.text();
                alert(`Invalid request parameters: ${errorText}`);
              } else if (response.status === 500) {
                const errorText = await response.text();
                alert(`Internal Server Error: ${errorText}`);
              } else {
                alert("Fail to add category!");
              }
            } catch (error) {
              console.error("Error add category:", error);
            }
          }
        });
      });
      table.appendChild(tbody);
      requestTable.appendChild(table);
    } catch (error) {
      console.error("Error:", error);
    }
  }else{
    requestTable.innerHTML += `
    <h2>Unauthorized</h2>
    <p>You are not authorized to access this page.</p>
    `;
  }

};

displayCategoryRequest();
