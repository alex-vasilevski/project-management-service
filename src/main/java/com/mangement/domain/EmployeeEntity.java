package com.mangement.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@NoArgsConstructor
@Getter
@Setter
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double salary;
    @Enumerated(EnumType.STRING)
    private Division division;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne
    private PersonalInformationEntity personalInformation;

    public EmployeeEntity(Double salary, Division division, Role role, PersonalInformationEntity personalInformation) {
        this.salary = salary;
        this.division = division;
        this.role = role;
        this.personalInformation = personalInformation;
    }
}
