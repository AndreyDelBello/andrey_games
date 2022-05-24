package application.repositories;
import org.springframework.data.repository.CrudRepository;
import application.models.Genero;

public interface JogoRepository extends CrudRepository<Genero, Integer>{
    
}