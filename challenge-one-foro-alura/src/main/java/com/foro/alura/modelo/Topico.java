package com.foro.alura.modelo;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.foro.alura.dto.topico.DatosModificarTopico;
import com.foro.alura.dto.topico.DatosRegistroTopico;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "topicos")
@NoArgsConstructor
public class Topico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "titulo")
	private String titulo;
	@Column(name = "mensaje")
	private String mensaje;
	@Column(name = "fechaCreacion")
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	@Enumerated
	private StatusTopico status = StatusTopico.NO_RESPONDIDO;

	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Usuario autor;
	@OneToOne
	@JoinColumn(name = "curso_id")
	private Curso curso;
	@JsonManagedReference
	@OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Respuesta> respuestas = new ArrayList<>();

	public Topico(String titulo, String mensaje, Curso curso) {
		this.titulo = titulo;
		this.mensaje = mensaje;
		this.curso = curso;
	}
	public Topico(DatosRegistroTopico datosRegistroTopico) {
		this.titulo = datosRegistroTopico.titulo();
		this.mensaje = datosRegistroTopico.mensaje();
	}

	public void modificar(DatosModificarTopico datosModificarTopico) {
		if (datosModificarTopico.titulo() != null){
			this.titulo = datosModificarTopico.titulo();
		}
		if (datosModificarTopico.mensaje() != null){
			this.mensaje = datosModificarTopico.mensaje();
		}
		if (datosModificarTopico.statusTopico() != null){
			this.status = datosModificarTopico.statusTopico();
		}
	}


}

