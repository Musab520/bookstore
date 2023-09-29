package com.example.bookstore.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class SalesMan {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Gender gender;
    private Date birthday ;
}
