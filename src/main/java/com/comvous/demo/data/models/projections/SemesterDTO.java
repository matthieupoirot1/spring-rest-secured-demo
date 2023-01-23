package com.comvous.demo.data.models.projections;

import com.comvous.demo.data.models.User;

import java.util.List;

public class SemesterDTO {

        private Long id;
        private String name;

        private String date;


        public SemesterDTO() {
        }

        public SemesterDTO(Long id, String name, String date) {
            this.id = id;
            this.name = name;
            this.date = date;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
}
