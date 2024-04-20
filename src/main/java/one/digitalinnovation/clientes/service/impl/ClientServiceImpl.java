package one.digitalinnovation.clientes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.clientes.model.Adress;
import one.digitalinnovation.clientes.model.AdressRepository;
import one.digitalinnovation.clientes.model.Client;
import one.digitalinnovation.clientes.model.ClientRepository;
import one.digitalinnovation.clientes.service.ClientService;
import one.digitalinnovation.clientes.service.ViaCepService;

import java.util.Optional;


@Service
public class ClientServiceImpl implements ClientService {
    // Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdressRepository adressRepository;
    @Autowired
    private ViaCepService viaCepService;

    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Client> buscarTodos() {
        // Buscar todos os Clientes.
        return clientRepository.findAll();
    }

    @Override
    public Client buscarPorId(Long id) {
        // Buscar Cliente por ID.
        Optional<Client> cliente = clientRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Client cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Client cliente) {
        // Buscar Cliente por ID, caso exista:
        Optional<Client> clienteBd = clientRepository.findById(id);
        if (clienteBd.isPresent()) {
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        // Deletar Cliente por ID.
        clientRepository.deleteById(id);
    }

    private void salvarClienteComCep(Client cliente) {
        // Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = cliente.getAdress().getCep();
        Adress endereco = adressRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Adress novoEndereco = viaCepService.consultarCep(cep);
            adressRepository.save(novoEndereco);
            return novoEndereco;
        });
        cliente.setAdress(endereco);
        // Inserir Cliente, vinculando o Endereco (novo ou existente).
        clientRepository.save(cliente);
    }

}
