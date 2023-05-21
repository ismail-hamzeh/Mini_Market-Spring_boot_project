package com.example.market.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "market_items")
public class Items {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int item_id;
    @Column
    String item_category;
    @Column
    String item_name;
    @Column
    float item_price;
    @Column
    int item_quantity;
    @Column
    String item_img;


    public Items(){}

    public Items(String item_category, String item_name, float item_price, int item_quantity, String item_img) {
        this.item_category = item_category;
        this.item_name = item_name;
        this.item_price = item_price;
        this.item_quantity = item_quantity;
        this.item_img = item_img;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem_category() {
        return item_category;
    }

    public void setItem_category(String item_type) {
        this.item_category = item_type;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public float getItem_price() {
        return item_price;
    }

    public void setItem_price(float item_price) {
        this.item_price = item_price;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }

    public String getItem_img() {
        return item_img;
    }

    public void setItem_img(String item_img) {
        this.item_img = item_img;
    }
}
