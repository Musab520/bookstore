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

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class TransactionServiceImpl implements TransactionService {
    private final BookRepository bookRepository;
    private final TransactionRepository transactionRepository;
    private final CartItemRepository cartItemRepository;
    @Override
    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
        for (var cartItem:transaction.getCartItems()) {
            var bookCount = cartItem.getBook().getCount();
            cartItem.getBook().setCount(bookCount - cartItem.getCount());
            cartItem.setPrice(cartItem.getBook().getPrice());
            cartItem.setTransaction(transaction);
        }
        cartItemRepository.save(transaction.getCartItems());
        List<Book> books = transaction.getCartItems().stream()
                .map(CartItem::getBook)
                .toList();
        bookRepository.update(books);
    }

    @Override
    public Transaction getById(int id) {
        return transactionRepository.getById(id);
    }

    @Override
    public List<Transaction> list() {
        return transactionRepository.list();
    }

    @Override
    public List<Transaction> listFromDate(LocalDate date){
        return transactionRepository.listFromDate(date);
    }
}
