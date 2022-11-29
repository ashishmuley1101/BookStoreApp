package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.exception.BookStoreException;
import com.bridgelabz.bookstore.model.BookModel;
import com.bridgelabz.bookstore.model.CartModel;
import com.bridgelabz.bookstore.model.UserModel;
import com.bridgelabz.bookstore.repository.IBookRepository;
import com.bridgelabz.bookstore.repository.ICartRepository;
import com.bridgelabz.bookstore.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    private IUserRepository userRepository;


    @Autowired
    private BookService bookService;

    @Autowired
    private ICartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Override
    public CartModel getCartById(int userid){
        return cartRepository.findById(userid).orElseThrow(()
                -> new BookStoreException("Cart details with UserId " + userid + " does not exit..!"));
    }
    @Override
    public List<CartModel> getCart(){
        return cartRepository.findAll();
    }

    @Override
    public CartModel createCart(CartDTO cartDTO) {
        Optional<UserModel> userModel=userRepository.findById(cartDTO.getUserId());
        Optional<BookModel>  bookModel= Optional.ofNullable(bookService.getBookModelById(cartDTO.bookId));
        if (userModel.isPresent() && bookModel.isPresent()) {
            if (bookModel.get().getQuantity() >= cartDTO.getQuantity() && cartDTO.getQuantity() > 0) {

                CartModel cartModel = cartRepository.findCartByUserIdAndBookId(cartDTO.getBookId(), cartDTO.getUserId());
               // Resetting the book quantity in BookModel
                bookModel.get().setQuantity(bookModel.get().getQuantity()-cartDTO.getQuantity());
                if (cartModel != null) {
                    CartModel cartModel1 = update(cartModel.getCartId(),cartDTO);
                    return cartModel1;
                } else {

                    double totalPrice = cartDTO.getQuantity() * bookModel.get().getPrice();
                    CartModel cartModel1 = new CartModel(cartDTO.getQuantity(), totalPrice, userModel.get(), bookModel.get());

                    return cartRepository.save(cartModel1);
                }
            }
            throw (new BookStoreException("Book out of stock only "+bookModel.get().getQuantity()+" books remaining...! "));
        }
        throw (new BookStoreException("Book not found..!"));
    }

    @Override
    public CartModel update( int id,CartDTO cartDTO){
        UserModel userModel=userService.getUserModelDataById(cartDTO.getUserId());
        if (cartRepository.findById(id).isPresent() && userModel!=null){
            CartModel cartModel = cartRepository.findById(id).get();
            cartModel.setQuantity(cartDTO.quantity);
            cartModel.setTotalPrice(cartModel.getQuantity() * cartModel.getBook().getPrice());
            return cartRepository.save(cartModel);
        }else  throw new BookStoreException("No book found with book Id or you are not right access user");
    }


    @Override
    public void deleteById(int userId){
        CartModel cartModel = this.getCartById(userId);
           cartRepository.delete(cartModel);
    }

    @Override
    public String emptyCart(){
        cartRepository.deleteAll();
        return "Your cart is empty..!";
    }

}




