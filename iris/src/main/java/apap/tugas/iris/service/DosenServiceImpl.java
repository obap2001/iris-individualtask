package apap.tugas.iris.service;

import apap.tugas.iris.model.DosenModel;
import apap.tugas.iris.repository.DosenDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DosenServiceImpl implements DosenService{
    @Autowired
    DosenDB dosenDb;

    @Override
    public void addDosen(DosenModel dosen){
        dosenDb.save(dosen);
    }

    @Override
    public List<DosenModel> getListDosen(){
        return dosenDb.findAll();
    }

    @Override
    public DosenModel getDosenByNip(String nip){
        Optional<DosenModel> dosen = dosenDb.findById(nip);
        if (dosen.isPresent()){
            return dosen.get();
        }else return null;
    }

    @Override
    public DosenModel getDosenByNipQuery(String nip){
        Optional<DosenModel> dosen = dosenDb.findByIdUsingQuery(nip);
        if (dosen.isPresent()){
            return dosen.get();
        }else return null;
    }

    @Override
    public DosenModel updateDosen(DosenModel dosen){
        dosenDb.save(dosen);
        return dosen;
    }

    @Override
    public void deleteDosen(DosenModel dosen){
        dosenDb.delete(dosen);
    }

}
