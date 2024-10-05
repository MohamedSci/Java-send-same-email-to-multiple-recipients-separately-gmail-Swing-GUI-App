# **Email Sender App**

This project is a **Java Swing application** that allows users to send emails in bulk using their Google email account. The app supports attachments and provides a user-friendly interface for inputting email credentials, message content, and recipient email addresses. The status of email delivery (success or failure) is displayed in real-time.

## **Features**

- Send emails in bulk using Gmail.
- Attach multiple types of files (PDF, image, video, etc.) to the emails.
- Upload recipient email addresses from a CSV file.
- Load email message content from a text file or input manually.
- View a real-time list of successfully sent emails and failed email addresses.
- Control sending operations (Pause, Cancel).
- Print a log of sent and failed emails.

## **Project Structure**

```
EmailSenderApp/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── emailsender/
│   │   │   │   │   ├── EmailSenderGUI.java        # Main Swing GUI for user input
│   │   │   │   │   ├── EmailStatusGUI.java        # Swing GUI for email sending status display
│   │   │   │   │   ├── EmailSenderService.java    # Service class for sending emails
│   │   ├── resources/
│   │   │   ├── Company_Emails_UAE.csv             # Sample CSV file with recipient emails
│   │   │   ├── message.txt                        # Sample text file with email message
│   │   │   └── attachments/                       # Folder for file attachments (PDF, images, etc.)
│
├── lib/
│   ├── commons-email-1.5.jar                      # Apache Commons Email library
│   ├── mail-1.4.jar                               # JavaMail API dependency (optional)
│
├── build/                                         # Compiled .class files
├── dist/                                          # Packaged JAR for distribution
├── .gitignore                                     # Exclude certain files/folders from version control
├── pom.xml                                        # Maven dependencies and configuration
├── README.md                                      # Project documentation
└── LICENSE                                        # License file (optional)
```

## **Requirements**

- Java JDK 8 or above
- Apache Commons Email library (commons-email-1.5.jar)
- Gmail App Password for secure email sending
- Maven (optional, for dependency management)
- SMTP configuration for Gmail

## **Dependencies**

If you are using Maven, add the following dependencies to your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-email</artifactId>
        <version>1.5</version>
    </dependency>
</dependencies>
```

Or, you can manually download the JAR files and place them in the `lib/` folder.

## **How to Run**

1. **Clone the repository:**

   ```bash
   git clone https://github.com/your-username/EmailSenderApp.git
   cd EmailSenderApp
   ```

2. **Open the project in your favorite IDE** (Eclipse, IntelliJ IDEA, etc.).

3. **Add the required libraries** (`commons-email-1.5.jar`, `mail-1.4.jar`) to the project classpath.

4. **Build and run the application**:
   - The GUI will open, allowing you to input your Gmail email address, app password, and message subject.
   - You can upload the recipient CSV file, message body file, and attachment files.

5. **Fill in the following fields**:
   - **Sender Email**: Your Gmail email address.
   - **App Password**: Your Gmail app password (generated in Google Account).
   - **Email Subject**: The subject of the email.
   - **Upload CSV**: Path to the CSV file containing recipient email addresses.
   - **Upload Message File**: Path to the text file with the email message body (optional).
   - **Manual Message**: Input message content in a large text area (optional).
   - **Upload Attachment**: Path to the attachment file (PDF, image, video, etc.).

6. **Start Sending Emails**:
   - After filling in all fields, click the "Send Emails" button.
   - The email-sending status will be displayed in a new window showing successfully sent and failed email addresses.

## **Screenshot**

Include a screenshot of your application interface here:

```
+------------------------------------------------+
| Sender Email: [______________________]         |
| App Password: [______________________]         |
| Email Subject: [______________________]        |
| [Upload CSV] [Upload Message File] [Attachment]|
| [Manual Message (optional)]:                   |
| [_____________________________]                |
|                                                |
| [Send Emails]                                  |
+------------------------------------------------+
```

## **Real-Time Status Window**

After starting the email-sending process, a new window displays:

```
+-----------------------------------------------------+
| Sent Successfully            | Failed to Send       |
| ---------------------------- | ------------------- |
| user1@example.com             | user5@example.com    |
| user2@example.com             | user6@example.com    |
| ...                           | ...                 |
+-----------------------------------------------------+
| [Print Status] [Pause] [Cancel]                     |
+-----------------------------------------------------+
```

## **App Secret Password**

To use your Google email securely, you'll need to generate an **App Password** for your Gmail account:

1. Go to your **Google Account** settings.
2. Navigate to **Security** and enable **2-Step Verification** (if not already enabled).
3. Go to **App Passwords** and generate one for the **Mail** app.

Use this app password in the **App Password** field of the application instead of your regular Gmail password.

## **Functionality**

- **Pause**: Temporarily stop sending emails. You can resume later.
- **Cancel**: Stop the process of sending emails.
- **Print Status**: Print the list of successful and failed email deliveries.

## **Customization**

You can customize the email message, attachment type, and SMTP configuration:

- **Message File**: Upload a custom `.txt` file with your email body.
- **CSV File**: Upload a `.csv` file with recipient email addresses.
- **Attachment**: Attach files like PDFs, images, or videos.

## **Known Issues**

- Ensure that your Gmail account has **App Password** enabled.
- The application may experience a delay in sending emails due to network issues or Gmail rate limits.

## **License**

This project is licensed under the Apache-2.0 License - see the [LICENSE](LICENSE) file for details.

---

### **Contributions**

Feel free to fork the repository, submit issues, or make a pull request if you'd like to improve the project!

## [Buy me a Coffee☕](<https://ko-fi.com/mohamedsaidibrahim>)

If you enjoy this content and want to support me, feel free to [buy me a coffee](<https://ko-fi.com/mohamedsaidibrahim>)
![Screenshot 1](Screenshots/2.cofffffe.png)(<https://ko-fi.com/mohamedsaidibrahim>)

### **Screenshots**

![Screenshot 2](Screenshots/1.sender%20emails.png)
![Screenshot 3](Screenshots/2.%20Sent%20Failed%20List.png)
