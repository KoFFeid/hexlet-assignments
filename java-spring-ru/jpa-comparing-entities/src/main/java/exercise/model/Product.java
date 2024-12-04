package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.*;

// BEGIN
@Entity
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of={"title", "price"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private int price;

}
// END
