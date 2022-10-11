package apap.tugas.iris.repository;

import apap.tugas.iris.model.DosenMataKuliahModel;
import apap.tugas.iris.model.DosenModel;
import apap.tugas.iris.model.MahasiswaModel;
import apap.tugas.iris.model.MataKuliahModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DosenMataKuliahDB extends JpaRepository<DosenMataKuliahModel, String> {

    List<DosenMataKuliahModel> findAllByDosen(DosenModel dosen);

    List<DosenMataKuliahModel> findAllByMataKuliah(MataKuliahModel mk);
    DosenMataKuliahModel findById_IdMataKuliah(Long idmk);

    List<DosenMataKuliahModel> findAllById(Long idmk);
}
