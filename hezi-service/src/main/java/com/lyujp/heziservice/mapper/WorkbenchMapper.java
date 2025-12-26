package com.lyujp.heziservice.mapper;

import com.lyujp.heziservice.entity.WorkBench;
import com.lyujp.heziservice.entity.WorkbenchCategory;

import java.util.List;

public interface WorkbenchMapper {
    List<WorkBench> getList();
    Long add(String title,String subtitle,String img,String url,Long categoryId,Long status);
    void edit(Long id,String title,String subtitle,String img,String url,Long categoryId,Long status);
    void delete(Long id);
    List<WorkbenchCategory> getCategoryList();
    Long addCategory(String name, Long weight, Long status);
    void editCategory(Long id,String name, Long weight, Long status);
    void deleteCategory(Long id);

}
