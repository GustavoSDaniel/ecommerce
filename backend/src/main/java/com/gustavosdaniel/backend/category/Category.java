package com.gustavosdaniel.backend.category;

import com.gustavosdaniel.backend.commun.ActiveOrInactive;
import com.gustavosdaniel.backend.product.Product;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Category {

    public Category() {
    }

    public Category(String id, String name, String imageName, ActiveOrInactive isActive, List<Product> products) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
        this.isActive = isActive;
        this.products = products;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private String id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String imageName;

    @Enumerated(EnumType.STRING)
    private ActiveOrInactive isActive;

    @OneToMany(mappedBy =  "category", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Product> products;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_at", insertable = false)
    private LocalDateTime updatedAt;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ActiveOrInactive getIsActive() {
        return isActive;
    }

    public void setIsActive(ActiveOrInactive isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
