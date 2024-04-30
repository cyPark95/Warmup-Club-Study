package com.group.libraryapp.controller.calulator;

import com.group.libraryapp.controller.dto.Calculator.request.CalculatorAddRequest;
import com.group.libraryapp.controller.dto.Calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.*;

// 해당 Class를 Controller로 등록한다.
// API 진입 지점으로 선언
@RestController
public class CalculatorController {

    // HTTP Method: GET
    // HTTP Path: /add
    @GetMapping("/add")
    public int addTowNumbers(
            // Query를 함수 파라미터로 바인딩
//            @RequestParam("number1") int number1,
//            @RequestParam("number2") int number2
            @ModelAttribute CalculatorAddRequest request
    ) {
        return request.number1() + request.number2();
    }

    // HTTP Method: POST
    // HTTP Path: /multiply
    @PostMapping("/multiply")
    public int multiplyTowNumbers(
            // HTTP Body에 있는 JSON 데이터를 파싱해서 객체로 바인딩
            @RequestBody CalculatorMultiplyRequest request
    ) {
        return request.number1() * request.number2();
    }
}
