package com.example.bookstore.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "salesman")
public class SalesMan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName;

    @Pattern(regexp = "^(?:\\+1)?[\\s.-]?\\(?([2-9][0-8][0-9])\\)?[\\s.-]?([2-9][0-9]{2})[\\s.-]?([0-9]{4})$")
    private String phone;

    @Email
    @NotEmpty
    @Size(max = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @OneToMany(mappedBy = "salesMan")
    private List<Sale> sales;
}