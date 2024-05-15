package com.fyp.eduflexconnect.DTOs;

import lombok.Data;

@Data
public class FilesDto {

    private  Long id;
    private String name;
    private String type;
//    @Lob
//    @Column(name = "file_data", columnDefinition = "LONGBLOB")
    private byte[] fileData;

}
