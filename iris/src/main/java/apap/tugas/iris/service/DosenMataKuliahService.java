package apap.tugas.iris.service;

import apap.tugas.iris.model.DosenMataKuliahModel;
import apap.tugas.iris.model.DosenModel;
import apap.tugas.iris.model.MataKuliahModel;

import java.util.List;

public interface DosenMataKuliahService {
    List<DosenMataKuliahModel> getListDosenMataKuliahByDosen(DosenModel dosen);
    List<DosenMataKuliahModel> getListDosenMataKuliah(MataKuliahModel mk);
    void addDosenMataKuliah(DosenMataKuliahModel dmk);
    List<DosenMataKuliahModel> getListDosenMataKuliah();
    void deleteDosenMataKuliah(Long idmk);
    void deleteAll(Long idmk);
}
