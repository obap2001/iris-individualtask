package apap.tugas.iris.repository;

import apap.tugas.iris.model.MataKuliahModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MataKuliahDB extends JpaRepository<MataKuliahModel, Long> {
    @Override
    Optional<MataKuliahModel> findById(Long id);

    List<MataKuliahModel> findAll();
    @Query("SELECT c FROM MataKuliahModel c WHERE c.semester LIKE CONCAT('%',:semester,'%')")
    List<MataKuliahModel> findAllByQuery(@Param("semester") String semester);

    @Query("SELECT c FROM MataKuliahModel c WHERE c.id = :id")
    Optional<MataKuliahModel> findByIdUsingQuery(@Param("id") Long id);
}
