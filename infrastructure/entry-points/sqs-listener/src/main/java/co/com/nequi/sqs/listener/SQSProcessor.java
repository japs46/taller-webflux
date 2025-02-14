package co.com.nequi.sqs.listener;

import co.com.nequi.model.user.model.UserProfile;
import co.com.nequi.usecase.user.UserProfileDynamoUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {

    private final UserProfileDynamoUseCase userProfileDynamoUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        log.info("Mensaje recibido: {}",message.toString());

        return Mono.fromCallable(() -> objectMapper.readValue(message.body(), UserProfile.class))
                .flatMap(userProfile -> userProfileDynamoUseCase.save(userProfile)
                        .then())
                .doOnError(e -> log.error("Error al procesar el mensaje: {}", e.getMessage()))
                .onErrorResume(e -> Mono.empty());
    }
}
