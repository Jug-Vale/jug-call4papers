package org.jugvale.call4papers.model.impl;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Autor implements Serializable
{

   private static final long serialVersionUID = 3253089299207253810L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Column
   private String nome;

   @Column
   private String email;

   @Column
   private String telefone;

   @Column
   private String site;

   @Column
   private String miniCurriculo;

   @OneToOne
   private Usuario usuario;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((Autor) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getNome()
   {
      return this.nome;
   }

   public void setNome(final String nome)
   {
      this.nome = nome;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getTelefone()
   {
      return this.telefone;
   }

   public void setTelefone(final String telefone)
   {
      this.telefone = telefone;
   }

   public String getSite()
   {
      return this.site;
   }

   public void setSite(final String site)
   {
      this.site = site;
   }

   public String getMiniCurriculo()
   {
      return this.miniCurriculo;
   }

   public void setMiniCurriculo(final String miniCurriculo)
   {
      this.miniCurriculo = miniCurriculo;
   }
   
   public Usuario getUsuario()
   {
      return this.usuario;
   }

   public void setUsuario(final Usuario usuario)
   {
      this.usuario = usuario;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "serialVersionUID: " + serialVersionUID;
      if (nome != null && !nome.trim().isEmpty())
         result += ", nome: " + nome;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (telefone != null && !telefone.trim().isEmpty())
         result += ", telefone: " + telefone;
      if (site != null && !site.trim().isEmpty())
         result += ", site: " + site;
      if (miniCurriculo != null && !miniCurriculo.trim().isEmpty())
         result += ", miniCurriculo: " + miniCurriculo;
      return result;
   }
}