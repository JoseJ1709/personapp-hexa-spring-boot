package co.edu.javeriana.as.personapp.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudiosRequest {
    private String cc_per;
    private String id_prof;
    private String fecha;
    private String univer;
    private String database;
}