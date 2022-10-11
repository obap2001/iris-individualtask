package apap.tugas.iris.repository;

import apap.tugas.iris.model.DosenModel;
import apap.tugas.iris.model.MataKuliahModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DosenDB extends JpaRepository<DosenModel, String> {
    @Override
    Optional<DosenModel> findById(String s);

    @Query("SELECT c FROM DosenModel c WHERE c.nip = :nip")
    Optional<DosenModel> findByIdUsingQuery(@Param("nip") String nip);
}
