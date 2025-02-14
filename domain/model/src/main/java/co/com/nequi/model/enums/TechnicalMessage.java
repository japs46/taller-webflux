package co.com.nequi.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TechnicalMessage {

    USER_NOT_FOUND("T004", "El usuario no existe. Id del usuario: "),
    USER_ALREADY_EXISTS("T005", "El usuario ya existe"),
    INTERNAL_ERROR("T006", "Error interno del sistema"),
    UNEXPECTED_SERVICE_ERROR("T008", "Error inesperado en el servicio, contacte al soporte técnico"),
    DATA_PROCESSING_ERROR("T009", "Error en el procesamiento de datos, verifique la información ingresada");

    private final String code;
    private final String message;
}
