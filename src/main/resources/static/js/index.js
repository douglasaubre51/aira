console.log('index.js is running ...');

const userNavBtn = document.getElementById('users-view')
const projectNavBtn = document.getElementById('projects-view')
const mainView = document.getElementById('main-view')
const airaBaseUrI = window.location.href
// @ts-ignore
userNavBtn.onclick = () => {
    console.log('clicked users-view!')

    projectNavBtn?.classList.remove('nav-btn-active')
    userNavBtn?.classList.add('nav-btn-active')
    mainView?.setAttribute('src', airaBaseUrI + 'user/view')
}
// @ts-ignore
projectNavBtn.onclick = () => {
    console.log('clicked projects-view!')

    userNavBtn?.classList.remove('nav-btn-active')
    projectNavBtn?.classList.add('nav-btn-active')
    mainView?.setAttribute('src', airaBaseUrI + 'projects')
}