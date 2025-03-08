package org.example.automanager.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import org.example.automanager.dto.abstracts.AbstractClientInfoRequest;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpRequest extends AbstractClientInfoRequest {
    @Size(min = 5, max = 32, message = "Адрес электронной почты должен содержать от 5 до 32 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;
}
