package apap.tugas.iris.controller;

import apap.tugas.iris.model.*;
import apap.tugas.iris.service.DosenMataKuliahService;
import apap.tugas.iris.service.DosenService;
import apap.tugas.iris.service.IRSService;
import apap.tugas.iris.service.MataKuliahService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MataKuliahController {
    @Qualifier("mataKuliahServiceImpl")
    @Autowired
    private MataKuliahService mataKuliahService;

    @Autowired
    private DosenMataKuliahService dosenMataKuliahService;
    @Autowired
    private DosenService dosenService;

    @GetMapping("/mata-kuliah")
    public String viewAllMataKuliah(Model model){
        List<MataKuliahModel> listMataKuliah = mataKuliahService.getListMataKuliah();
        model.addAttribute("listMataKuliah", listMataKuliah);
        return "viewall-mata-kuliah";
    }

    @GetMapping("/mata-kuliah/{id}")
    public String detailMataKuliah(@PathVariable Long id, Model model){
        MataKuliahModel mataKuliah = mataKuliahService.getMataKuliahById(id);
        List<DosenMataKuliahModel> listDosenMataKuliah = dosenMataKuliahService.getListDosenMataKuliah(mataKuliah);
        model.addAttribute("mataKuliah", mataKuliah);
        model.addAttribute("listDosenMataKuliah", listDosenMataKuliah);
        return "view-mata-kuliah";
    }
    @GetMapping("/mata-kuliah/{id}/delete")
    public String deleteMataKuliah(@PathVariable Long id, Model model){
        MataKuliahModel mataKuliah = mataKuliahService.getMataKuliahById(id);
        boolean isNotInIrs = false;
        List<IRSModel> listIrs = mataKuliah.getListIrs();
        if(listIrs.size()==0){
            isNotInIrs = true;
            if(mataKuliah.getListDosenMataKuliah().size() == 1)
                dosenMataKuliahService.deleteDosenMataKuliah(mataKuliah.getId());
            else if (mataKuliah.getListDosenMataKuliah().size() > 1)
                dosenMataKuliahService.deleteAll(mataKuliah.getId());
            mataKuliahService.deleteMataKuliah(mataKuliah);
        }
        model.addAttribute("code", mataKuliah.getCode());
        model.addAttribute("isNotInIrs", isNotInIrs);

        return "delete-mata-kuliah";
    }
    @GetMapping("/mata-kuliah/create-matakuliah")
    public String addMataKuliahFormPage(Model model){
        MataKuliahModel mataKuliah =  new MataKuliahModel();
        List<DosenModel> listDosen = dosenService.getListDosen();
        List<DosenMataKuliahModel> listDosenMataKuliah = dosenMataKuliahService.getListDosenMataKuliah();
        List<DosenMataKuliahModel> listDosenMataKuliahNew = new ArrayList<>();

        mataKuliah.setListDosenMataKuliah(listDosenMataKuliahNew);
        mataKuliah.getListDosenMataKuliah().add(new DosenMataKuliahModel());

        model.addAttribute("listDosenMataKuliahExisting", listDosenMataKuliah);
        model.addAttribute("listDosen", listDosen);
        model.addAttribute("mataKuliah",mataKuliah);
        return "form-add-mata-kuliah";
    }
    @PostMapping(value = "/mata-kuliah/create-matakuliah", params = "save")
    public String addMataKuliahSubmitPage(@ModelAttribute MataKuliahModel mataKuliah,
                                          @RequestParam("nip") String nip,
                                          @RequestParam("ruangKelas") String ruangKelas,
                                          Model model){
        LocalTime waktuMulai = mataKuliah.getWaktuMulai();
        int sks = mataKuliah.getSks();
        LocalTime waktuSelesai = waktuMulai.plusMinutes(sks*50);
        mataKuliah.setWaktuSelesai(waktuSelesai);
        mataKuliah.setTotalMahasiswa(0);
        String code = "MK";
        code = code + mataKuliah.getNamaMatkul().toUpperCase().substring(0,3);
        code = code + mataKuliah.getSemester().toUpperCase().substring(0,3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        LocalTime waktuSelesaii = mataKuliah.getWaktuSelesai();
        String jamMulai = waktuMulai.format(formatter);
        int hour = waktuSelesaii.get(ChronoField.CLOCK_HOUR_OF_DAY);
        String jamSelesai = String.valueOf(hour);
        String random = getRandomAlpha(2);
        code = code+jamMulai+jamSelesai+random;
        mataKuliah.setCode(code);
        mataKuliah.setListIrs(new ArrayList<>());
        if(mataKuliah.getListDosenMataKuliah() == null){
            mataKuliah.setListDosenMataKuliah(new ArrayList<>());
        }

        DosenMataKuliahKeyModel key = new DosenMataKuliahKeyModel(nip, mataKuliah.getId());
        DosenMataKuliahModel dmk = new DosenMataKuliahModel(key, dosenService.getDosenByNip(nip), mataKuliah, ruangKelas);
//        dosenMataKuliahService.addDosenMataKuliah(dmk);
        mataKuliah.getListDosenMataKuliah().add(dmk);
        mataKuliahService.addMataKuliah(mataKuliah);
        dosenMataKuliahService.addDosenMataKuliah(dmk);
        System.out.println(mataKuliah.getListDosenMataKuliah().get(0).getRuangKelas());

        model.addAttribute("code", mataKuliah.getCode());
        return "add-mata-kuliah";
    }
    //code reference: https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/
    static String getRandomAlpha(int n)
    {
        String Alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(Alphabet.length() * Math.random());
            sb.append(Alphabet.charAt(index));
        }
        return sb.toString();
    }
    @PostMapping(value = "/mata-kuliah/create-matakuliah", params = { "addRow" })
    private String addRowMataKuliahMultiple(@ModelAttribute MataKuliahModel mataKuliah,
                                            @ModelAttribute DosenMataKuliahModel dosenMataKuliah,
                                            @RequestParam("nip") String nip,
                                            @RequestParam("ruangKelas") String ruangKelas,
                                            Model model) {
        if (mataKuliah.getListDosenMataKuliah() == null || mataKuliah.getListDosenMataKuliah().size() ==0) {
            mataKuliah.setListDosenMataKuliah(new ArrayList<>());
//            dosenMataKuliahService.addDosenMataKuliah(doSenMataKuliah);
        }
//        dosenMataKuliahService.addDosenMataKuliah(new DosenMataKuliahModel());
        DosenMataKuliahModel dmk = new DosenMataKuliahModel();
        dmk.setMataKuliah(mataKuliah);
        dmk.setDosen(dosenService.getDosenByNip(nip));
        dmk.setRuangKelas(ruangKelas);
        DosenMataKuliahKeyModel key = new DosenMataKuliahKeyModel();
        key.setIdMataKuliah(mataKuliah.getId());
        key.setNip(nip);
        dmk.setId(key);
//        dosenMataKuliahService.addDosenMataKuliah(dmk);
        List<DosenMataKuliahModel> listDmkBaru = mataKuliah.getListDosenMataKuliah();
        listDmkBaru.add(dmk);
        mataKuliah.setListDosenMataKuliah(listDmkBaru);
        dosenMataKuliah.setMataKuliah(mataKuliah);
        dosenMataKuliah.setDosen(mataKuliah.getListDosenMataKuliah().get(0).getDosen());
//        dosenMataKuliahService.addDosenMataKuliah(dosenMataKuliah);
//        dosenMataKuliahService.
        List<DosenMataKuliahModel> listDosenMataKuliah = dosenMataKuliahService.getListDosenMataKuliah();
        List<DosenModel> listDosen = dosenService.getListDosen();

        model.addAttribute("mataKuliah", mataKuliah);
        model.addAttribute("listDosenMataKuliahExisting", listDosenMataKuliah);
        model.addAttribute("listDosen", listDosen);

        return "form-add-mata-kuliah";
    }

    @PostMapping(value = "/mata-kuliah/create-matakuliah", params = { "deleteRow" })
    private String deleteRowMataKuliahMultiple(
            @ModelAttribute MataKuliahModel mataKuliah,
            @RequestParam("deleteRow") Integer row,
            Model model) {
        final Integer rowId = Integer.valueOf(row);
        mataKuliah.getListDosenMataKuliah().remove(rowId.intValue());

        List<DosenMataKuliahModel> listDosenMataKuliah = dosenMataKuliahService.getListDosenMataKuliah();
        List<DosenModel> listDosen = dosenService.getListDosen();

        model.addAttribute("mataKuliah", mataKuliah);
        model.addAttribute("listDosenMataKuliahExisting", listDosenMataKuliah);
        model.addAttribute("listDosen", listDosen);

        return "form-add-mata-kuliah";
    }
}
