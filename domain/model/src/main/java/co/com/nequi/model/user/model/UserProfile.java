package co.com.nequi.model.user.model;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserProfile {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
