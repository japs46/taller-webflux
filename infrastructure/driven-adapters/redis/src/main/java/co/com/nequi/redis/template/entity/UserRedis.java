package co.com.nequi.redis.template.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class UserRedis {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;
}
