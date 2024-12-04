package com.amritraj.companyms.company;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Company {
    @Id
    private String companyId;
    private String name;
    private String description;
    private Double rating;
}
