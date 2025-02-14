package co.com.nequi.model.exceptions;

import co.com.nequi.model.enums.TechnicalMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TallerWebfluxException extends Exception{

    private final TechnicalMessage technicalMessage;

    public TallerWebfluxException(String message, TechnicalMessage technicalMessage) {
        super(message);
        this.technicalMessage = technicalMessage;
    }

    public TallerWebfluxException(Throwable throwable, TechnicalMessage technicalMessage) {
        super(throwable);
        this.technicalMessage = technicalMessage;
    }
}
