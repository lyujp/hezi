package com.lyujp.heziservice.controller;

import com.lyujp.heziservice.dto.common.IdDto;
import com.lyujp.heziservice.dto.common.ResDto;
import com.lyujp.heziservice.entity.WorkBench;
import com.lyujp.heziservice.entity.WorkbenchCategory;
import com.lyujp.heziservice.mapper.UserMapper;
import com.lyujp.heziservice.mapper.WorkbenchMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
public class WorkbenchController {

    private final WorkbenchMapper workbenchMapper;
    private final UserMapper userMapper;

    @RequestMapping("/admin/workbench/getlist")
    public ResDto<List<WorkBench>> getList(){
        return ResDto.success(workbenchMapper.getList());
    }

    @RequestMapping("/admin/workbench/category/getlist")
    public ResDto<List<WorkbenchCategory>> getCategoryList(){
        return ResDto.success(workbenchMapper.getCategoryList().stream().filter(
                category -> category.getStatus() == 1).toList());
    }

    @PostMapping("/admin/workbench/add")
    public ResDto<Void> add(@RequestBody WorkBench workbench){
        if(workbench == null){
            return ResDto.fail(500,"参数错误");
        }
        if(workbench.getTitle() == null || workbench.getTitle().isBlank()){
            return ResDto.fail(500,"标题不得为空");
        }

        if(workbench.getImg() == null || workbench.getImg().isBlank()){
            return ResDto.fail(500,"图片不得为空");
        }

        if(workbench.getUrl() == null || workbench.getUrl().isBlank()){
            return ResDto.fail(500,"路径不得为空");
        }

        if(workbench.getCategoryId() == null){
            return ResDto.fail(500,"分类不得为空");
        }

        if(workbench.getStatus() == null){
            workbench.setStatus(1L);
        }

        //查询分类是否存在
        List<WorkbenchCategory> workbenchCategoryList = workbenchMapper.getCategoryList();
        Stream<WorkbenchCategory> selectedCategory = workbenchCategoryList.stream().filter(
                workbenchCategory -> Objects.equals(workbenchCategory.getId(), workbench.getCategoryId())
        );
        if(selectedCategory.findAny().isEmpty()){
            return ResDto.fail(500,"分类不存在");
        }
        workbenchMapper.add(workbench.getTitle(),workbench.getSubtitle(),workbench.getImg(),workbench.getUrl(),
                workbench.getCategoryId(),workbench.getStatus());
        return ResDto.success();
    }

    @PostMapping("/admin/workbench/edit")
    public ResDto<Void> edit(@RequestBody WorkBench workbench){
        if(workbench == null){
            return ResDto.fail(500,"参数错误");
        }
        if(workbench.getTitle() == null || workbench.getTitle().isBlank()){
            return ResDto.fail(500,"标题不得为空");
        }

        if(workbench.getImg() == null || workbench.getImg().isBlank()){
            return ResDto.fail(500,"图片不得为空");
        }

        if(workbench.getUrl() == null || workbench.getUrl().isBlank()){
            return ResDto.fail(500,"路径不得为空");
        }

        if(workbench.getCategoryId() == null){
            return ResDto.fail(500,"分类不得为空");
        }

        if(workbench.getStatus() == null){
            workbench.setStatus(1L);
        }
        if(workbench.getId() == null){
            return ResDto.fail(500,"id 不得为空");
        }

        //查询分类是否存在
        List<WorkbenchCategory> workbenchCategoryList = workbenchMapper.getCategoryList();
        Stream<WorkbenchCategory> selectedCategory = workbenchCategoryList.stream().filter(
                workbenchCategory -> Objects.equals(workbenchCategory.getId(), workbench.getCategoryId())
        );
        if(selectedCategory.findAny().isEmpty()){
            return ResDto.fail(500,"分类不存在");
        }
        workbenchMapper.edit(workbench.getId(), workbench.getTitle(),workbench.getSubtitle(),workbench.getImg(),workbench.getUrl(),
                workbench.getCategoryId(),workbench.getStatus());
        return ResDto.success();
    }

    @PostMapping("/admin/workbench/delete")
    public ResDto<Void> delete(@RequestBody IdDto idDto){
        if(idDto==null || idDto.getId() == null){
            return ResDto.fail(500,"id 不得为空");
        }
        workbenchMapper.delete(idDto.getId());
        return ResDto.success();
    }


    @PostMapping("/admin/workbench/category/add")
    public ResDto<Void> addCategory(@RequestBody WorkbenchCategory workbenchCategory){
        if(workbenchCategory == null){
            return ResDto.fail(500,"参数错误");
        }
        if(workbenchCategory.getName() == null || workbenchCategory.getName().isBlank()){
            return ResDto.fail(500,"名称不得为空");
        }

        if(workbenchCategory.getWeight() == null){
            workbenchCategory.setWeight(0L);
        }

        if(workbenchCategory.getStatus() == null){
            workbenchCategory.setStatus(1L);
        }

        workbenchMapper.addCategory(workbenchCategory.getName(),workbenchCategory.getWeight(),workbenchCategory.getStatus());
        return ResDto.success();
    }

    @PostMapping("/admin/workbench/category/edit")
    public ResDto<Void> edit(@RequestBody WorkbenchCategory workbenchCategory){
        if(workbenchCategory == null){
            return ResDto.fail(500,"参数错误");
        }
        if(workbenchCategory.getName() == null || workbenchCategory.getName().isBlank()){
            return ResDto.fail(500,"名称不得为空");
        }

        if(workbenchCategory.getWeight() == null){
            workbenchCategory.setWeight(0L);
        }

        if(workbenchCategory.getStatus() == null){
            workbenchCategory.setStatus(1L);
        }
        if(workbenchCategory.getId() == null){
            return ResDto.fail(500,"id 不得为空");
        }

        workbenchMapper.editCategory(workbenchCategory.getId(),workbenchCategory.getName(),workbenchCategory.getWeight(),workbenchCategory.getStatus());
        return ResDto.success();
    }

    @PostMapping("/admin/workbench/category/delete")
    public ResDto<Void> deleteCategory(@RequestBody IdDto idDto){
        if(idDto==null || idDto.getId() == null){
            return ResDto.fail(500,"id 不得为空");
        }
        workbenchMapper.deleteCategory(idDto.getId());
        return ResDto.success();
    }

}
