package co.com.nequi.model.exceptions;

import co.com.nequi.model.enums.TechnicalMessage;

public class TechnicalException extends TallerWebfluxException{

    public TechnicalException(TechnicalMessage technicalMessage) {
        super(technicalMessage);
    }

    public TechnicalException(Throwable throwable, TechnicalMessage technicalMessage) {
        super(throwable, technicalMessage);
    }
}
