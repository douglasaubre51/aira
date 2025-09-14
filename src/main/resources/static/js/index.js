console.log("index.js is running ...");

// nav btn toggle webview

const userNavBtn = document.getElementById("users-view");
const projectNavBtn = document.getElementById("projects-view");

const mainView = document.getElementById("main-view");

const addPersonBtn = document.getElementById("add-person-btn");
const addProjectBtn = document.getElementById("add-project-btn");

const airaBaseUrI = window.location.href;

userNavBtn.onclick = () => {
  projectNavBtn.classList.remove("nav-btn-active");
  addProjectBtn.style.display = "none";

  userNavBtn.classList.add("nav-btn-active");
  mainView.setAttribute("src", airaBaseUrI + "user/view");
  addPersonBtn.style.display = "initial";
};

projectNavBtn.onclick = () => {
  userNavBtn.classList.remove("nav-btn-active");
  addPersonBtn.style.display = "none";

  projectNavBtn.classList.add("nav-btn-active");
  mainView.setAttribute("src", airaBaseUrI + "project/all");
  addProjectBtn.style.display = "initial";
};
