package uca.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by andy.lv
 * on: 2019/1/24 16:11
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public class StdDomain {

    @Column(name = "created_on")
    @JsonIgnore
    private LocalDateTime createdOn;

    @Column(name = "updated_on")
    @JsonIgnore
    private LocalDateTime updatedOn;

    @Column(name = "created_by")
    @JsonIgnore
    private String createdBy;

    @Column(name = "updated_by")
    @JsonIgnore
    private String updatedBy;

    @Column(name = "version")
    private Integer version;

}
