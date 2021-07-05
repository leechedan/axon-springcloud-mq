package org.github.axon.tag.contract.entity;


import org.github.axon.tag.contract.domain.contract.ContractInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContractView implements ContractInterface {

    @Id
    @Column(length = 64)
    private Long id;

    private String name;

    private String partyA;

    private String partyB;

    private String industryName;

    private boolean deleted = false;

}
