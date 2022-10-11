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
@Table(name = "irs")
public class IRSModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max=255)
    @Column(name="status", nullable=false)
    private String status;

    @NotEmpty(message = "Semester harus diisi")
    @NotNull
    @Size(max=8)
    @Column(name="semester", nullable=false)
    private String semester;

    @NotNull
    @Column(name="jumlah_sks", nullable=false)
    private int jumlahSks;


    @ManyToMany(mappedBy = "listIrs")
    List<MataKuliahModel> listMataKuliah;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uuid", referencedColumnName = "uuid",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MahasiswaModel mahasiswa;
}
