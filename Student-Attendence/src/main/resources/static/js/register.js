// register.js - Handles registration and Gmail OTP via AJAX

document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');
    const generateOtpBtn = document.getElementById('generateOtpBtn');
    const verifyOtpBtn = document.getElementById('verifyOtpBtn');
    const otpSection = document.getElementById('otpSection');

    if (generateOtpBtn) {
        generateOtpBtn.onclick = function() {
            const formData = new FormData(registerForm);
            const email = registerForm.email.value;
            fetch('/send-otp', {
                method: 'POST',
                body: formData
            })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    // Store email in localStorage for OTP verification page
                    localStorage.setItem('registerEmail', email);
                    window.location.href = '/otp-verification.html';
                } else {
                    alert(data.message || 'Failed to send OTP');
                }
            });
        };
    }

    if (verifyOtpBtn) {
        verifyOtpBtn.onclick = function() {
            const formData = new FormData(registerForm);
            fetch('/verify-otp', {
                method: 'POST',
                body: formData
            })
            .then(res => res.json())
            .then(data => {
                if (data.success) {
                    alert('Registration successful!');
                    window.location.href = '/login';
                } else {
                    alert(data.message || 'Invalid OTP');
                }
            });
        };
    }
});
