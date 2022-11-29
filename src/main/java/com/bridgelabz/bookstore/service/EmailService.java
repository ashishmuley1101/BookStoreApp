package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IOrderRepository orderRepository;


    // Cancel order Service
     public OrderModel sendEmailCancel(OrderModel orderModel)throws MessagingException {
        String toAddress = orderModel.getUser().getEmail(); //"ashishmuley1101@gmail.com";
        String body = " Date of cancel Order : " + orderModel.getOrderDate() + "\n Cancel Order Book Name : " + orderModel.getBook().getBookName() + "\n Cancel Book Quantity : "
                + orderModel.getQuantity() + "\n Total Book Price Rs. " + orderModel.getTotalPrice();
        String fromAddress = "ashishmuley1101@gmail.com";
        String subject = "Your Order Cancelled successfully";

        if (orderModel.getUser().getEmail() != null) {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(fromAddress);
            mailMessage.setTo(toAddress);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            // Sending the mail
            mailSender.send(mailMessage);

            return orderRepository.save(orderModel);
        } else {
            throw (new BookStoreException("Sorry we cannot cancel your Order...! "));
        }
    }

    //Order Placed Service
    public OrderModel sendEmailPlaced(OrderModel orderModel)throws MessagingException {
        String toAddress = orderModel.getUser().getEmail(); //"ashishmuley1101@gmail.com";
        String body= " Date of Order : "+orderModel.getOrderDate()+"\n Order Book Name : "+orderModel.getBook().getBookName()+"\n Book Quantity : "
                +orderModel.getQuantity()+"\n Total Book Price Rs. "+orderModel.getTotalPrice();
        String fromAddress = "ashishmuley1101@gmail.com";
        String subject = "Your Order Placed successfully";

        if (orderModel.getUser().getEmail() != null) {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(fromAddress);
            mailMessage.setTo(toAddress);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            // Sending the mail
            mailSender.send(mailMessage);

            return orderModel;
        }else {
            throw (new BookStoreException("Sorry we cannot placed your Order...! "));
        }

    }


    // Sending Mail at time of registration..

    public String sendVerificationEmail(UserModel userModel)throws MessagingException {
        String toAddress = userModel.getEmail(); //"ashishmuley1101@gmail.com";
        String body= userModel.getVerificationCode();//"Hello from body";  //
        String fromAddress = "ashishmuley1101@gmail.com";
        String subject = "Please verify your registration...!";

        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(fromAddress);
            mailMessage.setTo(toAddress);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            // Sending the mail
            mailSender.send(mailMessage);
            return "Mail Sent Successfully please check your mailbox ...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail...!";
        }


    }

}
