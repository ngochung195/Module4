package com.codegym.service;

import com.codegym.model.MedicalDeclaration;
import org.springframework.stereotype.Service;

@Service
public class MedicalDeclarationService {

    private MedicalDeclaration currentDeclaration;

    public MedicalDeclaration getDeclaration() {
        return currentDeclaration;
    }

    public void save(MedicalDeclaration declaration) {
        this.currentDeclaration = declaration;
    }

    public void update(MedicalDeclaration declaration) {
        this.currentDeclaration = declaration;
    }

    public void saveOrUpdate(MedicalDeclaration declaration) {
        this.currentDeclaration = declaration;
    }
}
