package com.bridgelabz.bookstore.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    public int bookId;
    public int quantity;

    public int userId;
}
