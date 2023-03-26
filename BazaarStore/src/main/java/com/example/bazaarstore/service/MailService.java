package com.example.bazaarstore.service;

import com.example.bazaarstore.dto.product.ProductDTO;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class MailService {

    private static final String FROM_MAIL = "yusifzehmeti@gmail.com";

    private final JavaMailSender mailSender;

    private final PdfService pdfService;

    public MailService(JavaMailSender mailSender, PdfService pdfService) {
        this.mailSender = mailSender;
        this.pdfService = pdfService;
    }


    public String sendSimpleMail(String toMail,String subject,String text){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(FROM_MAIL);
        mailMessage.setTo(toMail);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        mailSender.send(mailMessage);

        return (text + " sent to " + toMail);
    }

    public void sendEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        ProductDTO productDTO) throws MessagingException, DocumentException, IOException {


        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(pdfService.createProductCreationPdf(productDTO,"check.pdf"));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
        log.info("Mail sent ...");

    }
}
