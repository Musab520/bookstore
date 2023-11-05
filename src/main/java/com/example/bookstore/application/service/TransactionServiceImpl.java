package com.example.bookstore.application.service;

import com.example.bookstore.data.models.Book;
import com.example.bookstore.data.models.CartItem;
import com.example.bookstore.data.models.Transaction;
import com.example.bookstore.data.repository.BookRepository;
import com.example.bookstore.data.repository.CartItemRepository;
import com.example.bookstore.data.repository.TransactionRepository;
import com.example.bookstore.domain.service.TransactionService;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TransactionServiceImpl implements TransactionService {
    private final BookRepository bookRepository;
    private final TransactionRepository transactionRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public void save(Transaction transaction) {
        for (var cartItem:transaction.getCartItems()) {
            var bookCount = cartItem.getBook().getCount();
            cartItem.getBook().setCount(bookCount - cartItem.getCount());
            cartItem.setPrice(cartItem.getBook().getPrice());
        }
        cartItemRepository.save(transaction.getCartItems());
        transactionRepository.save(transaction);
        List<Book> books = transaction.getCartItems().stream()
                .map(CartItem::getBook)
                .toList();
        bookRepository.update(books);
    }

    @Override
    public Transaction getById(int id) {
        return transactionRepository.getById(id);
    }
}
