package com.example.e_pesegu_app;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class JavaMailAPI extends AsyncTask<Void, Void, Boolean> {

    private Context mContext;
    private Session mSession;
    private String mEmail;
    private String mSubject;
    private String mMessage;
    private Uri mFilePath; // New field to store the file URI
    private ProgressDialog mProgressDialog;


    public JavaMailAPI(Context mContext, String mEmail, String mSubject, String mMessage, Uri mFilePath) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
        this.mFilePath = mFilePath;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog = ProgressDialog.show(mContext, "Sending message", "Please wait...", false, false);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");



        mSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Utils.EMAIL, Utils.PASSWORD);
            }
        });

        try {
            MimeMessage mm = new MimeMessage(mSession);
            mm.setFrom(new InternetAddress(Utils.EMAIL));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(mEmail));
            mm.setSubject(mSubject);
            // Set the message body
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(mMessage);

            // Attach the file to the email if a valid file URI is provided
            // Attach the file to the email if a valid Uri is provided
            if (mFilePath != null) {
                MimeBodyPart fileBodyPart = new MimeBodyPart();
                try {
                    InputStream inputStream = mContext.getContentResolver().openInputStream(mFilePath);
                    fileBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(inputStream, "application/pdf")));
                    fileBodyPart.setFileName("Lampiran A.pdf"); // Set a filename for the attached file
                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(messageBodyPart);
                    multipart.addBodyPart(fileBodyPart);
                    mm.setContent(multipart);
                } catch (IOException | MessagingException e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                mm.setText(mMessage);
            }



            // Send the email
            Transport.send(mm);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }




    @Override
    protected void onPostExecute(Boolean isEmailSent) {
        super.onPostExecute(isEmailSent);
        mProgressDialog.dismiss();
        if (isEmailSent) {
            Toast.makeText(mContext, "Message Sent", Toast.LENGTH_SHORT).show();
            // Start the home page activity here
            Intent homeIntent = new Intent(mContext, hompepage.class);
            mContext.startActivity(homeIntent);
            // Finish the current activity to prevent coming back to it when pressing the back button
            ((Activity) mContext).finish();

        } else {
            Toast.makeText(mContext, "Failed to send message", Toast.LENGTH_SHORT).show();
        }
    }




    protected void onPostExecute(Void aVoid) {
    }
}
