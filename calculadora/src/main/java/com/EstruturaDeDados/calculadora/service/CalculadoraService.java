package com.EstruturaDeDados.calculadora.service;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class CalculadoraService {

	// Método principal
    public double calcularExpressao(String expressao) throws Exception {
        if (expressao == null || expressao.trim().isEmpty()) {
            throw new Exception("Expressão vazia");
        }

        Queue<String> fila = new LinkedList<>();
        Deque<Double> pilha = new LinkedList<>(); 

        String[] tokens = expressao.trim().split(" "); 
        for (String token : tokens) {
            fila.offer(token); 
        }


        while (!fila.isEmpty()) { 
            String token = fila.poll();

            if (isNumero(token)) {
                pilha.push(Double.parseDouble(token));
            } else if (isOperadorValido(token)) {
                if (pilha.size() < 2) {
                    throw new Exception("Operandos insuficientes para o operador '" + token + "'");
                }

                double b = pilha.pop();
                double a = pilha.pop();
                double resultado;

                switch (token) {
                    case "+":
                        resultado = a + b;
                        break;
                    case "-":
                        resultado = a - b;
                        break;
                    case "*":
                        resultado = a * b;
                        break;
                    case "/":
                        if (b == 0) throw new Exception("Divisão por zero");
                        resultado = a / b;
                        break;
                    case "%":
                        if (b == 0) throw new Exception("Divisão por zero (módulo)");
                        resultado = a % b;
                        break;
                    default:
                        throw new Exception("Operador inválido: " + token);
                }

                pilha.push(resultado);
            } else {
                throw new Exception("Token inválido: " + token);
            }
        }

        // Validação final
        if (pilha.size() != 1) {
            throw new Exception("Expressão malformada. Verifique operandos e operadores.");
        }

        return pilha.pop();
    }

    // Método auxiliar
    private boolean isNumero(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    // Método auxiliar
    private boolean isOperadorValido(String op) {
        return op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/") || op.equals("%");
    }
}