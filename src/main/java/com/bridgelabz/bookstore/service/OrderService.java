package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.OrderModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.IBookRepository;
import com.bridgelabz.bookstore.repository.ICartRepository;
import com.bridgelabz.bookstore.repository.IOrderRepository;
import com.bridgelabz.bookstore.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private IOrderRepository orderRepository;

    @Autowired
    private ICartService cartService;

    @Autowired
    private EmailService emailService;


    @Override
    public OrderModel  createOrder(OrderDTO orderDTO) throws MessagingException {

        Optional<CartModel> cartModel = Optional.ofNullable(cartService.getCartById(orderDTO.getCartId()));
        Optional<BookModel> book = bookRepository.findById(cartModel.get().getBook().getBookId());
        UserModel user = userRepository.findById(cartModel.get().getUser().getUserId()).get();
        user.setAddress(orderDTO.getAddress());
        if (book.isPresent() && user!=null) {


            OrderModel newOrder = new OrderModel(orderDTO, book.get(), user  , cartModel.get());
            orderRepository.save(newOrder);

           return emailService.sendEmailPlaced(newOrder);

        } else {
            throw (new BookStoreException("Sorry we cannot placed your Order...! "));
        }
    }

    @Override
    public List<OrderModel> getAllOrderRecords() {
        List<OrderModel> getOrder=orderRepository.findAll();
        return getOrder;
    }

    @Override
    public OrderModel getOrderItemByUserId(int orderId){
        return orderRepository.findById(orderId).orElseThrow(()
                -> new BookStoreException("Order details with UserId " + orderId + " does not exit..!"));
    }

    @Override
    public OrderModel cancelOrderRecord(int userId) throws MessagingException {
        Optional<OrderModel> orderModel = orderRepository.findById(userId);
        if (orderModel.isPresent()) {
            OrderModel order = orderRepository.getById(userId);
            order.setCancel(true);

            return emailService.sendEmailCancel(order);
        } else {
           throw ( new BookStoreException("Invalid order Id : "+userId));
        }
    }

    @Override
    public OrderModel update(int id, OrderDTO orderDTO) {
        CartModel cartModel = cartRepository.findById(orderDTO.getCartId()).get();
       if (orderRepository.findById(id).isPresent() && cartModel!=null){
            OrderModel orderModel = orderRepository.findById(id).get();
           //cartModel.setQuantity(orderDTO.getQuantity());
          orderModel.setQuantity(orderDTO.getQuantity());
            return orderRepository.save(orderModel);
        }else
            throw (new BookStoreException("No book found with book Id or you are not right access user"));
    }
}
