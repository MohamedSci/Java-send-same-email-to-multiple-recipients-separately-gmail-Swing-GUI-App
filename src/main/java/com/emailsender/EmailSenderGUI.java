package com.emailsender;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EmailSenderGUI extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField subjectField;
    private JTextField csvPathField;
    private JTextField messageFilePathField;
    private JTextField attachmentPathField;
    private JTextArea messageBodyArea;

    private String csvPath, messageFilePath, attachmentPath, senderEmail, password, subject, messageBody;

    // Status tracking variables
    private ArrayList<String> successfulEmails = new ArrayList<>();
    private ArrayList<String> failedEmails = new ArrayList<>();
    private boolean isPaused = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EmailSenderGUI frame = new EmailSenderGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public EmailSenderGUI() {
        setTitle("Email Sender");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(8, 2, 10, 10));

        JLabel emailLabel = new JLabel("Your Email:");
        contentPane.add(emailLabel);
        emailField = new JTextField();
        contentPane.add(emailField);
        emailField.setColumns(10);

        JLabel passwordLabel = new JLabel("App Secret Password:");
        contentPane.add(passwordLabel);
        passwordField = new JPasswordField();
        contentPane.add(passwordField);

        JLabel subjectLabel = new JLabel("Email Subject:");
        contentPane.add(subjectLabel);
        subjectField = new JTextField();
        contentPane.add(subjectField);

        JLabel csvPathLabel = new JLabel("CSV File Path:");
        contentPane.add(csvPathLabel);
        csvPathField = new JTextField();
        csvPathField.setEditable(false);
        contentPane.add(csvPathField);

        JButton csvBrowseButton = new JButton("Browse CSV");
        csvBrowseButton.addActionListener(e -> csvPathField.setText(selectFile()));
        contentPane.add(csvBrowseButton);

        JLabel messageFileLabel = new JLabel("Message File Path:");
        contentPane.add(messageFileLabel);
        messageFilePathField = new JTextField();
        messageFilePathField.setEditable(false);
        contentPane.add(messageFilePathField);

        JButton messageFileBrowseButton = new JButton("Browse Message File");
        messageFileBrowseButton.addActionListener(e -> messageFilePathField.setText(selectFile()));
        contentPane.add(messageFileBrowseButton);

        JLabel attachmentPathLabel = new JLabel("Attachment File Path:");
        contentPane.add(attachmentPathLabel);
        attachmentPathField = new JTextField();
        attachmentPathField.setEditable(false);
        contentPane.add(attachmentPathField);

        JButton attachmentBrowseButton = new JButton("Browse Attachment");
        attachmentBrowseButton.addActionListener(e -> attachmentPathField.setText(selectFile()));
        contentPane.add(attachmentBrowseButton);

        JLabel messageBodyLabel = new JLabel("Message Body (or leave blank if using file):");
        contentPane.add(messageBodyLabel);
        messageBodyArea = new JTextArea();
        contentPane.add(new JScrollPane(messageBodyArea));

        JButton sendButton = new JButton("Send Emails");
        sendButton.addActionListener(e -> {
            senderEmail = emailField.getText();
            password = new String(passwordField.getPassword());
            subject = subjectField.getText();
            csvPath = csvPathField.getText();
            messageFilePath = messageFilePathField.getText();
            attachmentPath = attachmentPathField.getText();
            messageBody = messageBodyArea.getText().isEmpty() ? readFileContent(messageFilePath) : messageBodyArea.getText();

            if (validateInputs()) {
                // Start email sending process and show status frame
                showStatusFrame(); 
                sendEmails();
            } else {
                JOptionPane.showMessageDialog(null, "Please fill all required fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        contentPane.add(sendButton);
    }

    private String selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getPath();
        }
        return "";
    }

    private String readFileContent(String filePath) {
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

    private boolean validateInputs() {
        return !senderEmail.isEmpty() && !password.isEmpty() && !csvPath.isEmpty() && !subject.isEmpty();
    }

    private void sendEmails() {
        try (BufferedReader csvReader = new BufferedReader(new FileReader(csvPath))) {
            String email;
            while ((email = csvReader.readLine()) != null) {
                if (isPaused) {
                    Thread.sleep(5000); // Pause sending for 5 seconds if paused
                    continue;
                }
                try {
                    sendEmailToRecipient(email);
                    successfulEmails.add(email);
                } catch (EmailException e) {
                    failedEmails.add(email);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

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

        if (!attachmentPath.isEmpty()) {
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(attachmentPath);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            multiPartEmail.attach(attachment);
        }

        multiPartEmail.addTo(recipientEmail);
        multiPartEmail.send();
    }

    private void showStatusFrame() {
        EmailStatusGUI statusGUI = new EmailStatusGUI(successfulEmails, failedEmails);
        statusGUI.setVisible(true);
    }
}
