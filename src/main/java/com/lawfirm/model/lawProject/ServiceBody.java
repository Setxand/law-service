package com.lawfirm.model.lawProject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ServiceBody {
    @Id
    private Long id;
    private String title;
    @Column(length = 10000  )
    private String body;
}
