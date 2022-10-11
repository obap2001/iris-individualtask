package apap.tugas.iris.service;

import apap.tugas.iris.model.MataKuliahModel;
import apap.tugas.iris.repository.MataKuliahDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MataKuliahServiceImpl implements MataKuliahService{
    @Autowired
    MataKuliahDB mataKuliahDb;

    @Override
    public void addMataKuliah(MataKuliahModel mataKuliah){
        mataKuliahDb.save(mataKuliah);
    }

    @Override
    public List<MataKuliahModel> getListMataKuliah(){
        return mataKuliahDb.findAll();
    }
    @Override
    public List<MataKuliahModel> getListMataKuliahQuery(String semester){
        return mataKuliahDb.findAllByQuery(semester);
    }

    @Override
    public MataKuliahModel getMataKuliahById(Long id){
        Optional<MataKuliahModel> mataKuliah = mataKuliahDb.findById(id);
        if (mataKuliah.isPresent()){
            return mataKuliah.get();
        }else return null;
    }

    @Override
    public MataKuliahModel getMataKuliahByIdQuery(Long id){
        Optional<MataKuliahModel> mataKuliah = mataKuliahDb.findByIdUsingQuery(id);
        if (mataKuliah.isPresent()){
            return mataKuliah.get();
        }else return null;
    }

    @Override
    public MataKuliahModel updateMataKuliah(MataKuliahModel mataKuliah){
        mataKuliahDb.save(mataKuliah);
        return mataKuliah;
    }

    @Override
    public void deleteMataKuliah(MataKuliahModel mataKuliah){
        mataKuliahDb.delete(mataKuliah);
    }
}
