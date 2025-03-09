package org.example.automanager.dto.abstracts;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractClientInfoRequest {
    @Size(min = 2, max = 32, message = "Имя пользователя должно содержать от 2 до 32 символов")
//    @NotBlank(message = "Имя пользователя не может быть пустыми")
    private String name;

    @Size(min = 2, max = 32, message = "Фамилия пользователя должно содержать от 2 до 32 символов")
//    @NotBlank(message = "Фамилия пользователя не может быть пустыми")
    private String surname;

    @Size(max = 64, message = "Длина пароля должна быть не более 64 символов")
    private String password;
}
