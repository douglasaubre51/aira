console.log('index.js is running ...');


// waguri-api status

console.log('checking waguri status ...')
const waguriStatus = document.getElementById("waguriStatus")
console.log(waguriStatus.innerHTML)
if (waguriStatus.innerHTML == 'live') {
    waguriStatus.style.color = 'lightGreen'
}
else
    waguriStatus.style.color = 'red'

// navigation

let userDetails = document.getElementById('detailsTable')
let createUser = document.getElementById('createUser')
let confirmUser = document.getElementById('confirmUser')
let removeUser = document.getElementById('removeUser')

let detailsTag = document.getElementById("detailsTag")
let createTag = document.getElementById("createTag")
let confirmTag = document.getElementById("confirmTag")
let removeTag = document.getElementById("removeTag")

function gotoUserDetails() {
    userDetails.style.display = 'table'
    createUser.style.display = 'none'
    confirmUser.style.display = 'none'
    removeUser.style.display = 'none'

    detailsTag.style.fontSize = '24px'
    createTag.style.fontSize = '16px'
    confirmTag.style.fontSize = '16px'
    removeTag.style.fontSize = '16px'
}

function gotoCreateUser() {
    userDetails.style.display = 'none'
    createUser.style.display = 'initial'
    confirmUser.style.display = 'none'
    removeUser.style.display = 'none'

    detailsTag.style.fontSize = '16px'
    createTag.style.fontSize = '24px'
    confirmTag.style.fontSize = '16px'
    removeTag.style.fontSize = '16px'
}

function gotoConfirmUser() {
    userDetails.style.display = 'none'
    createUser.style.display = 'none'
    confirmUser.style.display = 'initial'
    removeUser.style.display = 'none'

    detailsTag.style.fontSize = '16px'
    createTag.style.fontSize = '16px'
    confirmTag.style.fontSize = '24px'
    removeTag.style.fontSize = '16px'
}

function gotoRemoveUser() {
    userDetails.style.display = 'none'
    createUser.style.display = 'none'
    confirmUser.style.display = 'none'
    removeUser.style.display = 'initial'

    detailsTag.style.fontSize = '16px'
    createTag.style.fontSize = '16px'
    confirmTag.style.fontSize = '16px'
    removeTag.style.fontSize = '24px'
}

// initial state
gotoUserDetails()