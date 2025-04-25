package AuthManager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
