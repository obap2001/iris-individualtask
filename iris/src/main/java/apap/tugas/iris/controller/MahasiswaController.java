package apap.tugas.iris.controller;

import apap.tugas.iris.model.IRSModel;
import apap.tugas.iris.model.MahasiswaModel;
import apap.tugas.iris.model.MataKuliahModel;
import apap.tugas.iris.service.IRSService;
import apap.tugas.iris.service.MahasiswaService;
import apap.tugas.iris.service.MataKuliahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class MahasiswaController {
    @Qualifier("mahasiswaServiceImpl")
    @Autowired
    MahasiswaService mahasiswaService;
    @Autowired
    IRSService irsService;
    @Autowired
    MataKuliahService mataKuliahService;

    @GetMapping("/mahasiswa")
    public String viewAllMahasiswa(Model model){
        List<MahasiswaModel> listMahasiswa = mahasiswaService.getListMahasiswa();
        model.addAttribute("listMahasiswa", listMahasiswa);

        return "viewall-mahasiswa";
    }
    @GetMapping("mahasiswa/{npm}")
    public String detailMahasiswa(@PathVariable String npm, Model model){
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        List<IRSModel> listIrs = mahasiswa.getListIRS();
        model.addAttribute("mahasiswa", mahasiswa);
        model.addAttribute("listIrs", listIrs);

        return "view-mahasiswa";
    }
    @GetMapping("mahasiswa/{npm}/create-irs")
    public String addIRSMahasiswaFormPage(@PathVariable String npm, Model model){
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        IRSModel irs = new IRSModel();
        irs.setMahasiswa(mahasiswa);
        List<MataKuliahModel> listMK = mataKuliahService.getListMataKuliah();

        model.addAttribute("listMataKuliah", listMK);
        model.addAttribute("irs", irs);
        model.addAttribute("npm", npm);

        return "form-add-irs";
    }
    @PostMapping("mahasiswa/{npm}/create-irs")
    public String addIRSMahasiswaSubmitPage(@ModelAttribute IRSModel irs, @PathVariable String npm,
                                            @RequestParam("semester1") String semester1,
                                            @RequestParam("semester2") String semester2,
                                            Model model){

        //check is irs exsisted
        boolean isExsisted = false;
        boolean isValidSks = true;
        boolean isValidKapasitas = true;
        boolean isMkUnique = true;
        int totalSKS = 0;
        //check is mk.semester equals to irs.semester
        boolean isValidSemester = true;
        List<MataKuliahModel> listInvalidMK = new ArrayList<>();
        for (MataKuliahModel mk : irs.getListMataKuliah()) {
            totalSKS += mk.getSks();
            if (mk.getTotalMahasiswa() >= mk.getKapasitasKelas())//check kapasitas masih cukup atau tidak
                isValidKapasitas = false;
            if (!mk.getSemester().equals(irs.getSemester())) {
                isValidSemester = false;
                listInvalidMK.add(mk);
            }
        }
        String semester = irs.getSemester().toUpperCase();
        semester = semester.substring(0,3);
        semester += semester1.substring(2,4);
        semester += '/';
        semester += semester2.substring(2,4);
        irs.setSemester(semester);
        List<IRSModel> listIRS = mahasiswaService.getMahasiswaByNpm(npm).getListIRS();
        if (isValidSemester == true) {
            for (IRSModel irsListed : listIRS) {
                if (irsListed.getSemester().equals(irs.getSemester()))
                    isExsisted = true;
            }
        }
        if (isExsisted == false && isValidSemester == true && totalSKS <= 24 && isValidKapasitas==true){
            MataKuliahModel start = irs.getListMataKuliah().get(0);
            for (int i = 1; i < irs.getListMataKuliah().size(); i++){
                if (start.getCode().equals(irs.getListMataKuliah().get(i).getCode())) {
                    isMkUnique = false;
                    break;
                }
            }
        }
        if (isExsisted == false && isValidSemester == true && totalSKS <= 24 && isValidKapasitas==true
                && isMkUnique == true
        ){
            irs.setJumlahSks(totalSKS);
            irs.setMahasiswa(mahasiswaService.getMahasiswaByNpm(npm));
            irs.setStatus("Belum Disetujui");
            List<MataKuliahModel> listMataKuliah = irs.getListMataKuliah();
            for (MataKuliahModel mk : listMataKuliah) {
                mk.setTotalMahasiswa(mk.getTotalMahasiswa() + 1);
                mk.getListIrs().add(irs);
            }

            irsService.addIRS(irs);

        } else if (totalSKS > 24) {
            isValidSks = false;
        }

        model.addAttribute("isMkUnique", isMkUnique);
        model.addAttribute("isValidKapasitas", isValidKapasitas);
        model.addAttribute("isValidSks", isValidSks);
        model.addAttribute("isExisted", isExsisted);
        model.addAttribute("isValidSemester", isValidSemester);
        model.addAttribute("listInvalidMk", listInvalidMK);
        model.addAttribute("semester", irs.getSemester());
        model.addAttribute("npm", npm);

        return "add-irs";
    }
    @GetMapping("mahasiswa/{npm}/{id}")
    public String detailIRSMahasiswa(@PathVariable String npm, @PathVariable Long id, Model model) {
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        IRSModel irs = irsService.getIRSById(id);
        model.addAttribute("irs", irs);
        List<MataKuliahModel> listMk = irs.getListMataKuliah();
//        if (listMk.size()!=0 || listMk!=null){
//            for (MataKuliahModel mk : listMk){
//                for (int i = 0; i < listMk.size(); i++){
//                    if (mk.getCode() == listMk.get(i).getCode())
//                        listMk.remove(i);
//                }
//            }
//        }
        Collections.sort(listMk, new Comparator<MataKuliahModel>(){
            public int compare(MataKuliahModel o1, MataKuliahModel o2){
                if(o1.getNamaMatkul() == o2.getNamaMatkul())
                    return 0;
                return !o1.getNamaMatkul().equals(o2.getNamaMatkul()) ? -1 : 1;
            }
        });
        model.addAttribute("listMatkul", listMk);
        model.addAttribute("mahasiswa", mahasiswa);

        return "view-irs";
    }

    @GetMapping("mahasiswa/{npm}/{id}/update")
    public String updateIRSMahasiswaFormPage(@PathVariable String npm, @PathVariable Long id, Model model){
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        IRSModel recentIrs = irsService.getIRSById(id);
        List<MataKuliahModel> listRecentMk = recentIrs.getListMataKuliah();
        List<MataKuliahModel> listAllMk = mataKuliahService.getListMataKuliah();
        List<MataKuliahModel> listMk = new ArrayList<>();
        for (MataKuliahModel mk : listAllMk){
            if (!listRecentMk.contains(mk))
                listMk.add(mk);
        }
        model.addAttribute("irs", recentIrs);
        model.addAttribute("listMk", listMk);
        model.addAttribute("listRecentMk", listRecentMk);
        model.addAttribute("mahasiswa", mahasiswa);
        return "form-update-irs";
    }
    @PostMapping("mahasiswa/{npm}/{id}/update")
    public String updateIRSMahasiswaSubmitPage(@ModelAttribute IRSModel irs,
                                               @PathVariable Long id,
                                               @PathVariable String npm,
                                               Model model){
        IRSModel oldIrs = irsService.getIRSById(id);
        boolean isValidKapasitas = true;
        boolean isValidSemester = true;
        int totalSKS = 0;
        List<MataKuliahModel> listInvalidMK = new ArrayList<>();
        for (MataKuliahModel mk : irs.getListMataKuliah()) {
            totalSKS += mk.getSks();
            if (mk.getTotalMahasiswa() >= mk.getKapasitasKelas())//check kapasitas masih cukup atau tidak
                isValidKapasitas = false;
            if (!mk.getSemester().equals(irs.getListMataKuliah().get(0).getSemester())) {
                isValidSemester = false;
                listInvalidMK.add(mk);
            }
        }
        boolean isMkUnique = true;
        boolean isValidSks = true;
        if (isValidSemester == true && totalSKS <= 24 && isValidKapasitas==true && irs.getListMataKuliah().size()!=0){
            MataKuliahModel start = irs.getListMataKuliah().get(0);
            for (int i = 1; i < irs.getListMataKuliah().size(); i++){
                if (start.getCode().equals(irs.getListMataKuliah().get(i).getCode())) {
                    isMkUnique = false;
                    break;
                }
            }
        }
        if (isValidSemester == true && totalSKS <= 24 && isValidKapasitas==true
                && isMkUnique == true
        ){
            oldIrs.setJumlahSks(totalSKS);
            List<MataKuliahModel> listMataKuliah = irs.getListMataKuliah();
            for (MataKuliahModel mk : listMataKuliah) {
                if (!oldIrs.getListMataKuliah().contains(mk)){
                    int total = mk.getTotalMahasiswa();
                    total+=1;
                    mk.setTotalMahasiswa(total);
                    mk.getListIrs().add(oldIrs);
                    oldIrs.getListMataKuliah().add(mk);
                }
            }
//            List<MataKuliahModel> lisMK = irs.getListMataKuliah();
//            if(lisMK.size()!=0){
//                MataKuliahModel start = lisMK.get(0);
//                for (int i = 1; i <lisMK.size(); i++){
//                    if (start.getCode() == lisMK.get(i).getCode()){
//                        lisMK.remove(i);
//                    }
//                }
//            }
            oldIrs.setStatus(irs.getStatus());
            for(MataKuliahModel mk : irs.getListMataKuliah()){
                for (MataKuliahModel mkOld : oldIrs.getListMataKuliah()){
                    if (mk.equals(mkOld))
                        continue;
                    else if (!mk.equals(mkOld)) {
                        
                    }
                }
            }
            irsService.updateIRS(oldIrs);

        } else if (totalSKS > 24) {
            isValidSks = false;
        }

        model.addAttribute("isMkUnique", isMkUnique);
        model.addAttribute("isValidKapasitas", isValidKapasitas);
        model.addAttribute("isValidSks", isValidSks);
        model.addAttribute("isValidSemester", isValidSemester);
        model.addAttribute("listInvalidMk", listInvalidMK);
        model.addAttribute("semester", oldIrs.getSemester());
        model.addAttribute("npm", npm);
        return "update-irs";
    }
    @GetMapping("mahasiswa/{npm}/update")
    public String updateMahasiswaFormPage(@PathVariable String npm, Model model){
        MahasiswaModel mahasiswa = mahasiswaService.getMahasiswaByNpm(npm);
        model.addAttribute("mahasiswa", mahasiswa);

        return "form-update-mahasiswa";
    }
    @PostMapping("mahasiswa/{npm}/update")
    public String updateMahasiswaSubmitPage(@ModelAttribute MahasiswaModel mahasiswa, Model model){
        MahasiswaModel updatedMahasiswa = mahasiswaService.updateMahasiswa(mahasiswa);
        model.addAttribute("npm", mahasiswa.getNpm());

        return "update-mahasiswa";
    }
    @GetMapping("mahasiswa/create-mahasiswa")
    public String addMahasiswaFormPage(Model model){
        MahasiswaModel mahasiswa = new MahasiswaModel();
        model.addAttribute("mahasiswa", mahasiswa);
        return "form-add-mahasiswa";
    }

    @PostMapping("mahasiswa/create-mahasiswa")
    public String addMahasiswaSubmitPage(@ModelAttribute MahasiswaModel mahasiswa, Model model){
        if (mahasiswa.getListIRS() == null)
            mahasiswa.setListIRS(new ArrayList<>());
        List<MahasiswaModel> mahasiswaEx = mahasiswaService.getListMahasiswa();
        boolean alreadyEx = false;
        boolean sameEmail = false;
        for (MahasiswaModel mhs : mahasiswaEx){
            if(mhs.getNpm().equals(mahasiswa.getNpm())){
                alreadyEx = true;
            }if(mhs.getEmail().equals(mahasiswa.getEmail()))
                sameEmail = true;
        }
        if(alreadyEx==false && sameEmail==false)
            mahasiswaService.addMahasiswa(mahasiswa);
        model.addAttribute("npm", mahasiswa.getNpm());
        model.addAttribute("npmCheck", alreadyEx);
        model.addAttribute("emailCheck", sameEmail);
        model.addAttribute("email", mahasiswa.getEmail());

        return "add-mahasiswa";
    }
    @GetMapping("mahasiswa/delete")
    public String deleteMahasiswaForm(Model model){
        List<MahasiswaModel> listDelMahasiswa = mahasiswaService.getListMahasiswaByStatus(0);
        for (MahasiswaModel mhs : listDelMahasiswa){
            if (mhs.getStatusMahasiswa() !=0 )
                listDelMahasiswa.remove(mhs);
        }

        model.addAttribute("listMhs", listDelMahasiswa);
        return "form-delete-mahasiswa";
    }
    @PostMapping("mahasiswa/delete")
    public String deleteMahasiswaSubmit(@RequestParam("listMahasiswa") List<String> listNpm, Model model){
        System.out.println(listNpm.get(0));
        for (String npm : listNpm){
            MahasiswaModel mahasiswadel = mahasiswaService.getMahasiswaByNpm(npm);
            List<IRSModel> irsdels = mahasiswadel.getListIRS();
            for (IRSModel irsdel : irsdels){
                for (MataKuliahModel mk : irsdel.getListMataKuliah()){
                    List<IRSModel> listIrsMk = mk.getListIrs();
                    listIrsMk.remove(irsdel);
                }
                irsService.deleteIRS(irsdel);
            }
            mahasiswaService.deleteMahasiswa(mahasiswadel);
        }

        model.addAttribute("listNpm", listNpm);
        return "delete-mahasiswa";
    }

}
