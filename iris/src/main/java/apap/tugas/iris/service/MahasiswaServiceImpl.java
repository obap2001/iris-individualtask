package apap.tugas.iris.service;

import apap.tugas.iris.model.MahasiswaModel;
import apap.tugas.iris.repository.MahasiswaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MahasiswaServiceImpl implements MahasiswaService{
    @Autowired
    MahasiswaDB mahasiswaDb;

    @Override
    public void addMahasiswa(MahasiswaModel mahasiwa){
        mahasiswaDb.save(mahasiwa);
    }

    @Override
    public List<MahasiswaModel> getListMahasiswa(){
        return mahasiswaDb.findAll();
    }

    @Override
    public MahasiswaModel getMahasiswaByNpm(String npm){
        Optional<MahasiswaModel> mahasiswa = mahasiswaDb.findByNpm(npm);
        if (mahasiswa.isPresent()){
            return mahasiswa.get();
        }else return null;
    }

    @Override
    public MahasiswaModel getMahasiswaByUuidQuery(String uuid){
        Optional<MahasiswaModel> mahasiswa = mahasiswaDb.findByIdUsingQuery(uuid);
        if (mahasiswa.isPresent()){
            return mahasiswa.get();
        }else return null;
    }

    @Override
    public MahasiswaModel updateMahasiswa(MahasiswaModel mahasiswa){
        mahasiswaDb.save(mahasiswa);
        return mahasiswa;
    }

    @Override
    public void deleteMahasiswa(MahasiswaModel mahasiswa){
        mahasiswaDb.delete(mahasiswa);
    }
}
