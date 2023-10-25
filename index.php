<?php
$files = [
    "index.html",
    "login.html",
    "bestartice.html",
    "home.html",
    "pageByTag.html",
    "blogDetail.html",
    "teacherPage.html",
    "managerAdmin.html",
    "forgotPassword.html",
    "create.html",
    "profile.html",
    "postByCategory.html",
    "createUser.html",
    ""
];

foreach ($files as $file) {
    include_once($file);
}
?>