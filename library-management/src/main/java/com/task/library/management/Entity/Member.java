// Member.java
package com.task.library.management.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "contact_no")
    private Integer contactNo;

    @Column(name = "membership_type")
    private String membershipType;

    @Column(name = "address")
    private String address;

    @Column(name = "date_of_membership")
    private LocalDate dateOfMembership;

    @Column(name = "status")
    private String status; // (e.g., Active, Suspended)

    // -------------------------------
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) //, fetch = FetchType.LAZY
    private List<BorrowingRecord> borrowingRecords;

    //--------------------------------
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) // , fetch = FetchType.LAZY
//    private List<Fine> fines;
    
    //--------------------------------
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL) // , fetch = FetchType.LAZY
    private Fine fine;
}