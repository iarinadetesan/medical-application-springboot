package com.springapp.medicalapplication.doctor.dashboard;

import com.springapp.medicalapplication.consultation.dto.ConsultationResponseDTO;
import com.springapp.medicalapplication.doctor.dto.DoctorResponseDTO;
import com.springapp.medicalapplication.patient.dashboard.PatientSummaryDTO;
import com.springapp.medicalapplication.patient.dto.PatientResponseDTO;
import com.springapp.medicalapplication.prescription.dto.PrescriptionResponseDTO;
import com.springapp.medicalapplication.referral.dto.ReferralResponseDTO;

import java.util.List;

public class DoctorDashboardDTO {
    public DoctorResponseDTO doctor;
    public DoctorSummaryDTO summary;
    public List<PatientResponseDTO> patients;
    public List<ConsultationResponseDTO> recentConsultations;
    public List<PrescriptionResponseDTO> prescriptions;
    public List<ReferralResponseDTO> referrals;


}
