package com.excel.importer.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "filemetadata")
@Getter
@Setter
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long filemetadata_id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "size_file")
    private String sizeFile;

    @Column(name = "upload_date")
    private Date uploadDate;

}
