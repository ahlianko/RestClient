package com.andriihlianko.testproject.model.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@Getter
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
@JsonIgnoreProperties(value = { "Id" , "InterestRate"})
public class Customer {
    @Id
    @JsonProperty
    Long ID;
    @JsonProperty
    double  Amount,ApplicationSignedHour,ApplicationSignedWeekday, DebtToIncome,Education, EmploymentStatus,
            ExistingLiabilities;
    @JsonProperty
    double gender;
    @JsonProperty
    double HomeOwnershipType,IncomeFromPrincipalEmployer,IncomeTotal,
            LoanDuration, MaritalStatus, NoOfPreviousLoansBeforeLoan,OccupationArea,UseOfLoan,VerificationType, PreviousScore;
    @JsonProperty
    String  status,City,Country,CreditScoreEsEquifaxRisk,dateOfBirth,EmploymentDurationCurrentEmployer,
            EmploymentPosition;
    @JsonProperty(value = "Interest rate (APR)")
    double InterestRate;
    @JsonProperty
    String LoanDate,NewCreditCustomer, WorkExperience,Defaulted, DefaultDate;

    public Customer() {

    }
}
