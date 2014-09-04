package org.jugvale.call4papers.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.jugvale.call4papers.model.DefaultModel;
import org.jugvale.call4papers.model.builder.AutorBuilder;

@Entity
@XmlRootElement
@EqualsAndHashCode(callSuper = true) @ToString(callSuper=true)
@Getter @Setter 
public class Autor extends DefaultModel {

	private static final long serialVersionUID = 2467078273125922962L;

   @Column(nullable = false)
   private String nome;

   @Column(unique = true)
   private String email;

   @Column
   private String telefone;

   @Column
   private String site;

   @Column
   private String miniCurriculo;

   @OneToOne
   private Usuario usuario;
   
   public static AutorBuilder newAutor() {
	   return new AutorBuilder();
   }
   
}