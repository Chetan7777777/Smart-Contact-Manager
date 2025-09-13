package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserForm {
    @NotBlank(message = "Username is required!")
    @Size(min = 3, message = "Minimum 3 characters required!")
    private String name;
    @Email(message = "Invalid Email Address!")
    private String email;
    @NotBlank(message = "Password is required!")
    @Size(min = 6 , message = "Minimum 6 characters required!")
    private String password;
    @NotBlank(message = "About is required!")
    private String about;
    @Size(min = 8 , max = 12 , message = "Invalid Phone Number!")
    private String phoneNumber;
}
