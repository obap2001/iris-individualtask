package apap.tugas.iris.service;

import apap.tugas.iris.model.DosenModel;

import java.util.List;

public interface DosenService {
    void addDosen(DosenModel dosen);

    List<DosenModel> getListDosen();

    DosenModel getDosenByNip(String nip);

    DosenModel getDosenByNipQuery(String nip);

    DosenModel updateDosen(DosenModel dosen);

    void deleteDosen(DosenModel dosen);
}
