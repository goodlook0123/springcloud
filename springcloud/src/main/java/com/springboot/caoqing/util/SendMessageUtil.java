package com.springboot.caoqing.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Component
public class SendMessageUtil {

    private final Logger logger = Logger.getLogger(getClass());

    @Value("${spring.mail.username}")
    private String mailSenderName;


    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * @方法名: sendMail
     * @参数名：@param subject  邮件主题
     * @参数名：@param content 邮件主题内容
     * @参数名：@param from        发件人Email地址
     * @参数名：@param to         收件人Email地址
     * @描述语: 发送邮件
     *
     * String subject, String content, String from,String to
     */
    public void sendMailMessage() {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            messageHelper.setFrom(mailSenderName); //设置发件人Email
            logger.info(mailSenderName+"****************************************************************");
            messageHelper.setSubject("java测试发邮件"); //设置邮件主题
           // messageHelper.setText(content);   //设置邮件主题内容
            messageHelper.setTo("906505882@qq.com");          //设定收件人Email
            messageHelper.setCc("goodlook0123@126.com");//设定抄送人
            messageHelper.setText("只是测试下。。。");
            /*messageHelper.setText(
                    "<html>" +
                            "<body>" + content +
                            "</body>" +
                            "</html>", true);*/
            //步骤 1
            //资源的引用方法：cid:你自己设置的资源ID
//            messageHelper.setText(
//                    "<html>" +
//                            "<body>" +
//                            "<BR>" +
//                            "<div align='center'>" +
//                            "<img src='cid:imageid'/>" +
//                            "<BR>" +
//                            "<h4>" +
//                            "返回 fancydeepin 的Blogjava：" +
//                            "<a href='http://www.blogjava.net/fancydeepin/'>http://www.blogjava.net/fancydeepin/</a>" +
//                            "</h4>" +
//                            "</div>" +
//                            "</body>" +
//                            "</html>", true);
            /**
             * ClassPathResource：很明显就是类路径资源,我这里的附件是在项目里的,所以需要用ClassPathResource
             * 如果是系统文件资源就不能用ClassPathResource,而要用FileSystemResource,例：
             * FileSystemResource file = new FileSystemResource(new File("D:/woniu.png"));
             */
            /**
             * 如果想在HTML中使用资源,必须在HTML中通过资源 ID 先引用资源,然后才来加载资源
             */
            //步骤 2
//            ClassPathResource image = new ClassPathResource("images/body.png");
//            messageHelper.addInline("imageid", image);
            javaMailSender.send(mimeMessage);    //发送HTML邮件

        } catch (Exception e) {
            System.out.println("异常信息：" + e);
        }
    }


    /**
     * 多人发送邮件
     *
     * @param to
     *            收件人列表，以","分割
     * @param subject
     *            标题
     * @param body
     *            内容
     * @param filepath
     *            附件列表,无附件传递null
     * @return
     * @throws MessagingException
     * @throws AddressException
     * @throws UnsupportedEncodingException
     */

    public boolean  sendManyMailMessage(String to, String subject, String body,List<String> filepath) throws UnsupportedEncodingException, MessagingException {

        String host = "";  //"smtp.163.com";
        String nick = "";   //"测试admin";
        String username ="";    //"邮箱用户名";
        String password = "";       //"邮箱密码";
        String from ="";        //"xxx@abc.com";



        // 创建Properties对象
        Properties props = System.getProperties();
        // 创建信件服务器
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true"); // 通过验证
        // 得到默认的对话对象
        Session session = Session.getDefaultInstance(props, null);
        // 创建一个消息，并初始化该消息的各项元素
        MimeMessage msg = new MimeMessage(session);
        nick = MimeUtility.encodeText(nick);
        msg.setFrom(new InternetAddress(nick + "<" + from + ">"));
        // 创建收件人列表
        if (to != null && to.trim().length() > 0) {
            String[] arr = to.split(",");
            int receiverCount = arr.length;
            if (receiverCount > 0) {
                InternetAddress[] address = new InternetAddress[receiverCount];
                for (int i = 0; i < receiverCount; i++) {
                    address[i] = new InternetAddress(arr[i]);
                }
                msg.addRecipients(Message.RecipientType.TO, address);
                msg.setSubject(subject);
                // 后面的BodyPart将加入到此处创建的Multipart中
                Multipart mp = new MimeMultipart();
                // 附件操作
                if (filepath != null && filepath.size() > 0) {
                    for (String filename : filepath) {
                        MimeBodyPart mbp = new MimeBodyPart();
                        // 得到数据源
                        FileDataSource fds = new FileDataSource(filename);
                        // 得到附件本身并至入BodyPart
                        mbp.setDataHandler(new DataHandler(fds));
                        // 得到文件名同样至入BodyPart
                        mbp.setFileName(fds.getName());
                        mp.addBodyPart(mbp);
                    }
                    MimeBodyPart mbp = new MimeBodyPart();
                    mbp.setText(body);
                    mp.addBodyPart(mbp);
                    // 移走集合中的所有元素
                    filepath.clear();
                    // Multipart加入到信件
                    msg.setContent(mp);
                } else {
                    // 设置邮件正文
                    msg.setText(body);
                }
                // 设置信件头的发送日期
                msg.setSentDate(new Date());
                msg.saveChanges();
                // 发送信件
                Transport transport = session.getTransport("smtp");
                transport.connect(host, username, password);
                transport.sendMessage(msg,
                        msg.getRecipients(Message.RecipientType.TO));
                transport.close();
                return true;
            } else {
                System.out.println("None receiver!");
                return false;
            }
        } else {
            System.out.println("None receiver!");
            return false;
        }
    }
}
