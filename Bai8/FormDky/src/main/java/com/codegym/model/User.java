package com.codegym.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class User implements Validator {

    private Long id;

    @NotEmpty(message = "First name không được để trống")
    @Size(min = 5, max = 45, message = "First name phải có độ dài từ 5 đến 45 ký tự")
    private String firstName;

    @NotEmpty(message = "Last name không được để trống")
    @Size(min = 5, max = 45, message = "Last name phải có độ dài từ 5 đến 45 ký tự")
    private String lastName;

    @NotEmpty(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0[0-9]{9,10}$", message = "Số điện thoại không đúng quy tắc (phải bắt đầu bằng số 0 và có 10 đến 11 chữ số)")
    private String phoneNumber;

    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 18, message = "Tuổi phải lớn hơn hoặc bằng 18")
    private Integer age;

    @NotEmpty(message = "Email không được để trống")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Email không đúng quy tắc định dạng")
    private String email;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String phoneNumber, Integer age, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        String phone = user.getPhoneNumber();
        if (phone != null && !phone.trim().isEmpty()) {
            if (!phone.startsWith("0")) {
                if (!errors.hasFieldErrors("phoneNumber")) {
                    errors.rejectValue("phoneNumber", "phoneNumber.startsWith", "Số điện thoại phải bắt đầu bằng số 0");
                }
            } else if (phone.length() < 10 || phone.length() > 11) {
                if (!errors.hasFieldErrors("phoneNumber")) {
                    errors.rejectValue("phoneNumber", "phoneNumber.length", "Số điện thoại phải có từ 10 đến 11 số");
                }
            } else if (!phone.matches("^[0-9]+$")) {
                if (!errors.hasFieldErrors("phoneNumber")) {
                    errors.rejectValue("phoneNumber", "phoneNumber.matches", "Số điện thoại chỉ được chứa các chữ số");
                }
            }
        }
    }
}
