/**
 * Created by dsharko on 8/9/2016.
 */

function setLoginInputs(login) {
    document.getElementById('userName').value = login;
}

function setRegistrationInputs (name, email) {
    document.getElementById('newUserName').value = name;
    document.getElementById('newUserLogin').value = email;
}