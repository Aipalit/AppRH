package com.AppRH.AppRH.models;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

public class vaga {
    public class Vaga implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO) // gerando id auto
        private long code;

        @NotEmpty
        private String name;

        @NotEmpty
        private String description;

        @NotEmpty
        private String date;

        @NotEmpty
        private String salary;

        @OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE) // quando deletar uma vaga, deletar os candidatos
        private List<Candidate> cadidates;

        public long getCode() {
            return code;
        }

        public void setCode(long code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public List<Candidate> getCadidates() {
            return cadidates;
        }

        public void setCadidates(List<Candidate> cadidates) {
            this.cadidates = cadidates;
        }

        
    }
}
