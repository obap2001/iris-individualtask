package apap.tugas.iris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dosen")
public class DosenModel {
    @NotEmpty(message = "Nip harus diisi")
    @Id
    @NotNull
    @Size(max=255)
    private String nip;

    @NotEmpty(message = "Nama depan harus diisi")
    @NotNull
    @Size(max=255)
    @Column(name="nama_depan")
    private String namaDepan;

    @NotEmpty(message = "Nama belakang harus diisi")
    @NotNull
    @Size(max=255)
    @Column(name="nama_belakang")
    private String namaBelakang;

//    @ManyToMany(mappedBy = "listDosen")
//    List<MataKuliahModel> listMataKuliah;
    @OneToMany(mappedBy = "dosen")
    List<DosenMataKuliahModel> listDosenMataKuliah;

}
