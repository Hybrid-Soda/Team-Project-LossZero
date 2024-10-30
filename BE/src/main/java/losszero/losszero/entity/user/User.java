package losszero.losszero.entity.user;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 60)
    private String username;

    @Column(length = 20)
    private String team;

    @Column(columnDefinition = "TEXT")
    private String picture;

    @Column(length = 50)
    private String role;
}