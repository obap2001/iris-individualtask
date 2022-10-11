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
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mahasiswa")
public class MahasiswaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuid;

    @NotEmpty(message = "Npm depan harus diisi")
    @NotNull
    @Size(max=10)
    @Column(name="npm", nullable=false)
    private String npm;

    @NotEmpty(message = "Email harus diisi")
    @NotNull
    @Size(max=255)
    @Column(name="email", nullable=false)
    private String email;

    @NotEmpty(message = "Nama depan harus diisi")
    @NotNull
    @Size(max=255)
    @Column(name="nama_depan", nullable=false)
    private String namaDepan;

    @NotEmpty(message = "Nama belakang harus diisi")
    @NotNull
    @Size(max=255)
    @Column(name="nama_belakang", nullable=false)
    private String namaBelakang;

    @NotNull
    @Column(name="status_mahasiswa", nullable=false)
    private int statusMahasiswa;

    @OneToMany(mappedBy="mahasiswa", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<IRSModel> listIRS;
}
