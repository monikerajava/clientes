package one.digitalinnovation.clientes.model;

import one.digitalinnovation.clientes.model.Adress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends CrudRepository<Adress, String> {
}
