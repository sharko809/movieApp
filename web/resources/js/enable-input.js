document.getElementById('editName').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('userName').removeAttribute('readonly');
});

document.getElementById('editLogin').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('userLogin').removeAttribute('readonly');
});

document.getElementById('editPassword').addEventListener('click', function (event) {
    event.preventDefault();
    document.getElementById('userPassword').removeAttribute('readonly');
});