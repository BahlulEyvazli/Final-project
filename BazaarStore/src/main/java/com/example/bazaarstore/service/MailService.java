package com.example.bazaarstore.service;


import com.example.bazaarstore.dto.product.ProductDTO;
import com.example.bazaarstore.model.entity.User;
import com.itextpdf.text.DocumentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
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


    public void sendEmailWithAttachment(String toEmail,
                                        String body,
                                        String subject,
                                        ProductDTO productDTO,String fileName) throws MessagingException, DocumentException, IOException {


        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(pdfService.createProductCreationPdf(productDTO,fileName));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
        log.info("Mail sent ...");

    }

    public void sendPaymentMail(String toEmail,
                                String body,
                                String subject,
                                User user, String fileName) throws MessagingException, DocumentException, IOException {


        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper
                = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("spring.email.from@gmail.com");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setText(body);
        mimeMessageHelper.setSubject(subject);

        FileSystemResource fileSystem
                = new FileSystemResource(pdfService.makePayment(user,fileName));

        mimeMessageHelper.addAttachment(fileSystem.getFilename(),
                fileSystem);

        mailSender.send(mimeMessage);
        log.info("Mail sent ...");

    }
}
