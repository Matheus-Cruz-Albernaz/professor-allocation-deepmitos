package com.ipl.professorallocation.model;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public enum DiasDaSemana {
    SUNDAY() {
        @NonNull
        @Override
        public String toString() {
            return "Sunday";
        }
    },
    MONDAY(){
        @NonNull
        @Override
        public String toString() {
            return "Monday";
        }
    },
    TUESDAY(){
        @NonNull
        @Override
        public String toString() {
            return "Tuesday";
        }
    },
    WEDNESDAY(){
        @NonNull
        @Override
        public String toString() {
            return "Wednesday";
        }
    },
    THURSDAY(){
        @NonNull
        @Override
        public String toString() {
            return "Thursday";
        }
    },
    FRIDAY(){
        @NonNull
        @Override
        public String toString() {
            return "Friday";
        }
    },
    SATURDAY() {
        @NonNull
        @Override
        public String toString() {
            return "Saturday";
        }
    };

    public static List<DiasDaSemana> listarDiasDaSemana() {
        return Arrays.asList(SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY);
    }

}
