package apap.tugas.iris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mata_kuliah")
public class MataKuliahModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Code harus diisi")
    @NotNull
    @Size(max = 14)
    @Column(name="code", nullable=false)
    private String code;

    @NotEmpty(message = "Semester harus diisi")
    @NotNull
    @Size(max=6)
    @Column(name="semester", nullable=false)
    private String semester;

    @NotNull
    @Column(name="kapasitas_kelas", nullable=false)
    private int kapasitasKelas;

    @Column(name="sks", nullable=false)
    @Min(value = 1, message = "Sks tidak bisa bernilai nol")
    private int sks;

    @NotNull
    @Size(max=255)
    @Column(name="nama_matkul", nullable=false)
    private String namaMatkul;

    @NotNull
    @Column(nullable=false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuMulai;

    @NotNull
    @Column(nullable=false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime waktuSelesai;

    @NotEmpty(message = "Hari harus diisi")
    @NotNull
    @Size(max=255)
    @Column(name="hari", nullable=false)
    private String hari;

    @NotNull
    @Column(name="total_mahasiswa", nullable=false)
    private int totalMahasiswa; //default value = 0 hidden

    @ManyToMany
    @JoinTable(name = "irs_mata_kuliah", joinColumns = @JoinColumn(name = "id_mata_kuliah"), inverseJoinColumns = @JoinColumn(name = "id_irs"))
    List<IRSModel> listIrs;

//    @ManyToMany
//    @JoinTable(name = "dosen_mata_kuliah", joinColumns = @JoinColumn(name = "id_mata_kuliah"), inverseJoinColumns = @JoinColumn(name = "nip"))
//    List<DosenModel> listDosen;
    @OneToMany(mappedBy = "mataKuliah")
    List<DosenMataKuliahModel> listDosenMataKuliah;
}
