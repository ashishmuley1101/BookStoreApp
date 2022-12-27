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
import lombok.extern.slf4j.Slf4j;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    List<OrderModel> orderList = new ArrayList<>();
    @Override
    public OrderModel  createOrder(int userId,OrderDTO orderDTO) throws MessagingException {

        List<CartModel> cartModel = cartService.getCartByUserId(userId);
//        List<BookModel> book = bookRepository.findById(cartModel.);
        UserModel userModel = userService.getUserModelDataById(userId);
        if (!cartModel.isEmpty()) {
            userModel.setAddress(orderDTO.getAddress());
            double totalOrderPrice = 0;
            int totalOrderQty = 0;
            List<BookModel> orderedBooks = new ArrayList<>();

            String address = "";
            for (int i = 0; i < cartModel.size(); i++) {
                totalOrderPrice += cartModel.get(i).getTotalPrice();
                totalOrderQty += cartModel.get(i).getQuantity();
                orderedBooks.add(cartModel.get(i).getBook());
            }
            if (orderDTO.getAddress() == null) {
                address = userModel.getAddress();
            } else
                address = orderDTO.getAddress();
            OrderModel newOrder = new OrderModel(userId, address, cartModel, orderedBooks, totalOrderQty, totalOrderPrice);
            orderList.add(newOrder);
            orderRepository.save(newOrder);
//            cartRepository.deleteById(userId);
            try {
                emailService.sendEmailPlaced(newOrder, userModel);
            } catch (MessagingException e) {
                throw new BookStoreException(e.getMessage());
            }

            for (int i = 0; i < cartModel.size(); i++) {
                BookModel book = cartModel.get(i).getBook();
                int updatedQty = this.updateBookQty(book.getQuantity(), cartModel.get(i).getQuantity());
                book.setQuantity(updatedQty);
                cartRepository.deleteById(cartModel.get(i).getCartId());
            }
//              log.info("Order placed!");
            return newOrder;
        }
    else{
            throw new BookStoreException("No Book Added  in cart..! ");
        }

    }
            private int updateBookQty(int bookQty, int bookQtyInCart){
                int updatedQty = bookQty - bookQtyInCart;
                if (updatedQty <= 0)
                    return 0;
                else
                    return updatedQty;
            }

    @Override
    public List<OrderModel> getAllOrderRecords() {
        List<OrderModel> getOrder=orderRepository.findAll();
        return getOrder;
    }

    @Override
    public List<OrderModel> getOrderItemByUserId(int userId){
        return orderRepository.findOrderByUserId(userId);
    }
    @Override
    public OrderModel getOrderItemById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new BookStoreException("No order placed with order id " + id + "!"));
    }

        @Override
        public void cancelOrderRecord(int orderId) throws MessagingException {
        OrderModel order = this.getOrderItemById(orderId);
        UserModel user = userRepository.getUserById(order.getUserId());
        if (order.isCancel() == false) {
            orderRepository.updateCancel(orderId);
            try {
                emailService.sendEmailCancel(order);
            } catch (MessagingException e) {
                throw new BookStoreException(e.getMessage());
            }

            List<BookModel> book = order.getBook();
            for (int j = 0; j < orderList.size(); j++) {
                if (orderList.get(j).getOrderId() == orderId) {
                    for (int i = 0; i < book.size(); i++) {
                        BookModel updateBook = bookService.getBookModelById(book.get(i).getBookId());
                        System.out.println(updateBook);
                        for (int k = 0; k < book.size(); k++) {
                            int orderedBookQty = orderList.get(j).getCartModel().get(k).getQuantity();
                            System.out.println(orderedBookQty);
                            int orderedBookId = orderList.get(j).getCartModel().get(k).getBook().getBookId();
                            int bookId = updateBook.getBookId();
                            if (orderedBookId == bookId) {
                                int updatedQty = orderedBookQty + updateBook.getQuantity();
                                updateBook.setQuantity(updatedQty);
                                bookRepository.save(book.get(i));
                            }
                        }
                    }
                }
            }
//            log.info("Order canceled for Order id "+orderId+" !");
        } else {
           throw new BookStoreException("Order is already canceled!");
        }

    }

    @Override
    public OrderModel update(int id, OrderDTO orderDTO) {
//        CartModel cartModel = cartRepository.findById(orderDTO.getCartId()).get();
//       if (orderRepository.findById(id).isPresent() && cartModel!=null){
//            OrderModel orderModel = orderRepository.findById(id).get();
//           //cartModel.setQuantity(orderDTO.getQuantity());
//          orderModel.setQuantity(orderDTO.getQuantity());
//            return orderRepository.save(orderModel);
//        }else
//            throw (new BookStoreException("No book found with book Id or you are not right access user"));
//    }
        return null;
    }

}
