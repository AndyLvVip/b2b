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
 * on: 2019/1/24 15:52
 */
@Entity
@Table(name = "file_item_info")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FileItemInfo extends StdStrDomain {

    @Column(name = "file_set_info_id")
    private String fileSetInfoId;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "size")
    private Long size;

    @Column(name = "sequence")
    private Integer sequence;

}
