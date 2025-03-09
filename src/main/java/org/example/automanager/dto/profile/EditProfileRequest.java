package org.example.automanager.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.automanager.dto.abstracts.AbstractClientInfoRequest;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EditProfileRequest extends AbstractClientInfoRequest {
    @DateTimeFormat
    private LocalDateTime birthday;

    @Size(max = 64, message = "Длина пароля должна быть не более 64 символов")
    private String newPassword;
}
