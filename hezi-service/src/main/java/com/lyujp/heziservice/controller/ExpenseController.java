package com.lyujp.heziservice.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lyujp.heziservice.dto.common.ResDto;
import com.lyujp.heziservice.dto.expense.ExpenseRecordDto;
import com.lyujp.heziservice.dto.expense.ExpenseRecordOptionsDto;
import com.lyujp.heziservice.dto.expense.ExpenseSummaryDto;
import com.lyujp.heziservice.mapper.ExpenseMapper;
import com.lyujp.heziservice.mapper.TxtDmMapper;
import com.lyujp.heziservice.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/expense")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseMapper expenseMapper;
    private final TxtDmMapper txtDmMapper;
    private final ExpenseService expenseService;

    //显示首页统计信息
    @RequestMapping("/get_summary")
    public ResDto<ExpenseSummaryDto> getSummary(){
        ExpenseSummaryDto expenseSummaryDto = expenseMapper.getSummary(Long.parseLong(StpUtil.getLoginId().toString()));
        return ResDto.success(expenseSummaryDto);
    }

    //获取记账时需要用到的字段
    @RequestMapping("/get_record_options")
    public ResDto<ExpenseRecordOptionsDto> getRecordOptions(){
        return ResDto.success(expenseService.getRecordOptions());
    }

    @PostMapping("/record")
    public ResDto<Void> record(@RequestBody ExpenseRecordDto expenseRecordDto){
        return expenseService.record(expenseRecordDto);
    }
}
