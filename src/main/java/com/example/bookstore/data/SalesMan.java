package com.example.bookstore.data;

import lombok.Data;
import java.util.Date;

@Data
public class SalesMan {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Gender gender;
    private Date birthday ;

    public SalesMan(){}
}
