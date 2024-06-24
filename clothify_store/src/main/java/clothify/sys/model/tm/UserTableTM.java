package clothify.sys.model.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTableTM {
    private String id;
    private String email;
    private String name;
    private Boolean isAdmin;
    private Boolean isActive;

}
