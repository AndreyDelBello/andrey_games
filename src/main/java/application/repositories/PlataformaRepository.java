package application.repositories;
import org.springframework.data.repository.CrudRepository;
import application.models.Genero;

public interface PlataformaRepository extends CrudRepository<Genero, Integer>{
    
}