package com.admin.customer.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Este modelo se utiliza para devolver errores en RFC 7807, que creó un esquema generalizado de manejo de errores compuesto por cinco partes.")
@NoArgsConstructor
@Data
public class StandarizedApiExceptionResponse {

    @Schema(description = "El identificador uri único que categoriza el error.", name = "type",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-authorized")
    private String type;

    @Schema(description = "Un mensaje breve y legible por humanos sobre el error.", name = "title",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "The user does not have authorization")
    private String title;

    @Schema(description = "El código de error único", name = "code",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "192")
    private String code;

    @Schema(description = "Una explicación legible por humanos del error.", name = "detail",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "El usuario no tiene los permisos adecuados para acceder al "
            + "recurso, por favor contáctenos en https://example.com")
    private String detail;

    @Schema(description = "Un URI que identifica la ocurrencia específica del error.", name = "instance",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "/errors/authentication/not-authorized/01")
    private String instance;

    public StandarizedApiExceptionResponse(String type, String title, String code, String detail) {
        super();
        this.type = type;
        this.title = title;
        this.code = code;
        this.detail = detail;
    }
}
