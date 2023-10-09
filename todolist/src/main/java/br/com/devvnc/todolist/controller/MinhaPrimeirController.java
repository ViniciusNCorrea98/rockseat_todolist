package br.com.devvnc.todolist.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
Quando pretendemos que a class retorne alem de um objeto/string
utilizamos a annotation Controller, como uma pagina ou componente.
 */
@RestController
@RequestMapping("/primeiroRota")
public class MinhaPrimeirController {
    /**

    Dentro das classes, temos metodos e criamos eles a partir dos passos:
    1- Qual e o tipo do modificador public/private
    2- Qual e o tipo do dado (String/int/ou pode criar sua propria classe)
    3- O nome do metodo
    */
    @GetMapping("/primeiroMetodo")
    public String primeiraMensagem() {
        return "Funcionou!";
    }
}
