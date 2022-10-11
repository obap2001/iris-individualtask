package apap.tugas.iris.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DosenMataKuliahKeyModel implements Serializable {
    @Column(name = "nip")
    String nip;

    @Column(name = "id")
    Long idMataKuliah;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        DosenMataKuliahKeyModel that = (DosenMataKuliahKeyModel) o;
        return Objects.equals(nip, that.nip) &&
                Objects.equals(idMataKuliah, that.idMataKuliah);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nip, idMataKuliah);
    }

}
