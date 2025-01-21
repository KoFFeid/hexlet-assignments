package exercise.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EntityListeners;
import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.GeneratedValue;

@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Product {
    //    Изучите модель товара.
//    Обратите внимание на изменения, произошедшие в классе Product.
//    Название товара теперь содержится в поле name, цена в поле cost,
//    а артикул в поле barcode.
//
//    При этом интерфейс нашего приложения должен остаться неизменным,
//    потому что на него рассчитывают пользователи. Загляните в DTO, изучите интерфейс приложения.
//
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String name;

    private int cost;

    private long barcode;

    @LastModifiedDate
    private LocalDate updatedAt;

    @CreatedDate
    private LocalDate createdAt;
}
