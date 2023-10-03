package com.foro.alura.modelo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.foro.alura.dto.respuesta.DatosModificarRespuesta;
import com.foro.alura.dto.respuesta.DatosRegistroRespuesta;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "respuestas")
@NoArgsConstructor
public class Respuesta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "mensaje")
	private String mensaje;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "topico_id")
	private Topico topico;
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	@JsonManagedReference
	@OneToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	private Boolean solucion = false;


	public Respuesta(DatosRegistroRespuesta datosRegistroRespuesta) {
		this.mensaje = datosRegistroRespuesta.mensaje();
	}

	public void modificar(DatosModificarRespuesta datosModificarRespuesta) {
		if (datosModificarRespuesta.mensaje() != null){
			this.mensaje = datosModificarRespuesta.mensaje();
		}
		if (datosModificarRespuesta.solucion() != null){
			this.solucion = datosModificarRespuesta.solucion();
		}
	}

}
