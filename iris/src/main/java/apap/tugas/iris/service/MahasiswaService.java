package apap.tugas.iris.service;

import apap.tugas.iris.model.MahasiswaModel;

import java.util.List;

public interface MahasiswaService {
    void addMahasiswa(MahasiswaModel mahasiwa);

    List<MahasiswaModel> getListMahasiswa();
    List<MahasiswaModel> getListMahasiswaByStatus(int status);

    MahasiswaModel getMahasiswaByNpm(String npm);

    MahasiswaModel getMahasiswaByUuidQuery(String uuid);

    MahasiswaModel updateMahasiswa(MahasiswaModel mahasiswa);

    void deleteMahasiswa(MahasiswaModel mahasiswa);
}
