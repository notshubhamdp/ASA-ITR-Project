// login.js - Handles login form submission via AJAX

document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    if (!loginForm) return;

    loginForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const formData = new FormData(loginForm);
        fetch('/login', {
            method: 'POST',
            body: formData
        })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                alert('Login successful!');
                window.location.href = '/attendance';
            } else {
                alert(data.message || 'Invalid username or password');
            }
        })
        .catch(() => {
            alert('Login failed. Please try again.');
        });
    });
});
