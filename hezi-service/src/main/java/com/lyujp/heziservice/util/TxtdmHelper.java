package com.lyujp.heziservice.util;

import com.lyujp.heziservice.entity.TxtdmEntity;

import java.util.List;
import java.util.Objects;

public class TxtdmHelper {
    public static boolean isExist(List<TxtdmEntity> txtdmEntitys, String name, Long code){
        return txtdmEntitys.stream()
                .anyMatch(txtdmEntity -> txtdmEntity.getName().equals(name) && Objects.equals(txtdmEntity.getCode(), code));
    }

    public static boolean isExist(List<TxtdmEntity> txtdmEntitys, String name, String note){
        return txtdmEntitys.stream()
                .anyMatch(txtdmEntity -> txtdmEntity.getName().equals(name) && Objects.equals(txtdmEntity.getNote(), note));
    }
}
