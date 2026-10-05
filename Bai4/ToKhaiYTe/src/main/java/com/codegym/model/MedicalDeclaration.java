package com.codegym.model;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class MedicalDeclaration {

    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Năm sinh không được để trống")
    private String birthYear;

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @NotBlank(message = "Quốc tịch không được để trống")
    private String nationality;

    @NotBlank(message = "Số CMND/CCCD không được để trống")
    private String idCard;

    @NotBlank(message = "Thông tin đi lại không được để trống")
    private String travelInformation;

    private List<String> symptoms = new ArrayList<>();

    private List<String> medicalHistory = new ArrayList<>();

    @NotBlank(message = "Thông tin liên hệ không được để trống")
    private String contactInformation;

    public MedicalDeclaration() {
        this.nationality = "Việt Nam";
    }

    public MedicalDeclaration(String fullName, String birthYear, String gender, String nationality,
                              String idCard, String travelInformation, List<String> symptoms,
                              List<String> medicalHistory, String contactInformation) {
        this.fullName = fullName;
        this.birthYear = birthYear;
        this.gender = gender;
        this.nationality = nationality;
        this.idCard = idCard;
        this.travelInformation = travelInformation;
        this.symptoms = symptoms != null ? symptoms : new ArrayList<>();
        this.medicalHistory = medicalHistory != null ? medicalHistory : new ArrayList<>();
        this.contactInformation = contactInformation;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTravelInformation() {
        return travelInformation;
    }

    public void setTravelInformation(String travelInformation) {
        this.travelInformation = travelInformation;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms != null ? symptoms : new ArrayList<>();
    }

    public List<String> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = medicalHistory != null ? medicalHistory : new ArrayList<>();
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}
