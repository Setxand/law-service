package com.lawfirm.models.lawProject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ServiceBody {
    @Id
    private Long id;
    private String title;

    @OneToOne
    @JoinColumn(name = "service_entity_title_id")
    private ServiceTitle serviceTitle;
}
