package one.digitalinnovation.clientes.service;

import one.digitalinnovation.clientes.model.Adress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepService {
    @GetMapping("/{cep}/json/")
    Adress consultarCep(@PathVariable("cep") String cep);

}
