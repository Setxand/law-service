package com.lawfirm.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ServiceTitle {
    @Id
    private Long id;
    private String title;
    private String content;
    private String image;
    @OneToOne(mappedBy = "serviceTitle", cascade = CascadeType.ALL)
    private ServiceBody serviceBody;

}
