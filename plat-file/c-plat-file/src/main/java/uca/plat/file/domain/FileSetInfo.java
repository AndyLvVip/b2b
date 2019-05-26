package uca.plat.file.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uca.base.domain.StdStrDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by andy.lv
 * on: 2019/1/24 16:18
 */
@Entity
@Table(name = "file_set_info")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FileSetInfo extends StdStrDomain {

    @Column(name = "file_src_remark")
    private String fileSrcRemark;

}
