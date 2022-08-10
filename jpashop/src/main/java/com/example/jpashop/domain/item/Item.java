package com.example.jpashop.domain.item;

import com.example.jpashop.domain.Category;
import com.example.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorColumn(name = "dtype")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private Integer price;
    private Integer stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories;

    // 비즈니스
    // 재고 늘리기
    public void addStock(Integer quantity) {
        this.stockQuantity = this.stockQuantity + quantity;
    }
    // 재고 줄이기
    public void removeStock(Integer quantity) {
        int currentStock = this.stockQuantity - quantity;
        if (currentStock < 0) {
            throw new NotEnoughStockException("재고 없음");
        }
        this.stockQuantity = this.stockQuantity - quantity;
    }
}
