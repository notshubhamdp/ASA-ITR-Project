// otp-verification.js - Handles OTP verification for registration

document.addEventListener('DOMContentLoaded', function() {
    const otpForm = document.querySelector('form');
    const email = localStorage.getItem('registerEmail');

    otpForm.addEventListener('submit', function(e) {
        e.preventDefault();
        const otp = otpForm.otp.value;
        const formData = new FormData();
        formData.append('otp', otp);
        formData.append('email', email);

        fetch('/verify-otp', {
            method: 'POST',
            body: formData
        })
        .then(res => res.json())
        .then(data => {
            if (data.success) {
                alert('Registration successful!');
                localStorage.removeItem('registerEmail');
                window.location.href = '/login';
            } else {
                alert(data.message || 'Invalid OTP');
            }
        });
    });
});
