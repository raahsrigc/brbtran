package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;


@Getter
@Setter
public class InsertHistoryTrDto implements Serializable {


        private static final long serialVersionUID = 1L;
        private String id;
        private int objectId;
        private String comment;
}
