package apap.tugas.iris.service;

import apap.tugas.iris.model.DosenMataKuliahKeyModel;
import apap.tugas.iris.model.DosenMataKuliahModel;
import apap.tugas.iris.model.DosenModel;
import apap.tugas.iris.model.MataKuliahModel;
import apap.tugas.iris.repository.DosenMataKuliahDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DosenMataKuliahServiceImpl implements DosenMataKuliahService{
    @Autowired
    DosenMataKuliahDB dosenMataKuliahDB;
    @Override
    public List<DosenMataKuliahModel> getListDosenMataKuliahByDosen(DosenModel dosen){
        return dosenMataKuliahDB.findAllByDosen(dosen);

    }

    @Override
    public List<DosenMataKuliahModel> getListDosenMataKuliah(MataKuliahModel mk){
        return dosenMataKuliahDB.findAllByMataKuliah(mk);
    }
    @Override
    public void deleteDosenMataKuliah(Long idmk){
        DosenMataKuliahModel dmk = dosenMataKuliahDB.findById_IdMataKuliah(idmk);
        dosenMataKuliahDB.delete(dmk);
    }
    @Override
    public void deleteAll(Long idmk){
        List<DosenMataKuliahModel> listDmk = dosenMataKuliahDB.findAll();
        for (DosenMataKuliahModel dmk : listDmk){
            Long idmkrec = dmk.getMataKuliah().getId();
            if (idmkrec.equals(idmk))
                dosenMataKuliahDB.delete(dmk);
        }
    }
    @Override
    public List<DosenMataKuliahModel> getListDosenMataKuliah(){
        return dosenMataKuliahDB.findAll();
    }
    @Override
    public void addDosenMataKuliah(DosenMataKuliahModel dmk){
//        DosenModel dosen = dmk.getDosen();
//        MataKuliahModel mk = dmk.getMataKuliah();
//        String ruangKelas = dmk.getRuangKelas();
//        dosenMataKuliahDB.save(new DosenMataKuliahModel(null,dosen,mk,ruangKelas));
        dosenMataKuliahDB.save(dmk);
    }
}
