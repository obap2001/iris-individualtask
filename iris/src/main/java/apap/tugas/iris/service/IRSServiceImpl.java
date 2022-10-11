package apap.tugas.iris.service;

import apap.tugas.iris.model.IRSModel;
import apap.tugas.iris.repository.IRSDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class IRSServiceImpl implements IRSService{
    @Autowired
    IRSDB irsDb;

    @Override
    public void addIRS(IRSModel irs){
        irsDb.save(irs);
    }

    @Override
    public List<IRSModel> getListIRS(){
        return irsDb.findAll();
    }

    @Override
    public IRSModel getIRSById(Long id){
        Optional<IRSModel> irs = irsDb.findById(id);
        if (irs.isPresent()){
            return irs.get();
        }else return null;
    }

    @Override
    public IRSModel getIRSByIdUsingQuery(Long id){
        Optional<IRSModel> irs = irsDb.findByIdUsingQuery(id);
        if (irs.isPresent()){
            return irs.get();
        }else return null;
    }

    @Override
    public IRSModel getIRSBySemester(String semester){
        Optional<IRSModel> irs = irsDb.findBySemester(semester);
        if (irs.isPresent())
            return irs.get();
        else
            return null;
    }

    @Override
    public IRSModel updateIRS(IRSModel irs){
        irsDb.save(irs);
        return irs;
    }

    @Override
    public void deleteIRS(IRSModel irs){
        irsDb.delete(irs);
    }
}
