package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
public class UploadImageTrDto implements Serializable {/**
 *
 */
private static final long serialVersionUID = 1L;
    private String userId;
    private String imageBase64;
    private String format;
}