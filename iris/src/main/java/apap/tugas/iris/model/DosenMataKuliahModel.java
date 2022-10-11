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
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dosen_mata_kuliah")
@Embeddable
public class DosenMataKuliahModel {

    @EmbeddedId
    private DosenMataKuliahKeyModel id;

    @ManyToOne
    @MapsId("nip")
    @JoinColumn(name = "nip")
    @OnDelete(action = OnDeleteAction.CASCADE)
    DosenModel dosen;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    MataKuliahModel mataKuliah;

    @NotEmpty(message = "Ruang kelas harus diisi")
    @NotNull
    @Size(max=20)
    @Column(name="ruang_kelas")
    private String ruangKelas;
}
