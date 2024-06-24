package clothify.sys.model;
import java.time.LocalDate;

import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {


    private String id;
    private String email;
    private String name;
    private Boolean isAdmin;
    private Boolean isActive;

}
