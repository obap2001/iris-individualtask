package apap.tugas.iris.controller;

import apap.tugas.iris.model.DosenMataKuliahModel;
import apap.tugas.iris.model.DosenModel;
import apap.tugas.iris.model.MataKuliahModel;
import apap.tugas.iris.service.DosenMataKuliahService;
import apap.tugas.iris.service.DosenService;
import apap.tugas.iris.service.MataKuliahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BaseController {
    @Autowired
    DosenService dosenService;
    @Autowired
    MataKuliahService mataKuliahService;
    @Autowired
    DosenMataKuliahService dosenMataKuliahService;
    @GetMapping("/")
    public String Home(){
        return "home";
    }
    @GetMapping("/filter/mata-kuliah")
    public String filterMKFormPage(Model model){
        List<DosenModel> listDosen = dosenService.getListDosen();
        model.addAttribute("listDosen", listDosen);
        return "form-filter-matkul";
    }
    @PostMapping("/filter/mata-kuliah")
    public String filterMKSubmitPage(@RequestParam(value = "dosen") String nip,
                                        @RequestParam(value = "semester") String semester,
                                        Model model)
    {
        List<MataKuliahModel> listMkSemester = mataKuliahService.getListMataKuliahQuery(semester.toLowerCase());
        System.out.println(listMkSemester.size());
        List<MataKuliahModel> listMk = new ArrayList<>();
        DosenModel dosen = dosenService.getDosenByNip(nip);
        List<DosenMataKuliahModel> listDmk = dosenMataKuliahService.getListDosenMataKuliahByDosen(dosen);
        for (MataKuliahModel mk : listMkSemester){
            for (DosenMataKuliahModel dmk : listDmk) {
                if (mk.getListDosenMataKuliah().contains(dmk)) {
                    listMk.add(mk);
                }
            }
        }

        model.addAttribute("listMk", listMk);
        model.addAttribute("nip", nip);
        model.addAttribute("semester", semester);
        List<DosenModel> listDosen = dosenService.getListDosen();
        model.addAttribute("listDosen", listDosen);
        return "filter-matkul";
    }
}
