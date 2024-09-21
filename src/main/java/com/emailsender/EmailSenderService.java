package com.emailsender;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EmailSenderService {

    private String senderEmail;
    private String password;
    private String subject;
    private String messageBody;
    private String attachmentPath;
    private String csvPath;

    // Tracking email statuses
    private ArrayList<String> successfulEmails = new ArrayList<>();
    private ArrayList<String> failedEmails = new ArrayList<>();

    // Pause and cancel flags
    private boolean isPaused = false;
    private boolean isCancelled = false;

    public EmailSenderService(String senderEmail, String password, String subject, String messageBody,
            String attachmentPath, String csvPath) {
        this.senderEmail = senderEmail;
        this.password = password;
        this.subject = subject;
        this.messageBody = messageBody;
        this.attachmentPath = attachmentPath;
        this.csvPath = csvPath;
    }

    // Sends emails to all recipients in the CSV
    public void sendEmails() {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvPath))) {
            String email;

            while ((email = csvReader.readLine()) != null) {
                if (isCancelled) {
                    System.out.println("Email sending was cancelled.");
                    return;
                }
                if (isPaused) {
                    System.out.println("Email sending is paused.");
                    Thread.sleep(5000); // Wait until the resume or cancel button is pressed
                    continue;
                }

                try {
                    sendEmailToRecipient(email);
                    successfulEmails.add(email);
                    System.out.println("Email sent successfully to: " + email);
                } catch (EmailException e) {
                    failedEmails.add(email);
                    System.out.println("Failed to send email to: " + email);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Sends an email to a single recipient
    private void sendEmailToRecipient(String recipientEmail) throws EmailException {
        MultiPartEmail multiPartEmail = new MultiPartEmail();
        multiPartEmail.setHostName("smtp.gmail.com");
        multiPartEmail.setSmtpPort(465);
        multiPartEmail.setAuthenticator(new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, password);
            }
        });
        multiPartEmail.setSSLOnConnect(true);
        multiPartEmail.setFrom(senderEmail);
        multiPartEmail.setSubject(subject);
        multiPartEmail.setMsg(messageBody);

        // Attach the file if the path is provided
        if (!attachmentPath.isEmpty()) {
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(attachmentPath);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            multiPartEmail.attach(attachment);
        }

        // Add recipient and send the email
        multiPartEmail.addTo(recipientEmail);
        multiPartEmail.send();
    }

    // Getters for successful and failed emails
    public ArrayList<String> getSuccessfulEmails() {
        return successfulEmails;
    }

    public ArrayList<String> getFailedEmails() {
        return failedEmails;
    }

    // Pause email sending
    public void pauseSending() {
        isPaused = true;
    }

    // Resume email sending
    public void resumeSending() {
        isPaused = false;
    }

    // Cancel email sending
    public void cancelSending() {
        isCancelled = true;
    }

    // Utility to read the content of a file (e.g., message file)
    public static String readFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
