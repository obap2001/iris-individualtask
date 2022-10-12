package apap.tugas.iris.repository;

import apap.tugas.iris.model.IRSModel;
import apap.tugas.iris.model.MahasiswaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MahasiswaDB extends JpaRepository<MahasiswaModel, String> {
    @Override
    Optional<MahasiswaModel> findById(String s);
    Optional<MahasiswaModel> findByNpm(String npm);

    List<MahasiswaModel> findAllByStatusMahasiswa(int status);
    @Query("SELECT c FROM MahasiswaModel c WHERE c.uuid = :uuid")
    Optional<MahasiswaModel> findByIdUsingQuery(@Param("uuid") String uuid);
}
