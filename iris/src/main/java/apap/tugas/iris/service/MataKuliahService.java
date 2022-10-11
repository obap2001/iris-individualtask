package apap.tugas.iris.service;

import apap.tugas.iris.model.MataKuliahModel;

import java.util.List;

public interface MataKuliahService {
    void addMataKuliah(MataKuliahModel mataKuliah);

    List<MataKuliahModel> getListMataKuliah();
    List<MataKuliahModel> getListMataKuliahQuery(String semester);

    MataKuliahModel getMataKuliahById(Long id);

    MataKuliahModel getMataKuliahByIdQuery(Long id);

    MataKuliahModel updateMataKuliah(MataKuliahModel mataKuliah);

    void deleteMataKuliah(MataKuliahModel mataKuliah);
}
