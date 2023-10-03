package com.foro.alura.modelo;

import com.foro.alura.dto.curso.DatosRegistroCurso;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "cursos")
@NoArgsConstructor
public class Curso {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "categoria")
	private String categoria;

	public Curso(String nombre, String categoria) {
		this.nombre = nombre;
		this.categoria = categoria;
	}


	public Curso(DatosRegistroCurso datosRegistroCurso) {
		this.nombre = datosRegistroCurso.nombre();
		this.categoria = datosRegistroCurso.categoria();
	}

}
