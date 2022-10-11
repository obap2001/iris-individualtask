package apap.tugas.iris.repository;

import apap.tugas.iris.model.DosenModel;
import apap.tugas.iris.model.IRSModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IRSDB extends JpaRepository<IRSModel, Long> {
    @Override
    Optional<IRSModel> findById(Long aLong);

    Optional<IRSModel> findBySemester(String semester);
    @Query("SELECT c FROM IRSModel c WHERE c.id = :id")
    Optional<IRSModel> findByIdUsingQuery(@Param("id") Long id);
}
