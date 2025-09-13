package com.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContactForm {

    @NotBlank(message = "Name is Required!")
    private String name;

    @Email(message = "Email is invalid!" )
    private String email;

    @NotBlank(message = "Phone No. is Required!")
    @Pattern(regexp = "^[0-9]{10}$" ,message = "  Phone NO. is invalid!")
    private String phoneNo;

    @NotBlank(message = "Address is requied!")
    private String address;

    private String description;
    private boolean favorite;
    private String websiteLink;
    private String LinkedInLink;
    private MultipartFile contactImage;


}
