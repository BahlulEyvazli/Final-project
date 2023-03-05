package com.example.bazaarstore.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "category")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    Set<Product> products;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    Set<Product> products;

}
