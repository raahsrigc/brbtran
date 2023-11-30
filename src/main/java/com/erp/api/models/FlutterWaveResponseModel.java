package com.erp.api.models;

import java.util.LinkedHashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlutterWaveResponseModel {

    private String status;

    @Override
    public String toString() {
        return "{" +
                "status='" + status + '\'' +
                ", success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    private String success;
    private String message;
    private LinkedHashMap<String,Object> data;
}
