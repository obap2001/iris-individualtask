package apap.tugas.iris.service;

import apap.tugas.iris.model.IRSModel;

import java.util.List;

public interface IRSService {
    void addIRS(IRSModel irs);

    List<IRSModel> getListIRS();

    IRSModel getIRSById(Long id);

    IRSModel getIRSByIdUsingQuery(Long id);

    IRSModel getIRSBySemester(String semester);

    IRSModel updateIRS(IRSModel irs);

    void deleteIRS(IRSModel irs);
}
