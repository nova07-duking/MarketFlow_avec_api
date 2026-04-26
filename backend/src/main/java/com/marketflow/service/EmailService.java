package com.marketflow.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String otpCode) {
        String subject = "MarketFlow — Votre code de vérification";
        String htmlContent = buildOtpEmailTemplate(otpCode);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email OTP envoyé à : {}", to);
        } catch (MessagingException e) {
            log.error("Erreur lors de l'envoi de l'email OTP à {} : {}", to, e.getMessage());
            throw new RuntimeException("Impossible d'envoyer l'email de vérification");
        }
    }

    public void sendOrderConfirmation(String to, String orderNumber, String total) {
        String subject = "MarketFlow — Confirmation de commande #" + orderNumber;
        String htmlContent = buildOrderConfirmationTemplate(orderNumber, total);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Email de confirmation envoyé pour la commande #{}", orderNumber);
        } catch (MessagingException e) {
            log.error("Erreur lors de l'envoi de la confirmation de commande : {}", e.getMessage());
        }
    }

    private String buildOtpEmailTemplate(String otpCode) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: 'Inter', Arial, sans-serif; background-color: #f7f9ff; margin: 0; padding: 0; }
                    .container { max-width: 480px; margin: 40px auto; background: #ffffff; border-radius: 12px; border: 1px solid #e5e8ee; overflow: hidden; }
                    .header { background: #1a73e8; padding: 32px; text-align: center; }
                    .header h1 { color: #ffffff; margin: 0; font-size: 24px; font-weight: 700; }
                    .body { padding: 32px; text-align: center; }
                    .body p { color: #414754; font-size: 16px; line-height: 24px; }
                    .otp-code { display: inline-block; background: #f1f4fa; border: 2px solid #1a73e8; border-radius: 8px; padding: 16px 32px; margin: 24px 0; font-size: 32px; font-weight: 700; letter-spacing: 8px; color: #1a73e8; }
                    .footer { padding: 24px 32px; text-align: center; border-top: 1px solid #e5e8ee; }
                    .footer p { color: #727785; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>MarketFlow</h1>
                    </div>
                    <div class="body">
                        <p>Bonjour,</p>
                        <p>Voici votre code de vérification :</p>
                        <div class="otp-code">%s</div>
                        <p>Ce code expire dans <strong>5 minutes</strong>.</p>
                        <p>Si vous n'avez pas demandé ce code, ignorez cet email.</p>
                    </div>
                    <div class="footer">
                        <p>© 2024 MarketFlow Ecosystem. Commerce sécurisé & fiable.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(otpCode);
    }

    private String buildOrderConfirmationTemplate(String orderNumber, String total) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <style>
                    body { font-family: 'Inter', Arial, sans-serif; background-color: #f7f9ff; margin: 0; padding: 0; }
                    .container { max-width: 480px; margin: 40px auto; background: #ffffff; border-radius: 12px; border: 1px solid #e5e8ee; overflow: hidden; }
                    .header { background: #1a73e8; padding: 32px; text-align: center; }
                    .header h1 { color: #ffffff; margin: 0; font-size: 24px; font-weight: 700; }
                    .body { padding: 32px; }
                    .body p { color: #414754; font-size: 16px; line-height: 24px; }
                    .highlight { background: #f1f4fa; border-radius: 8px; padding: 16px; margin: 16px 0; }
                    .highlight .label { color: #727785; font-size: 12px; text-transform: uppercase; letter-spacing: 1px; }
                    .highlight .value { color: #181c20; font-size: 24px; font-weight: 700; }
                    .footer { padding: 24px 32px; text-align: center; border-top: 1px solid #e5e8ee; }
                    .footer p { color: #727785; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Commande confirmée ✓</h1>
                    </div>
                    <div class="body">
                        <p>Merci pour votre commande !</p>
                        <div class="highlight">
                            <div class="label">Numéro de commande</div>
                            <div class="value">#%s</div>
                        </div>
                        <div class="highlight">
                            <div class="label">Total</div>
                            <div class="value">%s €</div>
                        </div>
                        <p>Vous pouvez suivre votre commande depuis votre espace client.</p>
                    </div>
                    <div class="footer">
                        <p>© 2024 MarketFlow Ecosystem. Commerce sécurisé & fiable.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(orderNumber, total);
    }
}
