package com.lawfirm.model.lawProject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ServiceTitle {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private String image;

    @OneToOne(mappedBy = "serviceTitle", cascade = CascadeType.ALL)
    private ServiceBody serviceBody;

}
