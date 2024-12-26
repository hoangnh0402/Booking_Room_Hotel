package com.hit.common.utils;

import com.hit.common.core.domain.request.SendMailRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class SendMailUtils {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    /**
     * Gửi mail với file html
     *
     * @param request     Thông tin của mail cần gửi
     * @param template Tên file html trong folder resources/template
     *                 Example: Index.html
     */
    @Async
    @SneakyThrows
    public void sendEmailWithHTML(SendMailRequest request, String template) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());

            Context context = new Context();
            context.setVariables(request.getProperties());
            String htmlMsg = templateEngine.process(template, context);
            helper.setText(htmlMsg, true);
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Send mail with html template to email = {} fail, ERROR: ", request.getTo(), ex);
        }
    }


    /**
     * Gửi mail với tệp đính kèm
     *
     * @param request Thông tin của mail cần gửi
     * @param files    File cần gửi
     */
    @Async
    @SneakyThrows
    public void sendMail(SendMailRequest request, MultipartFile[] files) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setTo(request.getTo());
            helper.setSubject(request.getSubject());
            helper.setText(request.getContent());
            if (files != null) {
                for (MultipartFile file : files) {
                    helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), file);
                }
            }
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Send mail with attachment to email = {} fail, ERROR: ", request.getTo(), ex);
        }
    }
}
