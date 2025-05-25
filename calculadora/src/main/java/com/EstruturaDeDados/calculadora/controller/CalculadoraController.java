package com.EstruturaDeDados.calculadora.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.EstruturaDeDados.calculadora.service.CalculadoraService;

@RestController
@RequestMapping()
public class CalculadoraController {

	@Autowired
	private CalculadoraService calculadoraService;

	@GetMapping
	public ModelAndView form() {
		return new ModelAndView("index"); // Thymeleaf renderiza templates/index.html
	}
	
	@PostMapping("/calcular")
	public ModelAndView calcular(@RequestParam String expressao) { 
		ModelAndView mv = new ModelAndView("index"); 
		try {
			double resultado = calculadoraService.calcularExpressao(expressao); 
			mv.addObject("resultado", resultado); 
		} catch (Exception e) {
			mv.addObject("erro", e.getMessage());
		}
		mv.addObject("expressao", expressao);
		return mv; 
	}
}