/**
 * Created by dsharko on 8/10/2016.
 */


var formSortLogin = document.getElementById('sortLogin');

document.getElementById('login').addEventListener('click', function () {
   formSortLogin.submit();
});


var formSortID = document.getElementById('sortID');

document.getElementById('id').addEventListener('click', function () {
    formSortID.submit();
});

var formSortName = document.getElementById('sortName');

document.getElementById('userName').addEventListener('click', function () {
    formSortID.submit();
});


var formSortAdmin = document.getElementById('sortAdmin');

document.getElementById('admin').addEventListener('click', function () {
    formSortID.submit();
});

var formSortBanned = document.getElementById('sortBanned');

document.getElementById('banned').addEventListener('click', function () {
    formSortID.submit();
});