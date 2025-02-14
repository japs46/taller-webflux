package co.com.nequi.dynamodb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

/* Enhanced DynamoDB annotations are incompatible with Lombok #1932
         https://github.com/aws/aws-sdk-java-v2/issues/1932*/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@DynamoDbBean
public class UserProfileDynamo {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String avatar;

    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }
}
