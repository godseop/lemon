package org.godseop.lemon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.YesNoConverter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@DynamicUpdate
@ToString(exclude = "productSkuList")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "name", nullable = false)
    private String name;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "deleted")
    private Boolean deleted;

    @CreatedBy
    @Column(name = "created_by")
    private Long createBy;

    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Long updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private Instant updatedDate;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<ProductSku> productSkuList = new ArrayList<>();

    @Transient
    private Boolean recently = Boolean.FALSE;

    @Builder(builderClassName = "ProductBuilder", builderMethodName = "ProductBuilder")
    public Product(String name) {
        this.productType = ProductType.NORMAL;
        this.name = name;
        this.deleted = Boolean.FALSE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return Objects.equals(this.id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}