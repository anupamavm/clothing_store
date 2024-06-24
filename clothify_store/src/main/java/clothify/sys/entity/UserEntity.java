package clothify.sys.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class UserEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String email;
    private String name;
    private Boolean isAdmin;
    private Boolean isActive;


}
