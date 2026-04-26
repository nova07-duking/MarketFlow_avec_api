import api from './api.js';

document.addEventListener('DOMContentLoaded', () => {
    const stepEmail = document.getElementById('step-email');
    const stepOtp = document.getElementById('step-otp');
    const emailInput = document.getElementById('email-input');
    const sendOtpBtn = document.getElementById('send-otp-btn');
    const verifyOtpBtn = document.getElementById('verify-otp-btn');
    const otpDigits = document.querySelectorAll('.otp-digit');

    let currentEmail = '';

    // Étape 1 : Envoyer OTP
    sendOtpBtn.addEventListener('click', async () => {
        const email = emailInput.value.trim();
        if (!email) {
            alert('Veuillez entrer votre email');
            return;
        }

        try {
            sendOtpBtn.disabled = true;
            sendOtpBtn.textContent = 'Envoi...';
            
            await api.post('/auth/send-otp', { email });
            
            currentEmail = email;
            stepEmail.style.display = 'none';
            stepOtp.style.display = 'block';
            
            // Focus sur le premier champ OTP
            otpDigits[0].focus();
        } catch (error) {
            alert(error.message);
        } finally {
            sendOtpBtn.disabled = false;
            sendOtpBtn.textContent = 'Obtenir le code';
        }
    });

    // Gestion des inputs OTP (auto-tab)
    otpDigits.forEach((digit, index) => {
        digit.addEventListener('input', (e) => {
            if (e.target.value && index < otpDigits.length - 1) {
                otpDigits[index + 1].focus();
            }
        });

        digit.addEventListener('keydown', (e) => {
            if (e.key === 'Backspace' && !e.target.value && index > 0) {
                otpDigits[index - 1].focus();
            }
        });
    });

    // Étape 2 : Vérifier OTP
    verifyOtpBtn.addEventListener('click', async () => {
        const otpCode = Array.from(otpDigits).map(d => d.value).join('');
        if (otpCode.length < 6) {
            alert('Veuillez entrer le code à 6 chiffres');
            return;
        }

        try {
            verifyOtpBtn.disabled = true;
            verifyOtpBtn.textContent = 'Vérification...';
            
            const data = await api.post('/auth/verify-otp', {
                email: currentEmail,
                otp: otpCode
            });
            
            // Stocker le token et les infos
            localStorage.setItem('token', data.token);
            localStorage.setItem('user', JSON.stringify({
                id: data.userId,
                fullName: data.fullName,
                role: data.role
            }));
            
            // Redirection
            window.location.href = data.role === 'SELLER' ? 'dashboard.html' : 'index.html';
        } catch (error) {
            alert(error.message);
        } finally {
            verifyOtpBtn.disabled = false;
            verifyOtpBtn.textContent = 'Vérifier et se connecter';
        }
    });
});
