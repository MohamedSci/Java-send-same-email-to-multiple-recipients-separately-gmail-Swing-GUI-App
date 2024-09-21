package com.emailsender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EmailStatusGUI extends JFrame {
    private boolean isPaused = false;

    public EmailStatusGUI(ArrayList<String> successfulEmails, ArrayList<String> failedEmails) {
        setTitle("Email Sending Status");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        JPanel statusPanel = new JPanel(new BorderLayout());
        setContentPane(statusPanel);

        // Section 1: Emails status (2 columns)
        JPanel emailStatusPanel = new JPanel(new GridLayout(1, 2));

        // Left column - Successful emails
        JPanel successPanel = new JPanel();
        successPanel.setBorder(BorderFactory.createTitledBorder("Successful Emails"));
        successPanel.setLayout(new BoxLayout(successPanel, BoxLayout.Y_AXIS));
        JList<String> successList = new JList<>(successfulEmails.toArray(new String[0]));
        successList.setForeground(Color.GREEN);
        successPanel.add(new JScrollPane(successList));

        // Right column - Failed emails
        JPanel failurePanel = new JPanel();
        failurePanel.setBorder(BorderFactory.createTitledBorder("Failed Emails"));
        failurePanel.setLayout(new BoxLayout(failurePanel, BoxLayout.Y_AXIS));
        JList<String> failureList = new JList<>(failedEmails.toArray(new String[0]));
        failureList.setForeground(Color.RED);
        failurePanel.add(new JScrollPane(failureList));

        emailStatusPanel.add(successPanel);
        emailStatusPanel.add(failurePanel);

        // Section 2: Buttons (Print, Pause, Cancel)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton printButton = new JButton("Print Status");
        JButton pauseButton = new JButton("Pause Sending");
        JButton cancelButton = new JButton("Cancel Sending");

        // Print Button Action
        printButton.addActionListener(e -> {
            System.out.println("Successful Emails:");
            successfulEmails.forEach(System.out::println);

            System.out.println("Failed Emails:");
            failedEmails.forEach(System.out::println);
        });

        // Pause Button Action
        pauseButton.addActionListener(e -> {
            isPaused = !isPaused;
            pauseButton.setText(isPaused ? "Resume Sending" : "Pause Sending");
        });

        // Cancel Button Action
        cancelButton.addActionListener(e -> {
            dispose();
            // Additional code to stop email sending can be added here
        });

        buttonPanel.add(printButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(cancelButton);

        statusPanel.add(emailStatusPanel, BorderLayout.CENTER);
        statusPanel.add(buttonPanel, BorderLayout.SOUTH);
    }
}
